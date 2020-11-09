package com.golden.goldencorner.ui.main.OrderRate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Qustions.DataItem;
import com.golden.goldencorner.data.Qustions.SendRate;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderRateFragment extends DialogFragment implements DriverAdapter.AdapterListener, OrderAdapter.AdapterListener {
    @BindView(R.id.DriverRateRV)
    RecyclerView DriverRateRV;
    @BindView(R.id.OrderRateRV)
    RecyclerView OrderRateRV;
    @BindView(R.id.sendEvaluationBtn)
    CircularProgressButton sendEvaluationBtn;
    ArrayList<SendRate> sendRates = new ArrayList<>();
    SendRate sendRate;
    String token;
    Long orderId;
    String driverId;
    private OrderRateViewModel mViewModel;
    private DriverAdapter mAdapter;
    private OrderAdapter orderAdapter;
    private List<DataItem> dataList = new ArrayList<>();

    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_evaluation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this)
                .get(OrderRateViewModel.class);
        mAdapter = new DriverAdapter();
        orderAdapter = new OrderAdapter();
        mAdapter.mListener = this;
        orderAdapter.mListener = this;

        token = ((MainActivity) getActivity()).getAccessToken();
        mViewModel.invokeRateApi(token);
        subscribeOrderObserver();
        DriverRateRV.setAdapter(mAdapter);
        OrderRateRV.setAdapter(orderAdapter);

        if (getArguments() != null) {
            driverId = getArguments().getString("driverId");
            orderId = getArguments().getLong("orderId");
        }
    }

    private void subscribeOrderObserver() {
        mViewModel.getRateLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<DataItem>>>() {
                    @Override
                    public void onChanged(Resource<List<DataItem>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    dataList.addAll(resource.getData());
                                    showProgressBar(false);
                                    mAdapter.fillAdapterData(dataList.get(0).getDriver(), dataList.get(0).getAnswer());
                                    orderAdapter.fillAdapterData(dataList.get(0).getOrder(), dataList.get(0).getAnswer());
                                    break;
                                case ERROR:
                                    showProgressBar(false);
                                    ((MainActivity) getActivity()).showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    showProgressBar(true);
                                    break;
                            }
                        }
                    }
                });
    }

    private void subscribeSendOrderObserver() {
        mViewModel.getRateSendLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<SimpleModel>>() {
                    @Override
                    public void onChanged(Resource<SimpleModel> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar(false);
                                    ((MainActivity) getActivity()).showToast(resource.getMessage().toString());
                                    break;
                                case ERROR:
                                    showProgressBar(false);
                                    ((MainActivity) getActivity()).showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    showProgressBar(true);
                                    break;
                            }
                        }
                    }
                });
    }

    private void showProgressBar(boolean flag) {
//        isLoading = flag;
        ((MainActivity) getActivity()).showProgressBar(flag);
    }

    @OnClick(R.id.sendEvaluationBtn)
    public void sendEvaluation() {
        String question_id = sendRates.get(0).getQuestion_id();
        StringBuilder nb = new StringBuilder(question_id);
        for (int i = 0; i < sendRates.size(); i++) {

            if (i == 0) {
                continue;
            } else {
                String z = sendRates.get(i).getQuestion_id();
                nb.append("/" + z);
            }
        }


        String rate = sendRates.get(0).getRate();
        StringBuilder rb = new StringBuilder(rate);
        for (int i = 0; i < sendRates.size(); i++) {

            if (i == 0) {
                continue;
            } else {
                String z = sendRates.get(i).getRate();
                rb.append("/" + z);
            }
        }

        String type = sendRates.get(0).getType();
        StringBuilder tb = new StringBuilder(type);
        for (int i = 0; i < sendRates.size(); i++) {

            if (i == 0) {
                continue;
            } else {
                String z = sendRates.get(i).getType();
                tb.append("/" + z);
            }
        }


        mViewModel.invokeSendRateApi(token, String.valueOf(orderId), driverId, nb.toString(), rb.toString(), tb.toString());
        subscribeSendOrderObserver();
    }


    @Override
    public void onAddToCart(String question_id, String rate, String type) {
        sendRate = new SendRate(question_id, rate, type);
        sendRates.add(sendRate);
    }

    @Override
    public void onAddToCartOrder(String question_id, String rate, String type) {
        sendRate = new SendRate(question_id, rate, type);
        sendRates.add(sendRate);
    }
}
