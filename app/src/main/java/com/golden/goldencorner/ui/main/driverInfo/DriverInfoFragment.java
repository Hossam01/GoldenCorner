package com.golden.goldencorner.ui.main.driverInfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DriverInfoFragment extends DialogFragment {

    @BindView(R.id.driverIV)
    ImageView driverIV;
    @BindView(R.id.driverNameTv)
    TextView driverNameTv;
    @BindView(R.id.numberTv)
    TextView numberTv;
    @BindView(R.id.callDriverBtn)
    CircularProgressButton callDriverBtn;

    String phone, name;

    private DriverInfoViewModel mViewModel;

    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.driver_info_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this)
                .get(DriverInfoViewModel.class);

        if (getArguments() != null) {
            name = getArguments().getString("DriverName").toString();
            phone = getArguments().getString("DriverPhone").toString();
        }
        driverNameTv.setText(name);
        numberTv.setText(phone);
    }

    @OnClick({R.id.callDriverBtn, R.id.closeIV})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.callDriverBtn) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            getActivity().startActivity(intent);
        } else if (view.getId() == R.id.closeIV) {
            dismiss();
        }
    }

}