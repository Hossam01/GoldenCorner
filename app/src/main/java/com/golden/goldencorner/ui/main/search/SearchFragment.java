package com.golden.goldencorner.ui.main.search;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.LogUtils;
import com.golden.goldencorner.data.Utils.PaginationScrollListener;
import com.golden.goldencorner.data.Utils.Utils;
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

import static com.golden.goldencorner.data.Utils.Utils.utils;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener, ProductsAdapter.AdapterListener {

    public static final String TAG = SearchFragment.class.getSimpleName();
    @BindView(R.id.productsRV)
    RecyclerView productsRV;
    @BindView(R.id.searchViewId)
    SearchView searchView;
    private SearchViewModel mViewModel;
    private ProductsAdapter mAdapter;
    private List<Product> productList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        mAdapter = new ProductsAdapter();
        productsRV.setAdapter(mAdapter);
        mAdapter.mListener = this;
        productsRV.addOnScrollListener(getScrollListener());
        setUpSearchViewUI(view);
        subscribeObserver();
        subscribeMetaObserver();
        subscribeActionsObserver();
    }

    private void setUpSearchViewUI(@NonNull View view) {
        try {
            if (searchView == null)
                searchView = view.findViewById(R.id.searchViewId);
            searchView.setOnQueryTextListener(this);
            searchView.setOnCloseListener(this);
            searchView.requestFocus(View.LAYOUT_DIRECTION_LOCALE);
            searchView.setFocusable(true);
            searchView.setEnabled(true);
            int editTextId = searchView.getContext().getResources().getIdentifier(
                    "android:id/search_src_text", null, null);
            EditText searchEditText = searchView.findViewById(editTextId);
            searchEditText.setTextColor(getResources().getColor(R.color.whiteColor));
            //searchEditText.setTextSize(26);
            //searchEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 18);
        } catch (Resources.NotFoundException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        utils.hideSoftKeyboard(this.getView());
        return onSubmitSearchQuery(query.trim());
    }
    @Override
    public boolean onQueryTextChange(String query) {
        return onSubmitSearchQuery(query.trim());
    }
    @Override
    public boolean onClose() {
        return onSubmitSearchQuery("");
    }

    private boolean onSubmitSearchQuery(String query) {
        if (!TextUtils.isEmpty(query) && Utils.getInstance().isNetworkConnected(getContext())) {
            long page = (meta == null)?1: meta.getNumberOfPage();
            long branchId = ((MainActivity) getActivity()).getSelectedBranchId();
            if (branchId == 0)
                ((MainActivity) getActivity()).navToDestination(R.id.nav_branches);
            else
                mViewModel.invokeProductApi(query, branchId, page);
            return true;
        }
        return false;
    }

    private Meta meta = null;
    private void subscribeMetaObserver() {
        mViewModel.getMetaLiveData().observe(getViewLifecycleOwner(),
                new Observer<Meta>() {
                    @Override
                    public void onChanged(Meta metaResponse) {
                        SearchFragment.this.meta = metaResponse;
                    }
                });
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
        if (flag) {

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
//                    mViewModel.invokeProductApi(branchId, meta.getNumberOfPage());
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

    @Override
    public void onAddToCart(Product record) {
        ((MainActivity)getActivity()).addProductToCard(record);
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