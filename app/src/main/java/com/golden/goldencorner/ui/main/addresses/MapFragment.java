package com.golden.goldencorner.ui.main.addresses;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.Utils.Utils;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.ui.main.branches.BranchesViewModel;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;
import java.util.Locale;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapFragment extends DialogFragment implements OnMapReadyCallback, OnSuccessListener<Location>, GoogleMap.OnMarkerDragListener, GoogleMap.OnMapClickListener {

    public static final String TAG = MapFragment.class.getName();
    @BindView(R.id.mapLocationTV)
    TextView mapLocationTV;
    @BindView(R.id.confirmLocationBtn)
    CircularProgressButton confirmLocationBtn;
    @BindView(R.id.closeIV)
    ImageView closeIV;

    private GoogleMap mMap;
    private BranchesViewModel mViewModel;
    private FusedLocationProviderClient fusedLocationClient;
//    private Location location;
    private String currentAddress;
//    private Location currentLocation;

    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this)
                .get(BranchesViewModel.class);
        setUpMapUi();
    }


    private void setUpMapUi() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        grantLocationPermission();

    }

    @SuppressLint("MissingPermission")
    private void grantLocationPermission() {
        new RxPermissions(this)
                .requestEachCombined(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(permission -> {
                    if (permission.granted) {
                        fusedLocationClient.getLastLocation().addOnSuccessListener(this);
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onSuccess(Location location) {
        if (location == null) return;
//        this.location = location;
//        this.currentLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        currentAddress = mViewModel.getAddress(getContext(), latLng);
        addMarkerToMap(latLng);

    }

    private void addMarkerToMap(@NonNull LatLng latLng) {
        if (mMap != null && latLng != null) {
            mMap.clear();
            LatLng currentLatLng = new LatLng(latLng.latitude,
                    latLng.longitude);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.draggable(true);
            markerOptions.icon(getMarkerIcon());
            markerOptions.position(currentLatLng);
            currentAddress = mViewModel.getAddress(getContext(), latLng);
            markerOptions.title(currentAddress);
            mMap.addMarker(markerOptions);
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16.0f));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16.0f));
        }
    }
//    private void addMarkerToMap(@NonNull LatLng latLng) {
//        if (mMap != null && latLng != null) {
//            mMap.clear();
//            LatLng currentLatLng = new LatLng(latLng.latitude,
//                    latLng.longitude);
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.draggable(true);
//            markerOptions.icon(getMarkerIcon());
//            markerOptions.position(currentLatLng);
//            if (latLng == null) {
//                currentAddress = mViewModel.getAddress(getContext(), latLng);
//                markerOptions.title(currentAddress);
//            } else {
//                markerOptions.title(currentAddress);
//            }
//            mMap.addMarker(markerOptions);
//            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16.0f));
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16.0f));
//        }
//    }

    private BitmapDescriptor getMarkerIcon() {
        Bitmap bitmap = mViewModel.getBitmapFromVectorDrawable(getContext(), R.drawable.ic_location_black_21);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        checkLocationUpdateProvider();
//        super.onStart();
//    }
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
                    onSuccess(mLocation);
                }
                //viewModel.getLocationManager(getActivity()).requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new , null);
            } else if (!mViewModel.isGPSLocationEnabled(getContext())) {
                //viewModel.getLocationManager(getActivity()).requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
                Location mLocation = mViewModel.getLocationManager(getContext()).getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (mLocation != null) {
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


    @Override
    public void onMapClick(LatLng latLng) {
        this.latLng = latLng;
        addMarkerToMap(latLng);
    }
    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    private LatLng latLng;
    @Override
    public void onMarkerDragEnd(Marker marker) {
        mMap.clear();
        mapLocationTV.setText(marker.getTitle());
        latLng = marker.getPosition();
//        lat = (long) latLng.latitude;
//        lang = (long) latLng.longitude;
        addMarkerToMap(latLng);
        state = mViewModel.getAddress(getContext(), latLng);
        map_location = marker.getTitle();
    }
//    long lat = 0;
//    long lang = 0;
    String state = null;
    String map_location = null;
    @OnClick(R.id.confirmLocationBtn)
    public void onConfirmLocationBtnClicked() {
//        Bundle result = new Bundle();
//        result.putLong(AppConstant.LATITUDE, lat);
//        result.putLong(AppConstant.LONGITUDE, lang);
//        result.putString(AppConstant.STATE, state);
//        result.putString(AppConstant.MAP_LOCATION, map_location);

        SharedPreferencesManager.put(AppConstant.LATITUDE, (long) latLng.latitude);
        SharedPreferencesManager.put(AppConstant.LONGITUDE, (long)  latLng.longitude);
        SharedPreferencesManager.put(AppConstant.STATE, state);
        SharedPreferencesManager.put(AppConstant.MAP_LOCATION, map_location);

    }

    @OnClick(R.id.closeIV)
    public void onCloseIVClicked() {
        SharedPreferencesManager.remove(AppConstant.LATITUDE);
        SharedPreferencesManager.remove(AppConstant.LONGITUDE);
        SharedPreferencesManager.remove(AppConstant.STATE);
        SharedPreferencesManager.remove(AppConstant.MAP_LOCATION);
        this.dismiss();
    }


//    public String getAddress(@NonNull Context mContext, @NonNull LatLng location) {
//        if (location == null) return null;
//        String currentAddress = null;
//        if (currentAddress == null) {
//            try {
//                Geocoder geocoder = null;
//                String language =  SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
//                if (!TextUtils.isEmpty(language) && language.equalsIgnoreCase(AppConstant.ARABIC_LANGUAGE))
//                    geocoder = new Geocoder(mContext, new Locale(language));
//                else
//                    geocoder = new Geocoder(mContext, Locale.getDefault());
//                List<Address> addresses = geocoder.getFromLocation(location.latitude,
//                        location.longitude, 1);
//                Address mAddress = addresses.get(0);
//                currentAddress = mAddress.getAddressLine(0);
//            } catch (Exception e) {
//                Utils.getInstance().LogError(TAG, e.getMessage());
//            }
//        }
//        return currentAddress;
//    }
//    public String getAddress(@NonNull Context mContext, @NonNull Location location) {
//        if (location == null) return null;
//        String currentAddress = null;
//        if (currentAddress == null) {
//            try {
//                Geocoder geocoder = null;
//                String language =  SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
//                if (!TextUtils.isEmpty(language) && language.equalsIgnoreCase(AppConstant.ARABIC_LANGUAGE))
//                    geocoder = new Geocoder(mContext, new Locale(language));
//                else
//                    geocoder = new Geocoder(mContext, Locale.getDefault());
//                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
//                        location.getLongitude(), 1);
//                Address mAddress = addresses.get(0);
//                currentAddress = mAddress.getAddressLine(0);
//            } catch (Exception e) {
//                Utils.getInstance().LogError(TAG, e.getMessage());
//            }
//        }
//        return currentAddress;
//    }

}