package com.golden.goldencorner.ui.main.creditCards;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.ui.main.MainActivity;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CardsFragment extends Fragment {

    @BindView(R.id.productMealsTV)
    TextView productMealsTV;
    @BindView(R.id.viewLine)
    View viewLine;
    @BindView(R.id.cardHolderNameTv)
    TextView cardHolderNameTv;
    @BindView(R.id.commentET)
    EditText minutesSpinner;
    @BindView(R.id.cardNumberHintTv)
    TextView cardNumberHintTv;
    @BindView(R.id.cardNumberET)
    EditText cardNumberET;
    @BindView(R.id.cardCVCTv)
    TextView cardCVCTv;
    @BindView(R.id.cardCVCET)
    EditText cardCVCET;
    @BindView(R.id.cardExpireDateTv)
    TextView cardExpireDateTv;
    @BindView(R.id.cardExpireDateET)
    EditText cardExpireDateET;
    @BindView(R.id.addEvaluteBtn)
    CircularProgressButton addAddressBtn;
    private CardsViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cards, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this).get(CardsViewModel.class);
        subscribeMetaObserver();
        subscribeCardActionsObserver();

        String token = ((MainActivity) getActivity()).getAccessToken();
        if (TextUtils.isEmpty(token)){
            ((MainActivity) getActivity()).showToast(getString(R.string.login_or_create_new_account_and_try_again));
        } else {
            mViewModel.invokeCardsApi(token);
        }
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
        mViewModel.getCardActionsLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<SimpleModel>>() {
                    @Override
                    public void onChanged(Resource<SimpleModel> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    ((MainActivity)getActivity()).showProgressBar(false);
                                    ((MainActivity) getActivity()).showToast(resource.getData().getMessage());
                                    mViewModel.invokeCardsApi(((MainActivity) getActivity()).getAccessToken());
                                    break;
                                case ERROR:
                                    ((MainActivity)getActivity()).showProgressBar(false);
                                    ((MainActivity) getActivity()).showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    ((MainActivity)getActivity()).showProgressBar(true);
                                    break;
                            }
                        }
                    }
                });
    }

    @OnClick(R.id.addEvaluteBtn)
    public void onViewClicked() {
    }
}