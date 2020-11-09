package com.golden.goldencorner.ui.main.DiscounteCode;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.ui.main.DiscounteCode.Model.DataItem;
import com.golden.goldencorner.ui.main.MainActivity;

import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCode extends DialogFragment {
    public DiscounteData mCallback;
    @BindView(R.id.commentET)
    EditText commentET;
    @BindView(R.id.addCode)
    CircularProgressButton addCode;
    @BindView(R.id.closeIV)
    ImageView closeIV;
    double collect;
    private DiscounteCodeViewModel mViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (DiscounteData) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataCommunication");
        }
    }

    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.discount_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this)
                .get(DiscounteCodeViewModel.class);
        collect = getArguments().getDouble("collect");

    }


    private void subscribeRateObserver() {
        mViewModel.getOrderLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<DataItem>>>() {
                    @Override
                    public void onChanged(Resource<List<DataItem>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    ((MainActivity) getActivity()).showProgressBar(false);
                                    ((MainActivity) getActivity()).showToast(resource.getData().get(0).getMessage());
                                    mCallback.data(Double.valueOf(resource.getData().get(0).getDiscount()), commentET.getText().toString(), collect);
                                    Log.e("disocunt", resource.getData().get(0).getMessage());
                                    dismiss();

                                    break;
                                case ERROR:
                                    ((MainActivity) getActivity()).showProgressBar(false);
                                    ((MainActivity) getActivity()).showToast("error");

                                    break;
                                case LOADING:
                                    ((MainActivity) getActivity()).showProgressBar(true);
                                    break;
                            }
                        }
                    }
                });
    }

    @OnClick({R.id.addCode, R.id.closeIV})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.addCode) {
            String token = ((MainActivity) getActivity()).getAccessToken();
            String code = commentET.getText().toString();
            mViewModel.invokeOrderApi(token, code);
            subscribeRateObserver();

        } else if (view.getId() == R.id.closeIV) {
            dismiss();
        }
    }

}
