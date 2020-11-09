package com.golden.goldencorner.ui.main.orderDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.OrderRecords;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailsFragment extends DialogFragment {


    @BindView(R.id.orderNumberTV)
    TextView orderNumberTV;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.payment_method)
    TextView payment_method;
    @BindView(R.id.branchesIV)
    TextView branchesIV;
    @BindView(R.id.mobileTV)
    TextView mobileTV;
    @BindView(R.id.PaymentSummaryTotalPriceTV)
    TextView PaymentSummaryTotalPriceTV;
    @BindView(R.id.PaymentSummaryOrderTotalPriceTV)
    TextView PaymentSummaryOrderTotalPriceTV;
    @BindView(R.id.PaymentSummaryOrderTaxPriceTV)
    TextView PaymentSummaryOrderTaxPriceTV;
    @BindView(R.id.discounte)
    TextView PaymentSummaryOrderTaxTV;
    @BindView(R.id.PaymentSummaryDiscountCodePriceTV)
    TextView PaymentSummaryDiscountCodePriceTV;
    @BindView(R.id.TotalWithoutTax)
    TextView TotalWithoutTax;
    @BindView(R.id.TotalWithoutTaxPriceTV)
    TextView TotalWithoutTaxPriceTV;
    @BindView(R.id.DeliveryPriceTV)
    TextView DeliveryPriceTV;
    @BindView(R.id.evaluationTv2)
    TextView evaluationTv2;
    @BindView(R.id.NotesHintTv2)
    TextView NotesHintTv2;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.Ritem)
    RecyclerView Ritem;
    OrderDetailsViewModel mViewModel;
    String token;
    long id;
    OrderAdapter orderAdapter;

    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this)
                .get(OrderDetailsViewModel.class);
        if (getArguments() != null) {
            id = getArguments().getLong("orderId");
            token = SharedPreferencesManager.getString(AppConstant.ACCESS_TOKEN);
        }
        mViewModel.invokeOrderApi(id, token);
        subscribeRateObserver();
        orderAdapter = new OrderAdapter();
        Ritem.setAdapter(orderAdapter);

    }

    private void subscribeRateObserver() {
        mViewModel.getOrderLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<OrderRecords>>() {
                    @Override
                    public void onChanged(Resource<OrderRecords> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    orderNumberTV.setText(String.valueOf(resource.getData().getId()));
                                    nameTV.setText(resource.getData().getUser().getName());
                                    payment_method.setText(resource.getData().getPaymentMethodName());
                                    branchesIV.setText(resource.getData().getBranch().getName());
                                    mobileTV.setText(resource.getData().getUser().getMobile());
                                    PaymentSummaryTotalPriceTV.setText(String.valueOf(resource.getData().getGrandTotal()));
                                    PaymentSummaryOrderTotalPriceTV.setText(String.valueOf(resource.getData().getTotalPrice()));
                                    PaymentSummaryOrderTaxPriceTV.setText(String.valueOf((float) resource.getData().getDiscountCodePrice()));
                                    PaymentSummaryOrderTaxTV.setText(String.valueOf(getActivity().getString(R.string.discount_code2) + "%" + resource.getData().getDiscountPricePersent()));
                                    PaymentSummaryDiscountCodePriceTV.setText(String.valueOf(resource.getData().getShipping()));
                                    TotalWithoutTax.setText(String.valueOf(getActivity().getString(R.string.tax) + "%" + resource.getData().getTax()));
                                    TotalWithoutTaxPriceTV.setText(String.valueOf(resource.getData().getTaxPrice()));
                                    DeliveryPriceTV.setText(String.valueOf(resource.getData().getDiscountPrice()));
                                    orderAdapter.fillAdapterData(resource.getData().getProduct());
                                    if (resource.getData().getDescription().length() == 0) {
                                        evaluationTv2.setVisibility(View.GONE);
                                        NotesHintTv2.setVisibility(View.GONE);
                                        view.setVisibility(View.GONE);
                                    } else {
                                        evaluationTv2.setText(resource.getData().getDescription());
                                    }
                                    break;
                                case ERROR:
                                    break;
                                case LOADING:
                                    break;
                            }
                        }
                    }
                });
    }
}