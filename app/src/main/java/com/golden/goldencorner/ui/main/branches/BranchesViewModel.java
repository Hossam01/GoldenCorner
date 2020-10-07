package com.golden.goldencorner.ui.main.branches;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.Utils.Utils;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.BranchRecords;
import com.golden.goldencorner.data.remote.RetrofitProvider;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BranchesViewModel extends ViewModel {

    public static final String TAG = BranchesViewModel.class.getName();
    private MutableLiveData<Resource<List<BranchRecords>>> banchesLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<List<BranchRecords>>> getBranchesLiveData() {
        return banchesLiveData;
    }

    public void invokeBranchesApi() {

            banchesLiveData.setValue(Resource.loading());
            RetrofitProvider.getClient().getBranches()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(branchesResponse -> {
                        banchesLiveData.setValue(Resource.success(branchesResponse.getData()));
                    }, throwable -> {
                        banchesLiveData.setValue(Resource.error(throwable.getMessage(), null));
                    });
    }

    private LocationManager locationManager;
    public LocationManager getLocationManager(@NonNull Context mContext){
        if (locationManager == null)
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager;
    }
    public boolean isNetworkLocationEnabled(@NonNull Context mContext){
        return getLocationManager(mContext).isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    public boolean isGPSLocationEnabled(@NonNull Context mContext){
        return getLocationManager(mContext).isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable =  AppCompatResources.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    public String getAddress(@NonNull Context mContext, @NonNull LatLng location) {
        if (location == null) return null;
        String currentAddress = null;
        if (currentAddress == null) {
            try {
                Geocoder geocoder = null;
                String language =  SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
                if (!TextUtils.isEmpty(language) && language.equalsIgnoreCase(AppConstant.ARABIC_LANGUAGE))
                    geocoder = new Geocoder(mContext, new Locale(language));
                else
                    geocoder = new Geocoder(mContext, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(location.latitude,
                        location.longitude, 1);
                Address mAddress = addresses.get(0);
                currentAddress = mAddress.getAddressLine(0);
            } catch (Exception e) {
                Utils.getInstance().LogError(TAG, e.getMessage());
            }
        }
        return currentAddress;
    }

    public String getDistance(@NonNull Context mContext, @NonNull LatLng latLngFrom, @NonNull LatLng latLngTo) {
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setDecimalSeparator('.');
        DecimalFormat formatter = new DecimalFormat("##.##",symbols);
        double distance = SphericalUtil.computeDistanceBetween(latLngFrom, latLngTo);
        int dist = (int) (distance / 1000);
        return dist+mContext.getString(R.string.KM);
    }
    public boolean isOpen(@NonNull String openTimeStr, @NonNull String closeTimeStr) {
        try{
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm:ss");
            Date parsedDate1 = dateFormat1.parse(openTimeStr);
            long openTimeTimestamp = new Timestamp(parsedDate1.getTime()).getTime();

            SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm:ss");
            Date parsedDate2 = dateFormat2.parse(openTimeStr);
            long closeTimeTimestamp = new Timestamp(parsedDate2.getTime()).getTime();

            long currentTimestamp = new Timestamp(System.currentTimeMillis()).getTime();
            return  (openTimeTimestamp > currentTimestamp && currentTimestamp < closeTimeTimestamp);
        } catch(Exception e) {}
        return false;
    }
}
