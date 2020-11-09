package com.golden.goldencorner.ui.main.creditCards;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.CardRecords;
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.ui.ViewDialog;
import com.golden.goldencorner.ui.main.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CardsFragment extends Fragment implements CardsAdapter.AdapterListener {

    @BindView(R.id.cardsRV)
    RecyclerView cardsRV;
    @BindView(R.id.addNewAddressBtn)
    Button addNewAddressBtn;
    CardsAdapter cardsAdapter;
    private CardsViewModel mViewModel;
    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cridet_cards, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this).get(CardsViewModel.class);
        cardsAdapter = new CardsAdapter();
        cardsAdapter.mListener = this;

        token = ((MainActivity) getActivity()).getAccessToken();
        if (TextUtils.isEmpty(token)) {
            ViewDialog alert = new ViewDialog();
            alert.showDialog(getActivity());
        } else {
            mViewModel.invokeCardsApi(token);
        }

        subscribeMetaObserver();
        subscribeCardActionsObserver();
        cardsRV.setAdapter(cardsAdapter);

    }

    private Meta meta = null;

    private void subscribeMetaObserver() {
        mViewModel.getMetaLiveData().observe(getViewLifecycleOwner(),
                new Observer<Meta>() {
                    @Override
                    public void onChanged(Meta metaResponse) {
                        CardsFragment.this.meta = metaResponse;
                    }
                });
    }

    private void subscribeCardActionsObserver() {
        mViewModel.getCardsLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<CardRecords>>>() {
                    @Override
                    public void onChanged(Resource<List<CardRecords>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    ((MainActivity) getActivity()).showProgressBar(false);
                                    cardsAdapter.fillAdapterData(resource.getData(), getContext());
                                    break;
                                case ERROR:
                                    ((MainActivity) getActivity()).showProgressBar(false);
                                    ((MainActivity) getActivity()).showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    ((MainActivity) getActivity()).showProgressBar(true);
                                    break;
                            }
                        }
                    }
                });
    }

    private void subscribeCardDeleteObserver() {
        mViewModel.getCardActionsLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<SimpleModel>>() {
                    @Override
                    public void onChanged(Resource<SimpleModel> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    ((MainActivity) getActivity()).showProgressBar(false);
                                    mViewModel.invokeCardsApi(token);
                                    subscribeCardActionsObserver();
                                    break;
                                case ERROR:
                                    ((MainActivity) getActivity()).showProgressBar(false);
                                    ((MainActivity) getActivity()).showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    ((MainActivity) getActivity()).showProgressBar(true);
                                    break;
                            }
                        }
                    }
                });
    }

    @Override
    public void deleteItem(CardRecords record, int position) {
        mViewModel.invokeDeleteAddressApi(token, record.getId());
        subscribeCardDeleteObserver();
    }

    @OnClick(R.id.addNewAddressBtn)
    public void onClickItem() {
        ((MainActivity) getActivity()).navToDestination(R.id.nav_cards);
    }

    @Override
    public void viewItem(CardRecords record, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("cardData", record);
        ((MainActivity) getActivity()).navToDestination(R.id.editFragment, bundle);
    }
}