package com.golden.goldencorner.ui.ads;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.AdsRecords;
import com.golden.goldencorner.ui.base.BaseActivity;
import com.golden.goldencorner.ui.main.MainActivity;
import com.gt.playnow.data.ui.indefinitepagerindicator.IndefinitePagerIndicator;
import com.yariksoffice.lingver.Lingver;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.golden.goldencorner.data.Utils.AppConstant.ARABIC_LANGUAGE;
import static com.golden.goldencorner.data.Utils.AppConstant.ENGLISH_LANGUAGE;

public class AdsActivity extends BaseActivity {

    @BindView(R.id.langLiftBtn)
    Button langLiftBtn;
    @BindView(R.id.langRightBtn)
    Button langRightBtn;
    @BindView(R.id.skipLiftBtn)
    Button skipLiftBtn;
    @BindView(R.id.skipRightBtn)
    Button skipRightBtn;
    @BindView(R.id.categoriesRV)
    RecyclerView adsRecyclerView;
    @BindView(R.id.mDilatingDotsProgressBar)
    DilatingDotsProgressBar mDilatingDotsProgressBar;
    @BindView(R.id.recyclerview_pager_indicator)
    IndefinitePagerIndicator recyclerviewPagerIndicator;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private AdsViewModel mViewModel;
    @Inject
    AdsAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        ButterKnife.bind(this);
        hideNavigation();
        mViewModel = ViewModelProviders.of(this)
                .get(AdsViewModel.class);
        if (mAdapter == null)
            mAdapter = new AdsAdapter();
        adsRecyclerView.setAdapter(mAdapter);
        new PagerSnapHelper().attachToRecyclerView(adsRecyclerView);

        subscribeAdsObserver();
        mViewModel.invokeAdsApi(this);
        Lingver.getInstance().setLocale(this, ARABIC_LANGUAGE);
        SharedPreferencesManager.saveCurrentLang(ARABIC_LANGUAGE);
        recyclerviewPagerIndicator.attachToRecyclerView(adsRecyclerView);

    }

    private void subscribeAdsObserver() {
        mViewModel.getAdsLiveData().observe(this,
                new Observer<Resource<List<AdsRecords>>>() {
                    @Override
                    public void onChanged(Resource<List<AdsRecords>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar(false);
                                  //  mDilatingDotsProgressBar.setNumberOfDots(resource.getData().size());
                                    mAdapter.fillAdapterData(resource.getData(),getApplicationContext());
                                    recyclerviewPagerIndicator.setDotCount(resource.getData().size());
                                    break;
                                case ERROR:
                                    showProgressBar(false);
                                    showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    showProgressBar(true);
                                    break;
                            }
                        }
                    }
                });
    }

    private void showProgressBar(boolean isLoading) {
        if (isLoading) {
            mDilatingDotsProgressBar.show();
        } else {
            mDilatingDotsProgressBar.hideNow();
        }
    }

    @OnClick(R.id.langLiftBtn)
    public void onLangLiftBtnClicked() {
        Lingver.getInstance().setLocale(this, ENGLISH_LANGUAGE);
        SharedPreferencesManager.saveCurrentLang(ENGLISH_LANGUAGE);
        //restartApp();
        skip();
    }

    @OnClick(R.id.langRightBtn)
    public void onLangRightBtnClicked() {
        Lingver.getInstance().setLocale(this, ARABIC_LANGUAGE);
        SharedPreferencesManager.saveCurrentLang(ARABIC_LANGUAGE);
        //restartApp();
        skip();
    }

    @OnClick({R.id.skipLiftBtn
            , R.id.skipRightBtn})
    public void onSkipLiftBtnClicked() {
        skip();
    }

    public void skip(){
        progressBar.setVisibility(View.VISIBLE);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
    private void hideNavigation() {
        // Removal of status and navigation bar
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

}