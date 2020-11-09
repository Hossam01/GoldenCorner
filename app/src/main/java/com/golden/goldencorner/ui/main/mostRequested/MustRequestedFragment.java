package com.golden.goldencorner.ui.main.mostRequested;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.data.model.Product;
import com.golden.goldencorner.data.model.ProductResponse;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.ui.main.MainActivity;
import com.golden.goldencorner.ui.main.offers.ProductsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MustRequestedFragment extends Fragment implements ProductsAdapter.AdapterListener {

    @BindView(R.id.productRV)
    RecyclerView productRV;
    private MustRequestedViewModel mViewModel;
    private ProductsAdapter mAdapter;
    private List<Product> productList = new ArrayList<>();
    private Meta meta = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).titleToolbarIv.setText(getString(R.string.offers));
        return inflater.inflate(R.layout.most_requested_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this).get(MustRequestedViewModel.class);
        mAdapter = new ProductsAdapter();
        mAdapter.mListener = this;
        productRV.setAdapter(mAdapter);
        productRV.addOnScrollListener(getScrollListener());
        subscribeObserver();
        subscribeActionsObserver();

        long branchId = ((MainActivity) getActivity()).getSelectedBranchId();
        int page = 1;
        if (meta != null)
            page = meta.getCurrentPage();

        if (branchId >= 1)
            mViewModel.invokeProductApi(branchId, page);
        else
            ((MainActivity) getActivity()).navToDestination(R.id.nav_branches);
    }

    private void subscribeActionsObserver() {
        mViewModel.getAddToFavLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<SimpleModel>>() {
                    @Override
                    public void onChanged(Resource<SimpleModel> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar(false);
                                    ((MainActivity) getActivity()).showToast(resource.getData().getMessage());
                                    break;
                                case ERROR:
                                    showProgressBar(false);
                                    ((MainActivity) getActivity()).showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    showProgressBar(true);
                                    break;
                            }
                        }
                    }
                });
    }

    private void subscribeObserver() {
        mViewModel.getProductLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<ProductResponse>>() {
                    @Override
                    public void onChanged(Resource<ProductResponse> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar(false);
                                    productList.clear();
                                    meta = resource.getData().getData().get(0).getMeta();
                                    productList.addAll(resource.getData().getData().get(0).getItems());
                                    mAdapter.fillAdapterData(productList);
                                    break;
                                case ERROR:
                                    showProgressBar(false);
                                    break;
                                case LOADING:
                                    showProgressBar(true);
                                    break;
                            }
                        }
                    }
                });
    }

    private void showProgressBar(boolean flag) {
        isLoading = flag;
        if (flag){

        } else {

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
                    mViewModel.invokeProductApi(branchId, meta.getNumberOfPage());
                }
            }
            @Override
            public int getPageIndex() {
                return (meta == null)?0:meta.getNumberOfPage();
            }
            @Override
            public int getTotalPageCount() {
                return (meta == null)?0:meta.getTotalCount();
            }
            @Override
            public boolean isLastPage() {
                return (meta == null)?false:(meta.getCurrentPage() == meta.getTotalCount());
            }
            @Override
            public boolean isLoading() {
                return isLoading;
            }
        };
    }

    @Override
    public void onAddToCart(Product record) {
        Bundle bundle = new Bundle();
        bundle.putLong(AppConstant.PRODUCT_ID, record.id);
        bundle.putString("image", record.getImage());
        // ((MainActivity) getActivity()).navToDestination(R.id.nav_order_evaluate);
        ((MainActivity) getActivity()).navToDestination(R.id.nav_order_view, bundle);
    }

    @Override
    public void onAddOrDeleteFavorites(Product record) {
        String token = ((MainActivity)getActivity()).getAccessToken();
        if (TextUtils.isEmpty(token)){
            ((MainActivity) getActivity()).showToast(getString(R.string.login_or_create_new_account_and_try_again));
        } else {
            if (record.getIsFavorite() == 0) {
                mViewModel.invokeAddToFavoritesApi(token, record.getId());
            } else {
                mViewModel.invokeDeleteFavoritesApi(token, record.getId());
            }
        }
    }

}