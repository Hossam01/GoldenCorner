package com.golden.goldencorner.ui.main.aboutApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.AboutRecords;
import com.golden.goldencorner.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AboutFragment extends Fragment {

    @BindView(R.id.titleTV)
    TextView titleTV;
    @BindView(R.id.viewLine)
    View viewLine;
    @BindView(R.id.aboutIV)
    ImageView aboutIV;
    @BindView(R.id.textTV)
    TextView textTV;
    private AboutViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).titleToolbarIv.setText(getString(R.string.menu_about));
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this).get(AboutViewModel.class);
        subscribeObserver();
        mViewModel.invokeAboutApi();
    }

    private void subscribeObserver() {
        mViewModel.getAboutLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<AboutRecords>>() {
                    @Override
                    public void onChanged(Resource<AboutRecords> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
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

    private void fillUiData(AboutRecords records) {
        String currentLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
        if (currentLanguage.equalsIgnoreCase(AppConstant.ARABIC_LANGUAGE)){
            titleTV.setText(HtmlCompat.fromHtml(records.getTitle(), HtmlCompat.FROM_HTML_MODE_COMPACT));
            textTV.setText(HtmlCompat.fromHtml(records.getText(), HtmlCompat.FROM_HTML_MODE_COMPACT));
        } else {
            titleTV.setText(HtmlCompat.fromHtml(records.getTitleEn(), HtmlCompat.FROM_HTML_MODE_COMPACT));
            textTV.setText(HtmlCompat.fromHtml(records.getTextEn(), HtmlCompat.FROM_HTML_MODE_COMPACT));
        }
        Glide.with(getContext()).load(records.getImage()).into(aboutIV);
    }
}