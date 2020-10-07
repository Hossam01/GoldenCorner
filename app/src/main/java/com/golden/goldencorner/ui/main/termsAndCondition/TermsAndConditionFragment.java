package com.golden.goldencorner.ui.main.termsAndCondition;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.TermsAndConditionsRecords;

import butterknife.BindView;

public class TermsAndConditionFragment extends Fragment {

    @BindView(R.id.titleTV)
    TextView titleTV;
    @BindView(R.id.termsAndConditionsTV)
    TextView termsAndConditionsTV;
    private TermsAndConditionViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.terms_and_condition_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TermsAndConditionViewModel.class);
        subscribeObserver();
        mViewModel.invokeTermsApi();
    }

    private void subscribeObserver() {
        mViewModel.getTermsLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<TermsAndConditionsRecords>>() {
                    @Override
                    public void onChanged(Resource<TermsAndConditionsRecords> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    TermsAndConditionsRecords data = resource.getData();
                                    fillUiData(resource.getData());
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

    private void fillUiData(TermsAndConditionsRecords records) {
        String currentLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
        if (currentLanguage.equalsIgnoreCase(AppConstant.ARABIC_LANGUAGE)) {
            termsAndConditionsTV.setText(HtmlCompat.fromHtml(records.getContent(), HtmlCompat.FROM_HTML_MODE_COMPACT));
        } else {
            termsAndConditionsTV.setText(HtmlCompat.fromHtml(records.getContent(), HtmlCompat.FROM_HTML_MODE_COMPACT));
        }
        titleTV.setText(HtmlCompat.fromHtml(records.getTitle(), HtmlCompat.FROM_HTML_MODE_COMPACT));
    }

}