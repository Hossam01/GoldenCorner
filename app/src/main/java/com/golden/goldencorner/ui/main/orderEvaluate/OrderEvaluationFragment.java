package com.golden.goldencorner.ui.main.orderEvaluate;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.ui.main.MainActivity;
import com.squareup.picasso.Picasso;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.golden.goldencorner.data.Utils.AppConstant.USER_IMAGE;
import static com.golden.goldencorner.data.Utils.AppConstant.UserName;

public class OrderEvaluationFragment extends DialogFragment {

    @BindView(R.id.closeIV)
    ImageView closeIV;
    @BindView(R.id.imageIV)
    ImageView evaluationIV;
    @BindView(R.id.productMealsTV)
    TextView productMealsTV;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.evaluationHintTv)
    TextView evaluationHintTv;
    @BindView(R.id.evaluationHintTv2)
    TextView evaluationHintTv2;
    @BindView(R.id.rateRB)
    RatingBar rateRB;
    @BindView(R.id.cardHolderNameTv)
    TextView cardHolderNameTv;
    @BindView(R.id.commentET)
    EditText commentET;
    @BindView(R.id.addcommentbtn)
    CircularProgressButton addCommentBtn;
    @BindView(R.id.addEvaluationBtn)
    CircularProgressButton addEvaluationBtn;
    private OrderEvaluationViewModel mViewModel;
    private long productId;
    private String userImage;
    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.evalute_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this)
                .get(OrderEvaluationViewModel.class);
        subscribeRateObserver();
        subscribeCommentObserver();
        String username= SharedPreferencesManager.getString(UserName);
        nameTV.setText(username);
        Picasso.get().load(SharedPreferencesManager.getString(USER_IMAGE)).into(evaluationIV);

//        if (getArguments() != null){
            productId = getArguments().getLong(AppConstant.PRODUCT_ID);
            userImage = getArguments().getString(AppConstant.USER_IMAGE);
            Picasso.get().load(userImage).into(evaluationIV);
       // }
    }

    private void subscribeRateObserver() {
        mViewModel.getRateLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<SimpleModel>>() {
                    @Override
                    public void onChanged(Resource<SimpleModel> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar(false);
                                    ((MainActivity)getActivity()).showToast(resource.getData().getMessage());
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

    private void subscribeCommentObserver() {
        mViewModel.getCommentLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<SimpleModel>>() {
                    @Override
                    public void onChanged(Resource<SimpleModel> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar2(false);
                                    ((MainActivity)getActivity()).showToast(resource.getData().getMessage());
                                    break;
                                case ERROR:
                                    showProgressBar2(false);
                                    ((MainActivity) getActivity()).showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    showProgressBar2(true);
                                    break;
                            }
                        }
                    }
                });
    }

    private void showProgressBar(boolean isLoading) {
        if (isLoading) {
            addEvaluationBtn.startMorphAnimation();
            addEvaluationBtn.setBackground(getActivity().getDrawable(R.drawable.rounded_red_bg));
        } else {
            addEvaluationBtn.stopAnimation();
            addEvaluationBtn.setBackground(getActivity().getDrawable(R.drawable.login_button_shape));

        }
    }
    private void showProgressBar2(boolean isLoading) {
        if (isLoading) {
            addCommentBtn.startMorphAnimation();
            addCommentBtn.setBackground(getActivity().getDrawable(R.drawable.rounded_red_bg));
        } else {
            addCommentBtn.stopAnimation();
            addCommentBtn.setBackground(getActivity().getDrawable(R.drawable.login_button_shape));
        }
    }


    @OnClick(R.id.closeIV)
    public void onCloseIVClicked() {
        dismiss();
    }

    @OnClick(R.id.addEvaluationBtn)
    public void onAddEvaluationBtnClicked() {
        String token = ((MainActivity) getActivity()).getAccessToken();
        float rate = rateRB.getRating();
        if (rate == 0){
            ((MainActivity)getActivity()).showToast(getString(R.string.rate_cant_be_zero));
        }
        if (!TextUtils.isEmpty(token) && productId != 0) {
            mViewModel.invokeRateApi(token, productId, (long) rate);
        }
    }

    @OnClick(R.id.addcommentbtn)
    public void onBtnClicked() {
        String token = ((MainActivity) getActivity()).getAccessToken();
        String comment = commentET.getText().toString();
        if (TextUtils.isEmpty(comment)){
            commentET.setError(getString(R.string.comment_cant_be_empty));
        }
        if (!TextUtils.isEmpty(token) && productId != 0) {
            mViewModel.invokeCommentApi(productId, token, comment);
        }
    }
}