package com.golden.goldencorner.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ShareCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.golden.goldencorner.BuildConfig;
import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.BranchRecords;
import com.golden.goldencorner.data.model.DataItemTerms;
import com.golden.goldencorner.data.model.Product;
import com.golden.goldencorner.data.receiver.NetworkReceiver;
import com.golden.goldencorner.ui.DeliveryDate.SendTime;
import com.golden.goldencorner.ui.ViewDialog;
import com.golden.goldencorner.ui.base.BaseActivity;
import com.golden.goldencorner.ui.login.LogInActivity;
import com.golden.goldencorner.ui.main.DiscounteCode.DiscounteData;
import com.golden.goldencorner.ui.main.addresses.SendData;
import com.golden.goldencorner.ui.main.addresses.SendDataLocation;
import com.golden.goldencorner.ui.main.cart.CardAdapter;
import com.golden.goldencorner.ui.main.latestProducts.LastProductsFragment;
import com.golden.goldencorner.ui.main.mostRequested.MustRequestedFragment;
import com.golden.goldencorner.ui.main.offers.OffersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.golden.goldencorner.data.Utils.AppConstant.ACCESS_TOKEN;
import static com.golden.goldencorner.data.Utils.AppConstant.ARABIC_LANGUAGE;
import static com.golden.goldencorner.data.Utils.AppConstant.UserAvatar;
import static com.golden.goldencorner.data.Utils.AppConstant.UserName;
import static com.golden.goldencorner.ui.main.offers.ProductsFragment.SELECTED_FRAGMENT_NAME;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, AdapterView.OnItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener, SendDataLocation, SendTime, SendData, DiscounteData {


    public static final String TAG = MainActivity.class.getName();
    private static final int RC_LOGIN = 101;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.mCoordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    //    @BindView(R.id.menuToolbarIV)
//    AppCompatImageView menuToolbarIV;
    @BindView(R.id.titleToolbarIv)
   public    TextView titleToolbarIv;
    @BindView(R.id.spinnerToolbar)
    AppCompatSpinner spinnerToolbar;
    @BindView(R.id.searchToolbarIV)
    AppCompatImageView searchToolbarIV;
    @BindView(R.id.cartToolbarIV)
    AppCompatImageView cartToolbarIV;
    @BindView(R.id.cartCountIV)
    TextView cartCountIV;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.mDilatingDotsProgressBar)
    DilatingDotsProgressBar mDilatingDotsProgressBar;
    boolean value = false;
    private NavController navController;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView loginNavHeaderTV;
    private ImageView logoNavHeaderIV;
    private MainViewModel mViewModel;
    private List<BranchRecords> dataList = new ArrayList<>();
    private List<Product> cardListProducts = new ArrayList<>();
    private RxPermissions rxPermissions;

    int oldPosition, newPosition;
    String lang = "";

    private ArrayAdapter<String> branchesSpinnerAdapter = null;

    private void subscribeBranchesObserver() {
        mViewModel.getBranchesLiveData().observe(this,
                new Observer<Resource<List<BranchRecords>>>() {
                    @Override
                    public void onChanged(Resource<List<BranchRecords>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    dataList.clear();
                                    dataList.addAll(resource.getData());
                                    setUpSpinnerUi(dataList);
                                    break;
                                case ERROR:
                                    break;
                                case LOADING:
                                    break;
                            }
                        }
                    }
                });
    }

    String lat = "";

    private void setUpSpinnerUi(List<BranchRecords> dataList) {
        branchesSpinnerAdapter = new ArrayAdapter<>(getApplicationContext(),
               R.layout.simple_spinner_item);
        branchesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerToolbar.setAdapter(branchesSpinnerAdapter);
        spinnerToolbar.setOnItemSelectedListener(this);
        String currentLanguage = SharedPreferencesManager.getCurrentLang();
        for (BranchRecords records : dataList) {
            if (currentLanguage.equalsIgnoreCase(ARABIC_LANGUAGE))
                branchesSpinnerAdapter.add(records.getName());
            else
                branchesSpinnerAdapter.add(records.getNameEn());
        }

        branchesSpinnerAdapter.notifyDataSetChanged();
    }

    public void setPromot(int text)
    {
        spinnerToolbar.setSelection(text);

    }
    private void setUpUiViews() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mAppBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setDrawerLayout(drawerLayout).build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
