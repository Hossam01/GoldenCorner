package com.golden.goldencorner.ui.main.userOrders;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.PaginationScrollListener;
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.data.model.OrderRecords;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserOrdersFragment extends Fragment implements OrdersAdapter.AdapterListener {

    @BindView(R.id.ordersRV)
    RecyclerView ordersRV;
    private UserOrdersViewModel mViewModel;
    private OrdersAdapter mAdapter;
    private List<OrderRecords> dataList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).titleToolbarIv.setText(getString(R.string.menu_my_orders));
        return inflater.inflate(R.layout.my_orders_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this)
                .get(UserOrdersViewModel.class);

        mAdapter = new OrdersAdapter();
        ordersRV.setAdapter(mAdapter);
        ordersRV.addOnScrollListener(getScrollListener());
        mAdapter.mListener = this;
        subscribeOrderObserver();
        subscribeMetaObserver();
        subscribeActionsObserver();

        String token = ((MainActivity) getActivity()).getAccessToken();
        long page = (meta == null) ? 1 : meta.getNumberOfPage();
        if (!TextUtils.isEmpty(token)) {
            mViewModel.invokeOrderApi(token, page);
        }
    }

    private Meta meta = null;

    private void subscribeMetaObserver() {
        mViewModel.getMetaLiveData().observe(getViewLifecycleOwner(),
                new Observer<Meta>() {
                    @Override
                    public void onChanged(Meta metaResponse) {
                        UserOrdersFragment.this.meta = metaResponse;
                    }
                });
    }

    private void subscribeOrderObserver() {
        mViewModel.getOrderLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<OrderRecords>>>() {
                    @Override
                    public void onChanged(Resource<List<OrderRecords>> resource) {
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
        mViewModel.getDeleteLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<SimpleModel>>() {
                    @Override
                    public void onChanged(Resource<SimpleModel> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar(false);
                                    ((MainActivity) getActivity()).showToast(resource.getData().getMessage());
                                    String token = ((MainActivity) getActivity()).getAccessToken();
                                    long page = (meta == null) ? 1 : meta.getNumberOfPage();
                                    if (!TextUtils.isEmpty(token)) {
                                        mViewModel.invokeOrderApi(token, page);
                                    }
                                    mAdapter.notifyDataSetChanged();
                                    ((MainActivity) getActivity()).navToDestination(R.id.nav_my_orders);
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
        ((MainActivity) getActivity()).showProgressBar(flag);
    }

    @Override
    public void onCancelOrderClicked(OrderRecords record) {
        new AlertDialog.Builder(getActivity())
                .setMessage(getString(R.string.are_you_sure_to_cancel_this_order))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String token = ((MainActivity) getActivity()).getAccessToken();
                        mViewModel.invokeDeleteApi(token, record.getId());
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onContactDriverClicked(OrderRecords record) {
        ((MainActivity)getActivity()).getRxPermissions()
                .request(Manifest.permission.CALL_PHONE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        if (TextUtils.isEmpty(record.getDriver().getMobile())) {
                            ((MainActivity) getActivity()).showToast(getString(R.string.no_mobile_number));
                        } else {
                            Bundle b = new Bundle();
                            b.putString("DriverName", record.getDriver().getName());
                            b.putString("DriverPhone", record.getDriver().getMobile());
                            ((MainActivity) getActivity()).navToDestination(R.id.nav_driver_info, b);
                        }
                    }
                });
    }

    @Override
    public void onOrderDetailsClicked(OrderRecords record) {
        Bundle bundle = new Bundle();
        bundle.putLong("orderId", record.getId());
        ((MainActivity) getActivity()).navToDestination(R.id.orderDetailsFragment, bundle);
    }

    @Override
    public void OnOrderEvaluateClicked(OrderRecords record) {
        //todo comment because crashing
        Bundle bundle = new Bundle();
        bundle.putLong("orderId", record.getId());
        bundle.putString("driverId", record.getDriver().getId());
        ((MainActivity) getActivity()).navToDestination(R.id.orderRateFragment, bundle);
    }

    private boolean isLoading = false;
    private RecyclerView.OnScrollListener getScrollListener() {
        return new PaginationScrollListener() {
            @Override
            protected void loadMoreItems() {
                Long branchId = ((MainActivity) getActivity()).getSelectedBranchId();
                if (meta != null && branchId >= 1 && !isLoading) {
                    isLoading = true;
                    String token = ((MainActivity) getActivity()).getAccessToken();
                    mViewModel.invokeOrderApi(token, meta.getNumberOfPage());
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
}