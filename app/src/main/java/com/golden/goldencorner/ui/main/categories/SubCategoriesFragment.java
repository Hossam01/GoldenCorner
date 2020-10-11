package com.golden.goldencorner.ui.main.categories;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.data.model.Product;
import com.golden.goldencorner.ui.main.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SubCategoriesFragment extends Fragment implements SubCategoryProductAdapter.AdapterListener, RoundedCategoriesAdapter.AdapterListener {

    @BindView(R.id.topCategoryRV)
    RecyclerView topCategoryRV;
    @BindView(R.id.CategoryRV)
    RecyclerView CategoryRV;
    private RoundedCategoriesAdapter mRoundedAdapter;
    private SubCategoryProductAdapter mSubCategoryProductAdapter;
    private SubCategoriesViewModel mViewModel;
    private Long categoryID = null;
    private String categoryTitle="";
    Resource<List<Category>> listResource;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sub_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this).get(SubCategoriesViewModel.class);
        mRoundedAdapter = new RoundedCategoriesAdapter();
        mRoundedAdapter.mListener = this;
        mSubCategoryProductAdapter = new SubCategoryProductAdapter();
        mSubCategoryProductAdapter.mListener = this;
        topCategoryRV.setAdapter(mRoundedAdapter);
        CategoryRV.setAdapter(mSubCategoryProductAdapter);
        CategoryRV.addOnScrollListener(getScrollListener());
//        subscribeCategoriesObserver();
        subscribeSubCategoriesObserver();
        subscribeProductsBySubCategoryIdObserver();
        subscribeMetaObserver();
//        mViewModel.invokeCategoriesApi(getContext());

        if (getArguments() != null) {
            categoryID = (Long) getArguments().getLong(AppConstant.FLAG_CATEGORY_ID);
              categoryTitle = getArguments().getString(AppConstant.FLAG_CATEGORY_TITLE);
            int page = 1;
            if (categoryID != null) {
                ((MainActivity) getActivity()).titleToolbarIv.setText(categoryTitle);
                mViewModel.invokeSubCategoryByIdApi(categoryID, page);
                long branchId = ((MainActivity) getActivity()).getSelectedBranchId();
                if (branchId >= 1 && categoryID != null) {
                    mViewModel.invokeProductsBySubCategoryIdApi(categoryID, page);
                }
            }
        }


    }

    private Meta meta = null;

    private void subscribeMetaObserver() {
        mViewModel.getProductMetaLiveData().observe(getViewLifecycleOwner(),
                new Observer<Meta>() {
                    @Override
                    public void onChanged(Meta meta) {

                        SubCategoriesFragment.this.meta = meta;
                    }
                });
    }


    private void subscribeSubCategoriesObserver() {
        mViewModel.getSubCategoryByIdLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<Category>>>() {
                    @Override
                    public void onChanged(Resource<List<Category>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar(false);
//                                    mDilatingDotsProgressBar.setNumberOfDots(resource.getData().size());
                                    mRoundedAdapter.fillAdapterData(resource.getData());
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

    private void subscribeProductsBySubCategoryIdObserver() {
        mViewModel.getProductsBySubCategory().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<Product>>>() {
                    @Override
                    public void onChanged(Resource<List<Product>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar(false);
//                                    mDilatingDotsProgressBar.setNumberOfDots(resource.getData().size());
                                    mSubCategoryProductAdapter.fillAdapterData(resource.getData());
                                   // onSubCategoriesClicked((Category) listResource.getData());
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

    private void showProgressBar(boolean isLoading) {
        ((MainActivity) getActivity()).showProgressBar(isLoading);
    }

    @Override
    public void onAddToCartClicked(Product record) {
        Bundle bundle = new Bundle();
        bundle.putLong(AppConstant.PRODUCT_ID, record.id);
        bundle.putString("image", record.getImage());
        ((MainActivity) getActivity()).navToDestination(R.id.nav_order_view, bundle);
    }

    @Override
    public void onSubCategoriesClicked(Category record) {
        long branchId = ((MainActivity) getActivity()).getSelectedBranchId();
        if (branchId >= 1 && meta != null) {
            Log.d("Catrory",record.getTitle().toString());
           // mViewModel.invokeProductsBySubCategoryIdApi(categoryID, meta.getNumberOfPage());
            mViewModel.invokeProductsBySubCategoryIdApifilter(categoryID, meta.getNumberOfPage(),record.getId());
            mSubCategoryProductAdapter.notifyDataSetChanged();

        }

    }

    private boolean isLoading = false;

    private RecyclerView.OnScrollListener getScrollListener() {
        return new PaginationScrollListener() {
            @Override
            protected void loadMoreItems() {
                Long branchId = ((MainActivity) getActivity()).getSelectedBranchId();
                if (meta != null && branchId >= 1 && !isLoading) {
                    isLoading = true;
                    mViewModel.invokeProductsBySubCategoryIdApi(categoryID, meta.getNumberOfPage());
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

}