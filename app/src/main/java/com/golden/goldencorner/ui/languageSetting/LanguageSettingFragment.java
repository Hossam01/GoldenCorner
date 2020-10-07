package com.golden.goldencorner.ui.languageSetting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.ui.main.MainActivity;
import com.yariksoffice.lingver.Lingver;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LanguageSettingFragment extends Fragment {

    @BindView(R.id.arabicRBtn)
    RadioButton arabicRBtn;
    @BindView(R.id.englishRBtn)
    RadioButton englishRBtn;
    @BindView(R.id.langRG)
    RadioGroup langRG;
    @BindView(R.id.sendBtn)
    CircularProgressButton sendBtn;
    private LanguageSettingViewModel mViewModel;
    private String mLang = SharedPreferencesManager.getCurrentLang();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).titleToolbarIv.setText(getString(R.string.language));
        return inflater.inflate(R.layout.language_setting_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this)
                .get(LanguageSettingViewModel.class);

        langRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.arabicRBtn:
                        // do operations specific to this selection
                        mLang = AppConstant.ARABIC_LANGUAGE;
                        break;
                    case R.id.englishRBtn:
                        // do operations specific to this selection
                        mLang = AppConstant.ENGLISH_LANGUAGE;
                        break;

                }
            }
        });

    }

    @OnClick(R.id.sendBtn)
    public void onViewClicked() {
        changeLan();
    }


    private final void changeLan() {
        Lingver.getInstance().setLocale(getContext(), mLang);
        SharedPreferencesManager.saveCurrentLang(mLang);
        restartApp();
    }

    public final void restartApp() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}