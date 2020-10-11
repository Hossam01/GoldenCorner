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
import com.golden.goldencorner.data.model.Product;
import com.golden.goldencorner.data.receiver.NetworkReceiver;
import com.golden.goldencorner.ui.base.BaseActivity;
import com.golden.goldencorner.ui.login.LogInActivity;
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
        View.OnClickListener, AdapterView.OnItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {


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

    private NavController navController;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView loginNavHeaderTV;
    private ImageView logoNavHeaderIV;
    private MainViewModel mViewModel;
    private List<BranchRecords> dataList = new ArrayList<>();
    private List<Product> cardListProducts = new ArrayList<>();
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
        setUpUiViews();
//        hideNavigation();
        subscribeBranchesObserver();
        rxPermissions = new RxPermissions(this);
    }

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
                navToDestination(R.id.nav_account);
                break;
            case R.id.nav_branches:
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
                navToDestination(R.id.nav_addresses);
                break;
            case R.id.nav_favorites:
            case R.id.nav_favorites_drawer:
                navToDestination(R.id.nav_favorites);
                break;
            case R.id.nav_my_orders:
            case R.id.nav_my_orders_bottom:
            case R.id.nav_my_orders_drawer:
                navToDestination(R.id.nav_my_orders);
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
        }
        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

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

    private void navToTabsManager(String fragmentName) {
        Bundle bundle1 = new Bundle();
        bundle1.putString(SELECTED_FRAGMENT_NAME, fragmentName);
        navToDestination(R.id.nav_product_manager, bundle1);
    }

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

    // spinner click listener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        if (!isSpinnerFirstTime) {
            isSpinnerFirstTime = true;
            return;
        }
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(getString(R.string.added_delete))
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveSelectedBranch(dataList.get(position));

                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i(TAG, parent.getFirstVisiblePosition() + "");
    }

    public void showProgressBar(boolean isLoading) {
        if (isLoading) {
            mDilatingDotsProgressBar.show();
        } else {
            mDilatingDotsProgressBar.hideNow();
        }
    }
    private static boolean isSpinnerFirstTime = true;

    public void setToolBarTitle(String title){
        titleToolbarIv.setText(title);
    }
    public void saveSelectedBranch(BranchRecords branchRecords) {
        SharedPreferencesManager.put(AppConstant.SELECTED_BRANCH_NAME, branchRecords.getName());
        SharedPreferencesManager.put(AppConstant.SELECTED_BRANCH_ID, branchRecords.getId());
    }

    public Long getSelectedBranchId() {
        return SharedPreferencesManager.getLong(AppConstant.SELECTED_BRANCH_ID);
    }

    public Long getSelectedBranchName() {
        return SharedPreferencesManager.getLong(AppConstant.SELECTED_BRANCH_NAME);
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
        cartCountIV.setText(cardListProducts.size() + "");
    }

    public void clearCardListProducts() {
        this.cardListProducts.clear();
        cartCountIV.setText("");
        cartCountIV.setVisibility(View.INVISIBLE);
    }

    public RxPermissions getRxPermissions() {
        return rxPermissions;
    }

}