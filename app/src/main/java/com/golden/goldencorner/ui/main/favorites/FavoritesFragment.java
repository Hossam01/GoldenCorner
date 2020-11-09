package com.golden.goldencorner.ui.main.favorites;

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
import com.golden.goldencorner.data.model.FavDataList;
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.data.model.Product;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoritesFragment extends Fragment implements FavoritesAdapter.AdapterListener {

    @BindView(R.id.searchRV)
    RecyclerView searchRV;
    private FavoritesViewModel mViewModel;
    private FavoritesAdapter mAdapter;
    private List<FavDataList> dataList = new ArrayList<>();

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).titleToolbarIv.setText(getString(R.string.favorites));
        return inflater.inflate(R.layout.favorites_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        mAdapter = new FavoritesAdapter();
        searchRV.setAdapter(mAdapter);
        mAdapter.mListener = this;
        subscribeFavObserver();
        subscribeMetaObserver();
        subscribeActionsObserver();

        String token = ((MainActivity)getActivity()).getAccessToken();
        long page = (meta == null)?1:meta.getNumberOfPage();
        if (!TextUtils.isEmpty(token)){
            mViewModel.invokeFavoritesApi(token, page);
        }
    }
    private Meta meta = null;
    private void subscribeMetaObserver() {
        mViewModel.getMetaLiveData().observe(getViewLifecycleOwner(),
                new Observer<Meta>() {
                    @Override
                    public void onChanged(Meta metaResponse) {
                        FavoritesFragment.this.meta = metaResponse;
                    }
                });
    }

    private void subscribeFavObserver() {
        mViewModel.getFavLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<FavDataList>>>() {
                    @Override
                    public void onChanged(Resource<List<FavDataList>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    dataList.addAll(resource.getData());
                                    showProgressBar(false);
                                    mAdapter.fillAdapterData(dataList);
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
                                    String token = ((MainActivity)getActivity()).getAccessToken();
                                    long page = (meta == null)?1:meta.getNumberOfPage();
                                    if (!TextUtils.isEmpty(token)) {
                                        mViewModel.invokeFavoritesApi(token, page);
                                    }
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
    private void showProgressBar(boolean flag) {
//        isLoading = flag;
        ((MainActivity)getActivity()).showProgressBar(flag);
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
    public void onAddOrDeleteFavorites(FavDataList record) {
        String token = ((MainActivity)getActivity()).getAccessToken();
        if (TextUtils.isEmpty(token)) {
            ((MainActivity) getActivity()).showToast(getString(R.string.login_or_create_new_account_and_try_again));
        } else {
            if (record.getProduct().getIsFavorite() == 0) {
                mViewModel.invokeAddToFavoritesApi(token, record.getId());
            } else {
                mViewModel.invokeDeleteFavoritesApi(token, record.getId());
            }
        }
    }
}