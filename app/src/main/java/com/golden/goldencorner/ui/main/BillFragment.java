package com.golden.goldencorner.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.golden.goldencorner.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BillFragment extends DialogFragment {

    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.orderNumberTV)
    TextView orderNumberTV;
    @BindView(R.id.closeIV)
    ImageView closeIV;

    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.billing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            orderNumberTV.setText(getArguments().getString("Date"));
            num.setText(String.valueOf(getArguments().getInt("Number")));
        }
    }

    @OnClick(R.id.closeIV)
    public void close() {
        dismiss();
        ((MainActivity) getActivity()).navToDestination(R.id.nav_home);
    }
}
