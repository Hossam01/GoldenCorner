package com.golden.goldencorner.ui.DeliveryDate;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.golden.goldencorner.R;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeliveryDateFragment extends DialogFragment {

    public SendTime mCallback;
    @BindView(R.id.hoursSpinner)
    Spinner hoursSpinner;
    @BindView(R.id.minutesSpinner)
    Spinner minutesSpinner;
    @BindView(R.id.addEvaluteBtn)
    CircularProgressButton addEvaluteBtn;
    @BindView(R.id.hourTV)
    TextView hourTV;

    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (SendTime) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataCommunication");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.delivery_date, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        hoursSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hourTV.setText(hoursSpinner.getSelectedItem().toString() + ":" + minutesSpinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        minutesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hourTV.setText(hoursSpinner.getSelectedItem().toString() + ":" + minutesSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @OnClick(R.id.addEvaluteBtn)
    public void onClickView() {
        String hours = hoursSpinner.getSelectedItem().toString();
        String minute = minutesSpinner.getSelectedItem().toString();
        mCallback.sendTime(hours, minute);
        dismiss();
    }
}
