package com.golden.goldencorner.ui.main.home;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.Utils.PaginationScrollListener;
import com.golden.goldencorner.data.model.Category;
import com.golden.goldencorner.data.model.HomeSliderRecords;
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.ui.main.MainActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment implements HomeCategoriesAdapter.AdapterListener, HomeSliderAdapter.AdapterListener {

    @BindView(R.id.categoriesRV)
    RecyclerView categoriesRV;
    @BindView(R.id.imageSliderHome)
    SliderView imageSliderHome;
    @BindView(R.id.collapseArrow)
    ImageView arrow;
    @BindView(R.id.expandView)
    View expandView;
    @BindView(R.id.expandArrow)
    ImageView expandArrow;
    private HomeViewModel mViewModel;
    private HomeCategoriesAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ((MainActivity) getActivity()).titleToolbarIv.setText(getString(R.string.menu_home));
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        subscribeMetaObserver();
        subscribeSliderObserver();
        subscribeCategoriesObserver();
        mAdapter = new HomeCategoriesAdapter();
        categoriesRV.setAdapter(mAdapter);
        categoriesRV.addOnScrollListener(getScrollListener());
        mAdapter.mListener = this;
        long page = 1;
        mViewModel.invokeHomeSliderApi();
        mViewModel.invokeCategoriesApi(getContext(), page);
        categoriesRV.addItemDecoration(new MemberItemDecoration());
        setUpSliderView();


    }

    @Override
    public void onStart() {
        super.onStart();
        setUpSliderView();

    }

    @Override
    public void onResume() {
        super.onResume();
        setUpSliderView();
    }

    private HomeSliderAdapter mSliderAdapter;

    private void setUpSliderView() {
        mSliderAdapter = new HomeSliderAdapter(getContext());
        mSliderAdapter.mListener = this;
        imageSliderHome.setSliderAdapter(mSliderAdapter);
        mSliderAdapter.notifyDataSetChanged();
        imageSliderHome.setIndicatorAnimation(IndicatorAnimationType.WORM);
        imageSliderHome.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSliderHome.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSliderHome.setIndicatorUnselectedColor(Color.WHITE);
        imageSliderHome.setIndicatorSelectedColor(getResources().getColor(R.color.colorAccent));
        imageSliderHome.setScrollTimeInSec(4); //set scroll delay in seconds
        imageSliderHome.startAutoCycle();
        imageSliderHome.setIndicatorEnabled(true);
    }

    private Meta meta = null;

    private void subscribeMetaObserver() {
        mViewModel.getMetaLiveData().observe(getViewLifecycleOwner(),
                new Observer<Meta>() {
                    @Override
                    public void onChanged(Meta metaResponse) {
                        meta = metaResponse;
                    }
                });
    }

    private void subscribeSliderObserver() {
        mViewModel.getHomeSlider().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<HomeSliderRecords>>>() {
                    @Override
                    public void onChanged(Resource<List<HomeSliderRecords>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar(false);
//                                    mDilatingDotsProgressBar.setNumberOfDots(resource.getData().size());
                                    mSliderAdapter.fillAdapterData(resource.getData());
                                    break;
                                case ERROR:
                                    showProgressBar(false);
//                                    showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    showProgressBar(true);
                                    break;
                            }
                        }
                    }
                });
    }

    private void subscribeCategoriesObserver() {
        mViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<Category>>>() {
                    @Override
                    public void onChanged(Resource<List<Category>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar(false);
                                    mAdapter.fillAdapterData(resource.getData());
                                    break;
                                case ERROR:
                                    showProgressBar(false);
                                    if (resource.getMessage() != null)
//                                    showToast(resource.getMessage());
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
        this.isLoading = isLoading;
        ((MainActivity) getActivity()).showProgressBar(isLoading);
    }

    @Override
    public void onCategoriesClicked(Category record) {
        Bundle bundle = new Bundle();
        bundle.putLong(AppConstant.FLAG_CATEGORY_ID, record.getId());
        bundle.putString(AppConstant.FLAG_CATEGORY_TITLE, record.getTitle());
        ((MainActivity) getActivity()).navToDestination(R.id.nav_sub_categories, bundle);
    }

    @Override
    public void onSliderClicked(HomeSliderRecords record) {
        Bundle bundle = new Bundle();
        bundle.putLong(AppConstant.FLAG_CATEGORY_ID, record.getId());
        bundle.putString(AppConstant.FLAG_CATEGORY_TITLE, record.getTitle());
        ((MainActivity) getActivity()).navToDestination(R.id.nav_sub_categories, bundle);
    }


    private boolean isLoading = false;

    private RecyclerView.OnScrollListener getScrollListener() {
        return new PaginationScrollListener() {
            @Override
            protected void loadMoreItems() {
                Long branchId = ((MainActivity) getActivity()).getSelectedBranchId();
                if (meta != null && branchId >= 1 && !isLoading) {
                    isLoading = true;
                    mViewModel.invokeCategoriesApi(getContext(), meta.getNumberOfPage());
                }
            }

            @Override
            public int getPageIndex() {
                return (meta == null) ? 0 : meta.getNumberOfPage();
            }

            @Override
            public int getTotalPageCount() {
                return (meta == null) ? 0 : meta.getTotalCount();
            }

            @Override
            public boolean isLastPage() {
                return (meta == null) ? false : (meta.getCurrentPage() == meta.getTotalCount());
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        };
    }

    @OnClick({R.id.expandArrow, R.id.collapseArrow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.expandArrow:
                imageSliderHome.setVisibility(View.VISIBLE);
                expandView.setVisibility(View.GONE);
                break;
            case R.id.collapseArrow:
                imageSliderHome.setVisibility(View.GONE);
                expandView.setVisibility(View.VISIBLE);
                break;
        }
    }

    class MemberItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            // only for the last one
            if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
                outRect.bottom = 50/* set your margin here */;
            }
        }
    }
}