//        NavigationUI.setupWithNavController(toolbar, bottomNavigationView, mAppBarConfiguration);
        navigationView.setNavigationItemSelectedListener(this);
        if (!drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.close();
        View headerLayout = navigationView.getHeaderView(0);
        loginNavHeaderTV = headerLayout.findViewById(R.id.loginNavHeaderTV);
        logoNavHeaderIV = headerLayout.findViewById(R.id.logoNavHeaderIV);
        loginNavHeaderTV.setOnClickListener(this);
        logoNavHeaderIV.setOnClickListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        if (TextUtils.isEmpty(SharedPreferencesManager.getString(AppConstant.SELECTED_BRANCH_NAME))) {
            navToDestination(R.id.nav_branches);
        }
        if (SharedPreferencesManager.getString(UserName)!=null)
            navigationView.getMenu().removeItem(R.id.nav_signup_drawer);

    }

    private void fillViewOnLogin() {
        String UserNameStr = SharedPreferencesManager.getString(UserName);
        if (!TextUtils.isEmpty(UserNameStr)) {
            loginNavHeaderTV.setText(UserNameStr);
        } else {
            loginNavHeaderTV.setText(getString(R.string.login));
        }
        String UserAvatarStr = SharedPreferencesManager.getString(UserAvatar);
        if (!TextUtils.isEmpty(UserAvatarStr)) {
            Glide.with(getApplicationContext()).load(UserAvatarStr).into(logoNavHeaderIV);
        } else {
            Glide.with(getApplicationContext()).load(R.drawable.menuico_person_62).into(logoNavHeaderIV);
        }
        if (SharedPreferencesManager.getString(UserName)!=null)
            navigationView.getMenu().removeItem(R.id.nav_signup_drawer);
    }

    private void hideNavigation() {
//        this.getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
////                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
////                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        // remove the following flag for version < API 19
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        setCoordinatorLayout(mCoordinatorLayout);
        NetworkReceiver.getInstance().setNetworkReceiverListener(this);
        fillViewOnLogin();
    }

    @Override
    public void onNetworkReceiverChange(boolean flag) {
        super.onNetworkReceiverChange(flag);
        if (!flag) {
            if (getCoordinatorLayout() != null) {
                showSnackBar(getCoordinatorLayout(), getString(R.string.no_internet_connection));
            } else {
                showToast(getString(R.string.no_internet_connection));
            }
        } else {
            hideSnackBar();
            if (dataList.isEmpty()) {
                mViewModel.invokeBranchesApi();
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    String time = "00:00";

    private void shareApp() {
        ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setChooserTitle("Select")
                .setText("http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)
                .startChooser();
//        try {
//            Intent shareIntent = new Intent(Intent.ACTION_SEND);
//            shareIntent.setType("text/plain");
//            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
//            String shareMessage= "\nLet me recommend you this application\n\n";
//            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
//            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
//            startActivity(Intent.createChooser(shareIntent, "choose one"));
//        } catch(Exception e) {
//            //e.toString();
//        }
    }

    double total2 = 0.0;

    //    private boolean isValidDestination(int destination) {
//        return destination != navController.getCurrentDestination().getId();
//    }
    public void navToDestination(int resId) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        if (navController != null && resId != 0)
            navController.navigate(resId);

    }

    public void navToDestination(int resId, Bundle bundleArgs) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        if (navController != null && resId != 0)
            navController.navigate(resId, bundleArgs);
    }

    public void navToDestination(NavDirections action) {
        if (navController != null && action != null)
            navController.navigate(action);
    }

    public void drawerHandler() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            drawerLayout.open();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logoNavHeaderIV:
            case R.id.loginNavHeaderTV:
                startActivityForResult(new Intent(getApplicationContext(),
                        LogInActivity.class), RC_LOGIN);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_LOGIN && resultCode == RESULT_OK) {
            fillViewOnLogin();
        }
    }

    @OnClick({/*R.id.menuToolbarIV
            , */R.id.titleToolbarIv
            , R.id.searchToolbarIV
            , R.id.cartToolbarIV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.menuToolbarIV:
//                drawerHandler();
//                break;
            case R.id.titleToolbarIv:
                break;
            case R.id.searchToolbarIV:
                navToDestination(R.id.nav_search);
                break;
            case R.id.cartToolbarIV:
                navToDestination(R.id.nav_cart);
                break;
        }
    }

    String copun = "";

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i(TAG, parent.getFirstVisiblePosition() + "");
    }

    int discopun = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
