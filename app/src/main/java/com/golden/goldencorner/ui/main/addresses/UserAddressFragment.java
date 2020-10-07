package com.golden.goldencorner.ui.main.addresses;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.model.AddressRecords;
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.ui.main.MainActivity;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserAddressFragment extends Fragment implements AddressesAdapter.AdapterListener {


    @BindView(R.id.addNewAddressBtn)
    Button addNewAddressBtn;
    @BindView(R.id.addressRV)
    RecyclerView addressRV;
    private AddressesViewModel mViewModel;
    private AddressesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).titleToolbarIv.setText(getString(R.string.my_addresses));
        return inflater.inflate(R.layout.fragment_user_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this)
                .get(AddressesViewModel.class);
        mAdapter = new AddressesAdapter();
        mAdapter.mListener = this;
        addressRV.setAdapter(mAdapter);
        subscribeMetaObserver();
        subscribeAddressesObserver();
        subscribeAddressActionsObserver();

        String token = ((MainActivity) getActivity()).getAccessToken();
        if (TextUtils.isEmpty(token)){
            ((MainActivity) getActivity()).showToast(getString(R.string.login_or_create_new_account_and_try_again));
        } else {
            mViewModel.invokeAddressesApi(token);
        }
    }

    private Meta meta = null;
    private void subscribeMetaObserver() {
        mViewModel.getMetaLiveData().observe(getViewLifecycleOwner(),
                new Observer<Meta>() {
                    @Override
                    public void onChanged(Meta metaResponse) {
                        UserAddressFragment.this.meta = metaResponse;
                    }
                });
    }
    private void subscribeAddressActionsObserver() {
        mViewModel.getAddressActionsLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<SimpleModel>>() {
                    @Override
                    public void onChanged(Resource<SimpleModel> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar(false);
                                    ((MainActivity) getActivity()).showToast(resource.getData().getMessage());
                                    mViewModel.invokeAddressesApi(((MainActivity) getActivity()).getAccessToken());
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
    private void subscribeAddressesObserver() {
        mViewModel.getAddressesLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<AddressRecords>>>() {
                    @Override
                    public void onChanged(Resource<List<AddressRecords>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar(false);
                                    mAdapter.fillAdapterData(resource.getData());
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
    private void showProgressBar(boolean isLoading) {
        if (isLoading) {
//            mDilatingDotsProgressBar.show();
        } else {
//            mDilatingDotsProgressBar.hideNow();
        }
    }

    @OnClick(R.id.addNewAddressBtn)
    public void onViewClicked() {
        ((MainActivity) getActivity()).navToDestination(R.id.nav_add_address);
    }

    @Override
    public void onEditAddress(AddressRecords record) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.ADDRESS_RECORD, record);
        ((MainActivity) getActivity()).navToDestination(R.id.nav_edit_addresses, bundle);
    }

    @Override
    public void onDeleteAddress(AddressRecords record) {
        String token = ((MainActivity) getActivity()).getAccessToken();
        if (TextUtils.isEmpty(token)){
            ((MainActivity) getActivity()).showToast(getString(R.string.login_or_create_new_account_and_try_again));
        } else {
            mViewModel.invokeDeleteAddressApi(token, record.getId());
        }
    }
}