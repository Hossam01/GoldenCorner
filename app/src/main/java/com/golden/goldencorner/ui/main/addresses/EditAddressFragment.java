package com.golden.goldencorner.ui.main.addresses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.model.AddressRecords;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.ui.main.MainActivity;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EditAddressFragment extends Fragment {


    @BindView(R.id.addressNameET)
    EditText addressNameET;
    @BindView(R.id.userNameET)
    EditText userNameET;
    @BindView(R.id.userMobileET)
    EditText userMobileET;
    @BindView(R.id.userEmailET)
    EditText userEmailET;
    @BindView(R.id.userCityET)
    EditText userCityET;
    @BindView(R.id.userNeighborhoodET)
    EditText userNeighborhoodET;
    @BindView(R.id.userStreetET)
    EditText userStreetET;
    @BindView(R.id.addressNoteTV)
    EditText addressNoteTV;
    @BindView(R.id.saveAddressCBtn)
    CircularProgressButton saveAddressCBtn;
    private AddressesViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this)
                .get(AddressesViewModel.class);
        subscribeAddressActionsObserver();
        fillUiOnReceiveAddressRecord(getArguments());
    }

    private AddressRecords records = null;

    private void fillUiOnReceiveAddressRecord(Bundle arguments) {
        if (arguments != null) {
            records = (AddressRecords) arguments.getSerializable(AppConstant.ADDRESS_RECORD);
            if (records != null) {
                addressNameET.setText(getContext().getString(R.string.address_name) + ": " + records.getName());
                userNameET.setText(getContext().getString(R.string.name) + ": " + records.getUser().getName());
                userCityET.setText(getContext().getString(R.string.city) + ": " + records.getUser().getCity());
                userEmailET.setText(getContext().getString(R.string.email) + ": " + records.getUser().getEmail());
                userMobileET.setText(getContext().getString(R.string.mobile_number) + ": " + records.getUser().getMobile());
                userNeighborhoodET.setText(getContext().getString(R.string.neighborhood) + ": " + records.getState());
                userStreetET.setText(getContext().getString(R.string.street) + ": " + records.getAddress());
                addressNoteTV.setText(getContext().getString(R.string.address_note) + ": " + records.getAddress());
            }
        }
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
                                    ((MainActivity) getActivity()).navToDestination(R.id.nav_addresses);
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
            saveAddressCBtn.startMorphAnimation();
            saveAddressCBtn.setBackground(getContext().getDrawable(R.drawable.rounded_red_bg));
        } else {
            saveAddressCBtn.stopAnimation();
            saveAddressCBtn.setBackground(getContext().getDrawable(R.drawable.login_button_shape));
        }
    }


    @OnClick(R.id.saveAddressCBtn)
    public void onViewClicked() {
        String token = ((MainActivity) getActivity()).getAccessToken();

        String addressName = addressNameET.getText().toString();
        String userName = userNameET.getText().toString();
        String userCity = userCityET.getText().toString();
        String userEmail = userEmailET.getText().toString();
        String userMobile = userMobileET.getText().toString();
        String userNeighborhood = userNeighborhoodET.getText().toString();
        String userStreet = userStreetET.getText().toString();
        String addressNote = addressNoteTV.getText().toString();

        String lat = records.getLate();
        String lang = records.getLang();
        long isDefault = 0;
        String map_location = "";

        mViewModel.invokeUpdateAddressApi(token,
                records.getId(),
                userName,
                addressName,
                userCity,
                userStreet,
                lat, lang,
                isDefault,
                map_location);
    }

}