//        hideNavigation();

        subscribeBranchesObserver();
        rxPermissions = new RxPermissions(this);
        ArrayList<DataItemTerms> arrayList = new ArrayList<>();
        try {
            value = getIntent().getExtras().getBoolean("move", false);
        } catch (NullPointerException e) {
        }
        if (value) {

            navToDestination(R.id.nav_terms_and_conditions);
        }

        setUpUiViews();


    }


    public void showProgressBar(boolean isLoading) {
        if (isLoading) {
            mDilatingDotsProgressBar.show();
        } else {
            mDilatingDotsProgressBar.hideNow();
        }
    }

    private static boolean isSpinnerFirstTime = true;

    public void setToolBarTitle(String title) {
        titleToolbarIv.setText(title);
    }

    public void saveSelectedBranch(BranchRecords branchRecords) {
        SharedPreferencesManager.put(AppConstant.SELECTED_BRANCH_NAME, branchRecords.getName());
        SharedPreferencesManager.put(AppConstant.SELECTED_BRANCH_ID, branchRecords.getId());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public Long getSelectedBranchId() {
        return SharedPreferencesManager.getLong(AppConstant.SELECTED_BRANCH_ID);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
            case R.id.nav_home_drawer:
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.nav_host_fragment, true)
                        .build();
                navController.navigate(R.id.nav_home, null, navOptions);
                break;
            case R.id.nav_signup:
            case R.id.nav_signup_drawer:
                navToDestination(R.id.nav_signup);
                break;
            case R.id.nav_account:
            case R.id.nav_account_drawer:
                if (SharedPreferencesManager.getString(UserName) != null) {
                    navToDestination(R.id.nav_account);
                }
                else {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(MainActivity.this);
                }

                break;
            case R.id.nav_branches:
            case R.id.nav_branches_drawer:
                navToDestination(R.id.nav_branches);
                break;
            case R.id.nav_sub_categories:
                navToDestination(R.id.nav_sub_categories);
                break;
            case R.id.nav_latest_products:
            case R.id.nav_latest_products_drawer:
                navToTabsManager(LastProductsFragment.class.getSimpleName());
                break;
            case R.id.nav_offers:
            case R.id.nav_offers_drawer:
                navToTabsManager(OffersFragment.class.getSimpleName());
                break;
            case R.id.nav_most_requested:
            case R.id.nav_most_requested_drawer:
                navToTabsManager(MustRequestedFragment.class.getSimpleName());
                break;
            case R.id.nav_addresses:
            case R.id.nav_addresses_drawer:
                if (SharedPreferencesManager.getString(UserName) != null) {
                    navToDestination(R.id.nav_addresses);
                }
                else {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(MainActivity.this);
                }

                break;
            case R.id.nav_favorites:
            case R.id.nav_favorites_drawer:
                if (SharedPreferencesManager.getString(UserName) != null) {
                    navToDestination(R.id.nav_favorites);
                }
                else {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(MainActivity.this);
                }

                break;
            case R.id.nav_my_orders:
            case R.id.nav_my_orders_bottom:
            case R.id.nav_my_orders_drawer:
                if (SharedPreferencesManager.getString(UserName) != null) {
                    navToDestination(R.id.nav_my_orders);
                }
                else {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(MainActivity.this);
                }
                break;
            case R.id.nav_language_setting:
            case R.id.nav_language_setting_drawer:
                navToDestination(R.id.nav_language_setting);
                break;
            case R.id.nav_about_app:
            case R.id.nav_about_app_drawer:
                navToDestination(R.id.nav_about_app);
                break;
            case R.id.nav_share_app_drawer:
                shareApp();
                break;
            case R.id.nav_order_view:
                navToDestination(R.id.nav_order_view);
                break;
            case R.id.nav_driver_info:
                navToDestination(R.id.nav_driver_info);
                break;
            case R.id.nav_contact_us:
                navToDestination(R.id.nav_contact_us);
                break;
            case R.id.nav_logout_drawer:
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                finish();
                break;
            case R.id.nav_card_drawer:
            case R.id.nav_add_card:
                if (SharedPreferencesManager.getString(UserName) != null) {
                    navToDestination(R.id.nav_add_card);
                } else {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(MainActivity.this);
                }
                break;

        }
        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void savename(String name) {
        SharedPreferencesManager.put("Edit", name);
    }

    public String getName() {
        return SharedPreferencesManager.getString("Edit");
    }

    public String getAccessToken() {
        return SharedPreferencesManager.getString(ACCESS_TOKEN);
    }

    public List<Product> getCardListProducts() {
        return cardListProducts;
    }

    public void addProductToCard(Product product) {
        this.cardListProducts.add(product);
        cartCountIV.setVisibility(View.VISIBLE);
        cartCountIV.setText(cardListProducts.size() + "");
    }

    public void removeProductFromCard(Product product) {
        this.cardListProducts.remove(product);
        if (cardListProducts.size() == 0) {
            cartCountIV.setVisibility(View.INVISIBLE);
        } else {
            cartCountIV.setText(cardListProducts.size() + "");
        }
    }

    private void navToTabsManager(String fragmentName) {
        Bundle bundle1 = new Bundle();
        bundle1.putString(SELECTED_FRAGMENT_NAME, fragmentName);
        Log.i("Fragment", fragmentName);
        SharedPreferencesManager.put(SELECTED_FRAGMENT_NAME, fragmentName);
        navToDestination(R.id.nav_product_manager, bundle1);
    }

    public RxPermissions getRxPermissions() {
        return rxPermissions;
    }

    // spinner click listener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<Integer> back = new ArrayList<>();
        back.add(position);
        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        if (!isSpinnerFirstTime) {
            isSpinnerFirstTime = true;
            return;
        }
        if (getSelected() != position) {
            if (cardListProducts.size() > 0) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(getString(R.string.added_delete))
                        .setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                saveSelectedBranch(dataList.get(position));
                                clearCardListProducts();
                                setSelectedBranchId(dataList.get(position).getId());
                                setSelected(position);

                            }

                        })
                        .setNegativeButton(getString(R.string.off), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                spinnerToolbar.setSelection(getSelected());
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(false)
                        .show();
            } else {
                saveSelectedBranch(dataList.get(position));
                clearCardListProducts();
                setSelectedBranchId(dataList.get(position).getId());
                setSelected(position);
            }

        }
    }

    public int getSelected() {
        return SharedPreferencesManager.getInt("spinner");
    }

    public void setSelected(int id) {
        SharedPreferencesManager.put("spinner", id);
    }

    public void setSelectedBranchId(Long id) {
        SharedPreferencesManager.put(AppConstant.SELECTED_BRANCH_ID, id);
    }

    public String getSelectedBranchName() {
        return SharedPreferencesManager.getString(AppConstant.SELECTED_BRANCH_NAME);
    }

    public void clearCardListProducts() {
        this.cardListProducts.clear();
        cartCountIV.setText("");
        cartCountIV.setVisibility(View.INVISIBLE);
        CardAdapter cardAdapter = new CardAdapter();
        cardAdapter.notifyDataSetChanged();

    }

    public String getLang() {
        return lang;
    }

    public String getLat() {
        return lat;
    }

    @Override
    public void sendData(String location, String lat, String Lang) {
//        this.location=location;

        TextView locationtv = findViewById(R.id.locationTV);
        // TextView locationtv2=findViewById(R.id.deliveryMethodLocationViewTV);
        Log.i("location", location);
        locationtv.setText(location);
        //locationtv2.setText(location);

        lang = Lang;
        this.lat = lat;
    }

    public String gettime() {
        return time;
    }

    @Override
    public void sendTime(String hour, String minute) {
        TextView timeViewTV = findViewById(R.id.timeViewTV);
        timeViewTV.setText(hour + ":" + minute);
        time = hour + ":" + minute;
    }

    public void setTotal(double total) {
        this.total2 = total;
    }

    @Override
    public void sendDataLocation(String location) {
        TextView locationtv2 = findViewById(R.id.deliveryMethodLocationViewTV);
        locationtv2.setText(location);
    }

    public String getCopun() {
        return copun;
    }

    public String discopun() {
        return copun;
    }

    @Override
    public void data(double c, String code, double collect) {
        copun = code;
        TextView textView = findViewById(R.id.PaymentSummaryDiscountCodePriceTV);
        textView.setText(String.valueOf(c));
        TextView total = findViewById(R.id.PaymentSummaryTotalPriceTV);
        Log.e("result2", String.valueOf(Double.valueOf(c / 100)));
        double test = total2 - (total2 * (c / 100));

        total.setText(String.valueOf((total2 - (total2 * (c / 100)))));
        Log.e("result", String.valueOf(test));


    }
}