package com.golden.goldencorner.ui.main.creditCards;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.CardRecords;
import com.golden.goldencorner.data.model.CardTypeItem;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.ui.main.MainActivity;

import java.util.Calendar;
import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    final Calendar myCalendar = Calendar.getInstance();
    @BindView(R.id.productMealsTV)
    TextView productMealsTV;
    @BindView(R.id.viewLine)
    View viewLine;
    @BindView(R.id.cardHolderNameTv)
    TextView cardHolderNameTv;
    @BindView(R.id.commentET)
    EditText commentET;
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
    CircularProgressButton addEvaluteBtn;
    @BindView(R.id.cardNumberspinner)
    Spinner cardNumberspinner;
    DatePickerDialog.OnDateSetListener date;
    CardRecords cardRecords;
    ArrayAdapter spinnerAdapterDriver;
    private CardsViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).titleToolbarIv.setText(getString(R.string.credit_cards));

        View view = inflater.inflate(R.layout.fragment_cards, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this).get(CardsViewModel.class);
        mViewModel.invokeCardsApiType();
        subscribeCardTypeObserver();
        cardRecords = getArguments().getParcelable("cardData");
        commentET.setText(cardRecords.getName());
        cardNumberET.setText(cardRecords.getCardNumber());
        cardExpireDateET.setText(cardRecords.getExpired());
        Log.e("type", "" + cardRecords.getType());
        if (cardRecords.getType() == 0)
            cardNumberspinner.setSelection(1);
        else if (cardRecords.getType() == 1)
            cardNumberspinner.setSelection(2);
        else if (cardRecords.getType() == 2)
            cardNumberspinner.setSelection(3);
        else if (cardRecords.getType() == 3)
            cardNumberspinner.setSelection(4);
        else if (cardRecords.getType() == 4)
            cardNumberspinner.setSelection(5);

    }


    private void subscribeCardActionsObserver() {
        mViewModel.getCardActionsLiveData().observe(getViewLifecycleOwner(), new Observer<Resource<SimpleModel>>() {
            @Override
            public void onChanged(Resource<SimpleModel> listResource) {
                if (listResource != null) {
                    switch (listResource.getStatus()) {
                        case SUCCESS:
                            ((MainActivity) getActivity()).showProgressBar(false);
                            ((MainActivity) getActivity()).showToast("Done");

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

    private void subscribeCardTypeObserver() {
        mViewModel.getCardsLiveDataType().observe(getViewLifecycleOwner(), new Observer<Resource<List<CardTypeItem>>>() {
            @Override
            public void onChanged(Resource<List<CardTypeItem>> listResource) {
                if (listResource != null) {
                    switch (listResource.getStatus()) {
                        case SUCCESS:
                            ((MainActivity) getActivity()).showProgressBar(false);
                            setUpSpinnerUi(listResource.getData());
                            break;
                        case ERROR:
                            ((MainActivity) getActivity()).showProgressBar(false);
                            ((MainActivity) getActivity()).showToast(listResource.getMessage());
                            break;
                        case LOADING:
                            ((MainActivity) getActivity()).showProgressBar(true);
                            break;
                    }
                }
            }
        });
    }

    @OnClick(R.id.addEvaluteBtn)
    public void onViewClicked() {
        String citySpinner = cardNumberspinner.getSelectedItem().toString();
        if (commentET.getText().toString().isEmpty()) {
            commentET.setError(getString(R.string.this_field_is_required));
        } else if (cardNumberET.getText().toString().isEmpty()) {
            cardNumberET.setError(getString(R.string.this_field_is_required));
        } else if (cardExpireDateET.getText().toString().isEmpty()) {
            cardExpireDateET.setError(getString(R.string.this_field_is_required));
        } else if (cardCVCET.getText().toString().isEmpty()) {
            cardCVCET.setError(getString(R.string.this_field_is_required));
        } else if (TextUtils.isEmpty(citySpinner)) {
            ((MainActivity) getActivity()).showToast(getString(R.string.select_card));
        } else {
            mViewModel.invokeUpdateAddressApi(((MainActivity) getActivity()).getAccessToken(), cardRecords.getId(), commentET.getText().toString(), Long.valueOf(cardNumberET.getText().toString()), cardExpireDateET.getText().toString());
            subscribeCardActionsObserver();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinnerAdapterDriver.notifyDataSetChanged();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setUpSpinnerUi(List<CardTypeItem> dataList) {
        spinnerAdapterDriver = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item);
        spinnerAdapterDriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardNumberspinner.setAdapter(spinnerAdapterDriver);
        cardNumberspinner.setOnItemSelectedListener(this);
        for (CardTypeItem records : dataList) {
            String currentLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
            String mLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
            if (currentLanguage.equalsIgnoreCase(mLanguage))
                spinnerAdapterDriver.add(records.getTitle());
            else
                spinnerAdapterDriver.add(records.getTitleEn());
        }
        spinnerAdapterDriver.notifyDataSetChanged();
    }
}
