package com.golden.goldencorner.ui.main.addresses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.City;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.ui.main.MainActivity;
import com.golden.goldencorner.ui.main.branches.BranchesViewModel;

import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddAddressFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.addAddressesTitleTV)
    TextView addAddressesTitleTV;
    @BindView(R.id.viewLine)
    View viewLine;
    @BindView(R.id.addressNameTv)
    TextView addressNameTv;
    @BindView(R.id.addressNameET)
    EditText addressNameET;
    @BindView(R.id.cityTv)
    TextView cityTv;
    @BindView(R.id.citySpinnerET)
    Spinner citySpinnerET;
    @BindView(R.id.neighborhoodTv)
    TextView neighborhoodTv;
    @BindView(R.id.neighborhoodET)
    EditText neighborhoodET;
    @BindView(R.id.locationHintTv)
    TextView locationHintTv;
    @BindView(R.id.locationTV)
    public TextView locationTV;
    @BindView(R.id.navLocationBtn)
    ImageButton navLocationBtn;
    @BindView(R.id.selectLocationBtn)
    TextView selectLocationBtn;
    @BindView(R.id.addressTv)
    TextView addressTv;
    @BindView(R.id.addressET)
    EditText addressET;
    @BindView(R.id.addEvaluteBtn)
    CircularProgressButton addAddressBtn;
    private AddressesViewModel mViewModel;
    BranchesViewModel branchesViewModel;
    private ArrayAdapter<String> mSpinnerAdapter = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        locationTV = getActivity().findViewById(R.id.locationTV);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_address_fragment, container, false);
        locationTV = view.findViewById(R.id.locationTV);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this)
                .get(AddressesViewModel.class);

        locationTV = getActivity().findViewById(R.id.locationTV);
        subscribeCitiesObserver();
        subscribeAddressActionsObserver();
        mViewModel.invokeCitiesApi();

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
//            mDilatingDotsProgressBar.show();
        } else {
//            mDilatingDotsProgressBar.hideNow();
        }
    }

    private void subscribeCitiesObserver() {
        mViewModel.getCitiesLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<City>>>() {
                    @Override
                    public void onChanged(Resource<List<City>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    setUpSpinnerUi(resource.getData());
                                    break;
                                case ERROR:
                                    ((MainActivity) getActivity()).showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    break;
                            }
                        }
                    }
                });
    }




    private void setUpSpinnerUi(List<City> dataList) {
        mSpinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinnerET.setAdapter(mSpinnerAdapter);
        citySpinnerET.setOnItemSelectedListener(this);
        for (City records : dataList) {
            String currentLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
            String mLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
            if (currentLanguage.equalsIgnoreCase(mLanguage))
                mSpinnerAdapter.add(records.getName());
            else
                mSpinnerAdapter.add(records.getNameEn());
        }
        mSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSpinnerAdapter.notifyDataSetChanged();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
    @OnClick({R.id.locationTV
            , R.id.navLocationBtn})
    public void onLocationClicked() {

        ((MainActivity) getActivity()).savename(addressNameET.getText().toString());

        MapFragment mapFragment = new MapFragment();
        mapFragment.setTargetFragment(AddAddressFragment.this, 1);
        Bundle bundle = new Bundle();
        bundle.putInt("fragment", 1);
        ((MainActivity) getActivity()).navToDestination(R.id.nav_map_fragment, bundle);
    }


    @Override
    public void onResume() {
        super.onResume();

        //locationTV.setText ( ((MainActivity)getActivity()).getLocation());

    }

    @Override
    public void onPause() {
        super.onPause();
        // locationTV.setText ( ((MainActivity)getActivity()).getLocation());


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  locationTV.setText ( ((MainActivity)getActivity()).getLocation());


    }

    @OnClick(R.id.addEvaluteBtn)
    public void onViewClicked() {
        onAddAddressInoked();
    }
    public void onAddAddressInoked(/*long lat, long lang, String state, String map_location*/) {
        String addressName = addressNameET.getText().toString();
        String citySpinner = citySpinnerET.getSelectedItem().toString();
        String neighborhood = neighborhoodET.getText().toString();
        String location = locationTV.getText().toString();
        String address = addressET.getText().toString();
        if (TextUtils.isEmpty(addressName)){
            addressNameET.setError(getContext().getString(R.string.this_field_is_required));
            return;
        }
        if (TextUtils.isEmpty(citySpinner)){
            ((MainActivity)getActivity()).showToast(getString(R.string.select_your_city));
            return;
        }
        if (TextUtils.isEmpty(neighborhood)){
            neighborhoodET.setError(getContext().getString(R.string.this_field_is_required));
            return;
        }
        if (TextUtils.isEmpty(location)){
            ((MainActivity)getActivity()).showToast(getString(R.string.select_you_location));
            return;
        }
        if (TextUtils.isEmpty(address)){
            addressET.setError(getContext().getString(R.string.this_field_is_required));
            return;
        }
        long lat = SharedPreferencesManager.getLong(AppConstant.LATITUDE);
        long lang = SharedPreferencesManager.getLong(AppConstant.LONGITUDE);
        String state = SharedPreferencesManager.getString(AppConstant.STATE);
        String map_location = SharedPreferencesManager.getString(AppConstant.MAP_LOCATION);

        long isDefault = 0;
        String token = ((MainActivity) getActivity()).getAccessToken();
        if (TextUtils.isEmpty(token)) {
            ((MainActivity) getActivity()).showToast(getString(R.string.login_or_create_new_account_and_try_again));
        } else {
            mViewModel.invokeAddAddressApi(token, addressName, address, state, lat, lang, isDefault, map_location);
        }

    }

    public void settext(String location) {
        locationTV.setText(location);
    }

}