package com.golden.goldencorner.ui.main.branches;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.Utils;
import com.golden.goldencorner.data.model.BranchRecords;
import com.golden.goldencorner.ui.main.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BranchesFragment extends Fragment implements OnMapReadyCallback, OnSuccessListener<Location>, BranchesAdapter.AdapterListener {
    private static View view;
    public static final String TAG = BranchesFragment.class.getName();
    @BindView(R.id.branchTitleTV)
    TextView branchTitleTV;
    @BindView(R.id.continueBtn)
    CircularProgressButton continueBtn;
    @BindView(R.id.branchesRV)
    RecyclerView branchesRV;
    private GoogleMap mMap;
    private BranchesViewModel mViewModel;
    private BranchesAdapter mAdapter;
    private FusedLocationProviderClient fusedLocationClient;
    private Location location;
    private String currentAddress;
    private Location currentLocation;
    private List<BranchRecords> dataList = new ArrayList<>();
    SupportMapFragment mapFragment;
    int click = 0;
    double longitude = 0.0;
    double latitude = 0.0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup viewGroupParent = (ViewGroup) view.getParent();
            if (viewGroupParent != null)
                viewGroupParent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.branches_fragment, container, false);
        } catch (Exception e) {
            // map is already there
        }

        ((MainActivity) getActivity()).titleToolbarIv.setText(getString(R.string.branches));
        ButterKnife.bind(this, view);

        mViewModel = ViewModelProviders.of(this)
                .get(BranchesViewModel.class);
        setUpMapUi();

        mAdapter = new BranchesAdapter();
        mViewModel.invokeBranchesApi();
        subscribeBranchesObserver();
        branchesRV.setAdapter(mAdapter);
        mAdapter.mListener = this;

        return view;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }

    private void subscribeBranchesObserver() {
        mViewModel.getBranchesLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<BranchRecords>>>() {
                    @Override
                    public void onChanged(Resource<List<BranchRecords>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    dataList.clear();

                                    ((MainActivity) getActivity()).showProgressBar(false);

                                    showProgressBar(false);
                                    List<BranchRecords> list = resource.getData();
                                    Long branchId = ((MainActivity) getActivity()).getSelectedBranchId();
                                    for (int i = 0; i < list.size(); i++) {


                                        LatLng latLngTo = new LatLng(Double.valueOf(list.get(i).getLat())
                                                , Double.valueOf(list.get(i).getLang()));
                                        GPSTracker gps = new GPSTracker(getActivity());

                                        LatLng latLngFrom = new LatLng(
                                                gps.getLatitude()
                                                , gps.getLongitude());
                                        String distance = mViewModel.getDistance(getContext()
                                                , latLngFrom, latLngTo);
                                        boolean isOpen = mViewModel.isOpen(list.get(i).getOpenTime(),
                                                list.get(i).getClosedTime());
                                        list.get(i).setDistance(distance);
                                        list.get(i).setIsOpen(isOpen);
                                        if (branchId == list.get(i).getId()) {
                                            list.get(i).setSelected(true);
                                        }
                                        dataList.add(list.get(i));

                                    }
                                    mAdapter.fillAdapterData(dataList);
                                    drawMarkersOnMap();
                                    break;
                                case ERROR:
                                    ((MainActivity) getActivity()).showProgressBar(false);
                                    showProgressBar(false);
//                                    showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    ((MainActivity) getActivity()).showProgressBar(true);

                                    showProgressBar(true);
                                    break;
                            }
                        }
                    }
                });
    }

    private void drawMarkersOnMap() {
        if (mMap != null && dataList.size()>0) {
            for (BranchRecords records : dataList) {
                addMarkerToMap(new LatLng((records.getLat()),(records.getLang())));
            }
        }
    }

    private void showProgressBar(boolean isLoading) {
//        if (isLoading) {
//            mDilatingDotsProgressBar.show();
//        } else {
//            mDilatingDotsProgressBar.hideNow();
//        }
    }


    private void setUpMapUi() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        grantLocationPermission();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                mapFragment.getMapAsync(this);

            } else if (grantResults.length == 0) {
                if ((ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

                    requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

                }


            }


        }
    }

    @SuppressLint("MissingPermission")
    private void grantLocationPermission() {
        new RxPermissions(this)
                .requestEachCombined(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(permission -> {
                    if (permission.granted) {

                        mapFragment.getMapAsync(this);

                        fusedLocationClient.getLastLocation().addOnSuccessListener(this);
                    }

                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        drawMarkersOnMap();
    }

    @Override
    public void onSuccess(Location location) {
        if (location == null) return;
        this.location = location;
        this.currentLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        currentAddress = mViewModel.getAddress(getContext(), latLng);
//        addMarkerToMap(latLng);
        drawMarkersOnMap();
    }

    private void addMarkerToMap(@NonNull LatLng location) {
        if (click == 0) {
            if (mMap != null && location != null) {
                //mMap.clear();
                LatLng currentLatLng = new LatLng(location.latitude, location.longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(getMarkerIcon());
                markerOptions.position(currentLatLng);
                if (location == null) {
//                    currentAddress = mViewModel.getAddress(getContext(), location);
//                    markerOptions.title(currentAddress);

                } else {
                    currentAddress = mViewModel.getAddress(getContext(), location);
                    markerOptions.title(currentAddress);
                }
                mMap.addMarker(markerOptions);
                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16.0f));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 11.0f));

            }
        } else if (click == 1) {
            if (mMap != null && location != null) {
                //mMap.clear();
                LatLng currentLatLng = new LatLng(location.latitude, location.longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(getMarkerIcon());
                markerOptions.position(currentLatLng);
                if (location == null) {
                    currentAddress = mViewModel.getAddress(getContext(), location);
                    markerOptions.title(currentAddress);
                } else {
                    currentAddress = mViewModel.getAddress(getContext(), location);
                    markerOptions.title(currentAddress);
                }
                mMap.addMarker(markerOptions);
                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16.0f));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 20.0f));

            }
        }

    }

    private BitmapDescriptor getMarkerIcon() {
        Bitmap bitmap = mViewModel.getBitmapFromVectorDrawable(getContext(), R.drawable.ic_location_pin);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    @Override
    public void onResume() {
        super.onResume();
        checkLocationUpdateProvider();
    }

    private final int LOCATION_PERMISSION_REQUEST_CODE = 1212;

    private void checkLocationUpdateProvider() {
        try {
            if (mViewModel.isNetworkLocationEnabled(getContext())) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    grantLocationPermission();
                    return;
                }
                Location mLocation = mViewModel.getLocationManager(getContext()).getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (mLocation != null) {
                    currentLocation = mLocation;
                    onSuccess(mLocation);
                }
                //viewModel.getLocationManager(getActivity()).requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new , null);
            } else if (!mViewModel.isGPSLocationEnabled(getContext())) {
                //viewModel.getLocationManager(getActivity()).requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
                Location mLocation = mViewModel.getLocationManager(getContext()).getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (mLocation != null) {
                    currentLocation = mLocation;
                    onSuccess(mLocation);
                } else {
                    enableLocationSettings();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;

    private void enableLocationSettings() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getContext())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {
                            checkLocationUpdateProvider();
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            Utils.getInstance().LogError("Location error", "Location error " + connectionResult.getErrorCode());
                            //Utils.getInstance().showToast(getContext(), "Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(getActivity(), REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                                Utils.getInstance().LogError(TAG, e.getMessage());
                            }
                            break;
                        case LocationSettingsStatusCodes.SUCCESS:
                            checkLocationUpdateProvider();
                            break;
                    }
                }
            });
        }
    }

    @OnClick(R.id.continueBtn)
    public void onViewClicked() {
        ((MainActivity) getActivity()).navToDestination(R.id.nav_home);
    }


    @Override
    public void onSelectBranch(BranchRecords record, int position) {
        click = 1;
        Long branchId = ((MainActivity) getActivity()).getSelectedBranchId();
        for (int i = 0; i < dataList.size(); i++) {
            record.setSelected(false);
            dataList.get(i).setBackground(R.drawable.stroke_bg_red);
        }
        dataList.get(position).setBackground(R.drawable.stroke_bg_red2);
        dataList.get(position).setSelected(true);
        branchTitleTV.setText(record.getName());
        ((MainActivity)getActivity()).saveSelectedBranch(record);
        addMarkerToMap(new LatLng((dataList.get(position).getLat()),(dataList.get(position).getLang())));
        ((MainActivity) getActivity()).setPromot(position);

        mAdapter.fillAdapterData(dataList);



        Log.d("position",""+position+""+branchId);

    }
}