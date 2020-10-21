package com.golden.goldencorner.ui.main.termsAndCondition;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.ResponseTerms;

import butterknife.BindView;

public class TermsAndConditionFragment extends DialogFragment {

    @BindView(R.id.titleTVterms)
    TextView titleTV;
    @BindView(R.id.termsAndConditionsTV)
    TextView termsAndConditionsTV;
    private TermsAndConditionViewModel mViewModel;

    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.terms_and_condition_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TermsAndConditionViewModel.class);
        mViewModel.invokeTermsApi();
        subscribeObserver();
//        titleTV=getActivity().findViewById(R.id.titleTVterms);
//        termsAndConditionsTV=getActivity().findViewById(R.id.termsAndConditionsTV);
    }

    private void subscribeObserver() {
        mViewModel.getTermsLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<ResponseTerms>>() {
                    @Override
                    public void onChanged(Resource<ResponseTerms> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    ResponseTerms data = resource.getData();
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

    private void fillUiData(ResponseTerms records) {
        String currentLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
       // Log.d("test",records.getData().get(0).getTitle());

            if (records.getData().get(0).getTitle() != null)
                titleTV.setText(records.getData().get(0).getTitle());

        if (records.getData().get(0).getContent()!=null) {
            if (currentLanguage.equalsIgnoreCase(AppConstant.ARABIC_LANGUAGE)) {
                Log.d("test", records.getData().get(0).getContent());
                termsAndConditionsTV.setText(HtmlCompat.fromHtml(records.getData().get(0).getContent(), HtmlCompat.FROM_HTML_MODE_COMPACT));
            } else {
                termsAndConditionsTV.setText(HtmlCompat.fromHtml(records.getData().get(0).getContent(), HtmlCompat.FROM_HTML_MODE_COMPACT));
            }
        }
    }


}