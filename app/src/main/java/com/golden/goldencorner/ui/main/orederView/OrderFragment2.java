package com.golden.goldencorner.ui.main.orederView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.BranchRecords;
import com.golden.goldencorner.data.model.Dish;
import com.golden.goldencorner.data.model.ProductDetailData;
import com.golden.goldencorner.data.model.ProductExtension;
import com.golden.goldencorner.data.model.ProductSize;
import com.golden.goldencorner.data.model.Rice;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.ui.ViewDialog;
import com.golden.goldencorner.ui.main.MainActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.golden.goldencorner.data.Utils.AppConstant.UserName;
 
public class OrderFragment2 extends DialogFragment implements ExtensionsAdapter.AdapterListener, SelectMealAdapter.AdapterListener, RiceAdapter.AdapterListener, dishAdapter.AdapterListener {
 
    double dishPrice = 0.0;
    double ricePrice = 0.0;
    int riceselect = 0;
    int riceSize;
    @BindView(R.id.closeIV)
    ImageView close;
    @BindView(R.id.productDetailsSliderRV)
    SliderView productDetailsSliderRV;
    @BindView(R.id.favoritesIV)
    ImageView favoritesIV;
    @BindView(R.id.shareIV)
    ImageView shareIV;
    @BindView(R.id.productPriceTV)
    TextView productPriceTV;
    @BindView(R.id.productItemsCountTV)
    TextView productItemsCountTV;
    @BindView(R.id.productNameTV)
    TextView productNameTV;
    @BindView(R.id.productRateTV)
    TextView productRateTV;
    @BindView(R.id.productDescriptionTV)
    TextView productDescriptionTV;
    @BindView(R.id.productCaloriesTV)
    TextView productCaloriesTV;
    @BindView(R.id.productMealsTV)
    TextView productMealsTV;
    @BindView(R.id.productExpandMealBtn)
    ImageView productExpandMealBtn;
    @BindView(R.id.productDetailsMailsRV)
    RecyclerView productDetailsMailsRV;
    @BindView(R.id.productAdditionsTV)
    TextView productAdditionsTV;
    @BindView(R.id.productExpandMealAdditionsBtn)
    ImageView productExpandMealAdditionsBtn;
    @BindView(R.id.productDetailsAdditionsRV)
    RecyclerView productDetailsAdditionsRV;
    int mealselect = 0;
    int size;
    float quntity = 0;
    List<ProductSize> productSizes = new ArrayList<>();

    List<ProductExtension> extensions = new ArrayList<>();

    //    @BindView(R.id.productCommentsTV)
//    TextView productCommentsTV;
//    @BindView(R.id.productHintTV)
//    TextView productHintTV;
//    @BindView(R.id.productExpandMealCommentsBtn)
//    ImageView productExpandMealCommentsBtn;
//    @BindView(R.id.productDetailsCommentsRV)
//    RecyclerView productDetailsCommentsRV;
    @BindView(R.id.addToCartBtn)
    CircularProgressButton addToCartBtn;
    @BindView(R.id.minusBtn)
    ImageView minusBtn;
    @BindView(R.id.ProductImage)
    ImageView productImage;
    @BindView(R.id.pluseBtn)
    ImageView pluseBtn;
    double orderTotal=0.0;
    Double ext_price = 0.0;
    String starttime, closetime;
    String kindMeal;
    private OrderViewViewModel mViewModel;
    private ProductDetailData detailData = null;
    private SelectMealAdapter selectMealAdapter;
    private ExtensionsAdapter extensionsAdapter;
    private CommentAdapter commentAdapter;
    private ProductSliderAdapter mSliderAdapter;
    long orderId;
    List<BranchRecords> list;

    @BindView(R.id.riceExpandMealAdditionsBtn)
    ImageView riceExpandMealAdditionsBtn;
    @BindView(R.id.dishesExpandMealAdditionsBtn)
    ImageView dishesExpandMealAdditionsBtn;

    @BindView(R.id.riceDetailsAdditionsRV)
    RecyclerView riceDetailsAdditionsRV;
    @BindView(R.id.dishesDetailsAdditionsRV)
    RecyclerView dishesDetailsAdditionsRV;
    RiceAdapter riceAdapter;
    dishAdapter dishAdapter;
    @BindView(R.id.addLayout)
    ConstraintLayout addLayout;
    @BindView(R.id.dishLayout)
    ConstraintLayout dishLayout;
    @BindView(R.id.riceLayout)
    ConstraintLayout riceLayout;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.line4)
    View line4;

    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this)
                .get(OrderViewViewModel.class);
        subscribeOrderObserver();
        subscribeActionsObserver();

        riceAdapter = new RiceAdapter();
        dishAdapter = new dishAdapter();

        selectMealAdapter = new SelectMealAdapter();
        selectMealAdapter.mListener = this;
        productDetailsMailsRV.setAdapter(selectMealAdapter);
        extensionsAdapter = new ExtensionsAdapter();
        extensionsAdapter.mListener = this;
        productDetailsAdditionsRV.setAdapter(extensionsAdapter);
        commentAdapter = new CommentAdapter();
        //productDetailsCommentsRV.setAdapter(commentAdapter);
        riceAdapter.mListener = this;
        dishAdapter.mListener = this;
        riceDetailsAdditionsRV.setAdapter(riceAdapter);
        dishesDetailsAdditionsRV.setAdapter(dishAdapter);
        setUpSliderView(getArguments().getString("image"));
        if (getArguments() != null) {
            orderId = getArguments().getLong(AppConstant.PRODUCT_ID);
            mViewModel.invokeOrderApi(orderId);
        }
        mViewModel.invokeBranchesApi();
    }
    private void setUpSliderView(String image) {
        mSliderAdapter = new ProductSliderAdapter(getContext(),image);
        //mSliderAdapter.mListener = this;
        productDetailsSliderRV.setSliderAdapter(mSliderAdapter);
        productDetailsSliderRV.setIndicatorAnimation(IndicatorAnimationType.WORM);
        productDetailsSliderRV.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        productDetailsSliderRV.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        productDetailsSliderRV.setIndicatorUnselectedColor(Color.GRAY);
        productDetailsSliderRV.setIndicatorSelectedColor(Color.WHITE);
        productDetailsSliderRV.setScrollTimeInSec(2); //set scroll delay in seconds
        productDetailsSliderRV.startAutoCycle();
    }
    private void subscribeOrderObserver() {
        mViewModel.getOrderLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<ProductDetailData>>() {
                    @Override
                    public void onChanged(Resource<ProductDetailData> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    detailData = resource.getData();
                                    kindMeal = detailData.getProduct().getPrice().toString();
                                    showProgressBar(false);
                                    fillOrderDetails(detailData);
                                    quntity = detailData.getProduct().getProductSize().get(0).getQuantity();

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
    private void fillOrderDetails(ProductDetailData data) {
        String currentLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
        if (currentLanguage.equalsIgnoreCase(AppConstant.ARABIC_LANGUAGE)) {
            productNameTV.setText(data.getProduct().getTitle());
            productDescriptionTV.setText(data.getProduct().getText());
        } else {
            productDescriptionTV.setText(data.getProduct().getTextEn());
            productNameTV.setText(data.getProduct().getTitleEn());
        }
        productCaloriesTV.setText(getString(R.string.calories) + data.getProduct().getCalorie());
        //productItemsCountTV.setText(data.getProduct().getQuantity() + "");
        productPriceTV.setText(data.getProduct().getPrice());
        productRateTV.setText(data.getProduct().getRate() + "");
        detailData.getProduct().setTotalPrice(Double.valueOf(data.getProduct().getPrice()));
        updateFavoriteIcon();
        if (data.getProduct().getImages().size() > 0) {
            mSliderAdapter.fillAdapterData(data.getProduct().getImages());
            productImage.setVisibility(View.INVISIBLE);
        } else {
            productDetailsSliderRV.setVisibility(View.INVISIBLE);
            Glide.with(getContext()).load(getArguments().getString("image")).placeholder(R.drawable.golden)
                    .into(productImage);
        }
        size = data.getProduct().getProductSize().size();
        riceSize = data.getProduct().getRice().size();
        selectMealAdapter.fillAdapterData(data.getProduct().getProductSize(), data.getProduct().getPrice());
        riceAdapter.fillAdapterData(data.getProduct().getRice(), data.getProduct().getQuantity());
        dishAdapter.fillAdapterData(data.getProduct().getDish());
        extensionsAdapter.fillAdapterData(data.getProduct().getProductExtension(), data.getProduct().getQuantity());
        if (detailData.getProduct().getProductExtension().size() == 0) {
            addLayout.setVisibility(View.GONE);
            line4.setVisibility(View.GONE);
        }
        if (detailData.getProduct().getRice().size() == 0) {
            riceLayout.setVisibility(View.GONE);
            line2.setVisibility(View.GONE);

        }
        if (detailData.getProduct().getDish().size() == 0) {
            dishLayout.setVisibility(View.GONE);
            line3.setVisibility(View.GONE);

        }
    }



    @OnClick(R.id.shareIV)
    public void onShareIVClicked() {
        Picasso.get().load(detailData.getProduct().getImage())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        String currentLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
                        String name = "myImage";
                        if (currentLanguage.equalsIgnoreCase(AppConstant.ARABIC_LANGUAGE)) {
                            name = detailData.getProduct().getTitle();
                        } else {
                            name = detailData.getProduct().getTitleEn();
                        }
                        shareBitmap(bitmap, name);
                    }
                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        ((MainActivity) getActivity()).showToast(getString(R.string.couldnt_share_product_image));
                    }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });

    }

    private void shareBitmap(Bitmap bitmap, String fileName) {
        try {
            File file = new File(getContext().getCacheDir(), fileName + ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_COMPONENT_NAME, fileName);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.productExpandMealBtn)
    public void onProductExpandMealBtnClicked() {
        if (productDetailsMailsRV.getVisibility() == View.VISIBLE) {
            productExpandMealBtn.setRotation(180);
            productDetailsMailsRV.setVisibility(View.GONE);
        } else {
            productExpandMealBtn.setRotation(0);
            productDetailsMailsRV.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.productExpandMealAdditionsBtn)
    public void onProductExpandMealAdditionsBtnClicked() {
        if (productDetailsAdditionsRV.getVisibility() == View.VISIBLE) {
            productExpandMealAdditionsBtn.setRotation(180);
            productDetailsAdditionsRV.setVisibility(View.GONE);
        } else {
            productExpandMealAdditionsBtn.setRotation(0);
            productDetailsAdditionsRV.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.riceExpandMealAdditionsBtn)
    public void onriceExpandMealAdditionsBtnClicked() {
        if (riceDetailsAdditionsRV.getVisibility() == View.VISIBLE) {
            riceExpandMealAdditionsBtn.setRotation(180);
            riceDetailsAdditionsRV.setVisibility(View.GONE);
        } else {
            riceExpandMealAdditionsBtn.setRotation(0);
            riceDetailsAdditionsRV.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.dishesExpandMealAdditionsBtn)
    public void ondishesExpandMealAdditionsBtnClicked() {
        if (dishesDetailsAdditionsRV.getVisibility() == View.VISIBLE) {
            dishesExpandMealAdditionsBtn.setRotation(180);
            dishesDetailsAdditionsRV.setVisibility(View.GONE);
        } else {
            dishesExpandMealAdditionsBtn.setRotation(0);
            dishesDetailsAdditionsRV.setVisibility(View.VISIBLE);
        }
    }


    //    @OnClick(R.id.productExpandMealCommentsBtn)
//    public void onProductExpandMealCommentsBtnClicked() {
////        if (productDetailsCommentsRV.getVisibility() == View.VISIBLE) {
////            productExpandMealCommentsBtn.setRotation(180);
////            productDetailsCommentsRV.setVisibility(View.GONE);
////        } else {
////            productExpandMealCommentsBtn.setRotation(0);
////            productDetailsCommentsRV.setVisibility(View.VISIBLE);
////        }
//    }
    @OnClick(R.id.addToCartBtn)
    public void onAddToCartBtnClicked() {

        if (size == 1) {
            if (riceSize == 0) {
                subscribeBranchesObserver();
                SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss", Locale.ENGLISH);
                String timeDate = sdf.format(new Date());
                if (checktimings(timeDate, closetime, starttime) == true) {
                    detailData.getProduct().getProductExtension().clear();
                    detailData.getProduct().getProductSize().clear();
                    detailData.getProduct().getProductExtension().addAll(extensions);
                    detailData.getProduct().getProductSize().addAll(productSizes);
                    detailData.getProduct().setPrice(String.valueOf(detailData.getProduct().getTotalPrice()));
                    ((MainActivity) getActivity()).addProductToCard(detailData.getProduct());
                    new AlertDialog.Builder(getActivity())
                            .setMessage(getString(R.string.added_succes))
                            .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    detailData = null;

                                    ((MainActivity) getActivity()).navToDestination(R.id.nav_cart);
                                }
                            })
                            .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(getString(R.string.added_failed) + "\n" + starttime + " " + closetime)
                            .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            } else if (riceSize > 0) {
                if (riceselect == 0) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(getString(R.string.select_rice))
                            .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    subscribeBranchesObserver();
                    SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss", Locale.ENGLISH);
                    String timeDate = sdf.format(new Date());
                    if (checktimings(timeDate, closetime, starttime) == true) {
                        detailData.getProduct().getProductExtension().clear();
                        detailData.getProduct().getProductSize().clear();
                        detailData.getProduct().getProductExtension().addAll(extensions);
                        detailData.getProduct().getProductSize().addAll(productSizes);
                        detailData.getProduct().setPrice(String.valueOf(detailData.getProduct().getTotalPrice()));
                        ((MainActivity) getActivity()).addProductToCard(detailData.getProduct());
                        new AlertDialog.Builder(getActivity())
                                .setMessage(getString(R.string.added_succes))
                                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        detailData = null;

                                        ((MainActivity) getActivity()).navToDestination(R.id.nav_cart);
                                    }
                                })
                                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {
                        new AlertDialog.Builder(getActivity())
                                .setMessage(getString(R.string.added_failed) + "\n" + starttime + " " + closetime)
                                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
            }
        } else if (size > 1) {
            if (mealselect == 0) {
                new AlertDialog.Builder(getActivity())
                        .setMessage(getString(R.string.select_meal))
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } else {
                if (riceSize == 0) {
                    subscribeBranchesObserver();
                    SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss", Locale.ENGLISH);
                    String timeDate = sdf.format(new Date());
                    if (checktimings(timeDate, closetime, starttime) == true) {
                        detailData.getProduct().getProductExtension().clear();
                        detailData.getProduct().getProductSize().clear();
                        detailData.getProduct().getProductExtension().addAll(extensions);
                        detailData.getProduct().getProductSize().addAll(productSizes);
                        detailData.getProduct().setPrice(String.valueOf(detailData.getProduct().getTotalPrice()));
                        ((MainActivity) getActivity()).addProductToCard(detailData.getProduct());
                        new AlertDialog.Builder(getActivity())
                                .setMessage(getString(R.string.added_succes))
                                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        detailData = null;
                                        ((MainActivity) getActivity()).navToDestination(R.id.nav_cart);
                                    }
                                })
                                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {
                        new AlertDialog.Builder(getActivity())
                                .setMessage(getString(R.string.added_failed) + "\n" + starttime + " " + closetime)
                                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                } else if (riceSize > 0) {
                    if (riceselect == 0) {
                        new AlertDialog.Builder(getActivity())
                                .setMessage(getString(R.string.select_rice))
                                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {
                        subscribeBranchesObserver();
                        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss", Locale.ENGLISH);
                        String timeDate = sdf.format(new Date());
                        if (checktimings(timeDate, closetime, starttime) == true) {
                            detailData.getProduct().getProductExtension().clear();
                            detailData.getProduct().getProductSize().clear();
                            detailData.getProduct().getProductExtension().addAll(extensions);
                            detailData.getProduct().getProductSize().addAll(productSizes);
                            detailData.getProduct().setPrice(String.valueOf(detailData.getProduct().getTotalPrice()));
                            ((MainActivity) getActivity()).addProductToCard(detailData.getProduct());
                            new AlertDialog.Builder(getActivity())
                                    .setMessage(getString(R.string.added_succes))
                                    .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            detailData = null;

                                            ((MainActivity) getActivity()).navToDestination(R.id.nav_cart);
                                        }
                                    })
                                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        } else {
                            new AlertDialog.Builder(getActivity())
                                    .setMessage(getString(R.string.added_failed) + "\n" + starttime + " " + closetime)
                                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    }
                }
            }
        }
    }

    private boolean checktimings(String time, String endtime,String startTime) {

        String pattern = "kk:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);
            Date date3 = sdf.parse(startTime);

            if(date1.after(date3)&&date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }



    @OnClick({R.id.favoritesIV,R.id.closeIV})
    public void onAddToFavoritesClicked(View view) {
        if (view.getId()==R.id.closeIV)
        {
            dismiss();
        }
        else if (view.getId()==R.id.favoritesIV) {
            if (SharedPreferencesManager.getString(UserName) != null) {
                updateFavoriteIcon();
                mViewModel.invokeAddToFavoritesApi(
                        ((MainActivity) getActivity()).getAccessToken()
                        , detailData.getProduct().getId());
            } else {
                ViewDialog alert = new ViewDialog();
                alert.showDialog(getActivity());
            }
        }
    }

    private void updateFavoriteIcon() {
        Bitmap icon = null;
        if (detailData.getProduct().getIsFavorite() == 0) {
            icon = BitmapFactory.decodeResource(getActivity().getResources(),
                    R.drawable.ic_fav_62);
        } else {
            icon = BitmapFactory.decodeResource(getActivity().getResources(),
                    R.drawable.ic_fav_red_62);
        }
        favoritesIV.setImageBitmap(icon);
    }

    @OnClick(R.id.minusBtn)
    public void onMinusBtnClicked() {
        double count = Double.valueOf(productItemsCountTV.getText().toString());
        if (count <= 1) {
            ((MainActivity) getActivity()).showToast(getString(R.string.order_quantity_must_be_at_least));
        } else {
            count--;
        }
        productItemsCountTV.setText((int) count + "");
        detailData.getProduct().setQuantity((float) count);
        double price = Double.valueOf(kindMeal);
        orderTotal = Math.abs((count * Double.valueOf(price)));
        detailData.getProduct().setTotalPrice(Math.abs((count * Double.valueOf(price)) + (count * Double.valueOf(ext_price) * quntity)) + (dishPrice * count) + (ricePrice * count));
        productPriceTV.setText(String.valueOf(Math.abs((count * Double.valueOf(price)) + (count * Double.valueOf(ext_price) * quntity)) + (dishPrice * count) + (ricePrice * count)));

    }

    @OnClick(R.id.pluseBtn)
    public void onPluseBtnClicked() {
        double count = Double.valueOf(productItemsCountTV.getText().toString());
        count++;
        productItemsCountTV.setText((int) count + "");
        detailData.getProduct().setQuantity((float) count);
        double price = Double.valueOf(kindMeal);
        orderTotal = Math.abs(count * price);
        detailData.getProduct().setTotalPrice(Math.abs((count * Double.valueOf(price)) + (count * Double.valueOf(ext_price) * quntity) + (dishPrice * count) + (ricePrice * count)));
        productPriceTV.setText(String.valueOf(Math.abs((count * Double.valueOf(price)) + (count * Double.valueOf(ext_price) * quntity)) + (dishPrice * count) + (ricePrice * count)));
    }

    public void onExtensionBtnClicked(String price, boolean checked, int p) {
        if (checked) {
            ext_price = ext_price + Double.valueOf(price);
            Toast.makeText(getContext(), "" + ext_price, Toast.LENGTH_SHORT).show();
            productPriceTV.setText(String.valueOf(Math.abs(Double.valueOf(productPriceTV.getText().toString()) + (Double.valueOf(price) * quntity * Double.valueOf(productItemsCountTV.getText().toString())))));
            detailData.getProduct().setTotalPrice(Double.valueOf(productPriceTV.getText().toString()));
        } else {
            ext_price = ext_price - Double.valueOf(price);
            Toast.makeText(getContext(), "" + ext_price, Toast.LENGTH_SHORT).show();
            productPriceTV.setText(String.valueOf(Math.abs(Double.valueOf(productPriceTV.getText().toString()) - (Double.valueOf(price) * quntity * Double.valueOf(productItemsCountTV.getText().toString())))));
            detailData.getProduct().setTotalPrice(Double.valueOf(productPriceTV.getText().toString()));
            extensions.remove(p);
        }


    }

    @Override
    public void onSelectedExtension(ProductExtension record, String s, boolean checked, int p) {
        detailData.getProduct().getProductExtension().add(record);
        onExtensionBtnClicked(s, checked, p);
        extensions.add(record);
        Log.e("price", detailData.getProduct().getPrice());
        Log.e("totalprice", String.valueOf(detailData.getProduct().getTotalPrice()));

    }

    @Override
    public void onSelectedMeal(ProductSize record, int position) {
        productSizes.clear();
        productSizes.add(record);
        detailData.getProduct().getProductSize().add(record);
        if (Double.valueOf(detailData.getProduct().getProductSize().get(position).getDisPrice()) > 0.00)
            kindMeal = detailData.getProduct().getProductSize().get(position).getDisPrice().toString();
        else
            kindMeal = detailData.getProduct().getProductSize().get(position).getPrice().toString();

        extensionsAdapter.fillAdapterData(detailData.getProduct().getProductExtension(), detailData.getProduct().getProductSize().get(position).getQuantity());
        riceAdapter.fillAdapterData(detailData.getProduct().getRice(), detailData.getProduct().getProductSize().get(position).getQuantity());
        quntity = detailData.getProduct().getProductSize().get(position).getQuantity();
        double i = Double.valueOf(productItemsCountTV.getText().toString());
        double x = (Double.valueOf(kindMeal) * i) + (i * (ext_price * quntity)) + (dishPrice * i) + (ricePrice * i);
        productPriceTV.setText(String.valueOf(x));
        mealselect = position + 1;
        productCaloriesTV.setText(getString(R.string.calories) + detailData.getProduct().getProductSize().get(position).getCalorie());
        detailData.getProduct().setTotalPrice(x);
        Log.e("price", detailData.getProduct().getPrice());
        Log.e("totalprice", String.valueOf(detailData.getProduct().getTotalPrice()));

    }
    private void subscribeBranchesObserver() {
        mViewModel.getBranchesLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<BranchRecords>>>() {
                    @Override
                    public void onChanged(Resource<List<BranchRecords>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar(false);
                                    list = resource.getData();
                                    Long branchId = ((MainActivity) getActivity()).getSelectedBranchId();
                                    int id=branchId.intValue();
                                    starttime=list.get(id-1).getOpenTime();
                                    closetime=list.get(id-1).getClosedTime();
                                    break;
                                case ERROR:
                                    showProgressBar(false);
//                                    showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    showProgressBar(true);
                                    break;
                            }
                        }
                    }
                });
    }

    @Override
    public void onSelectedRice(Rice record, int position) {
        detailData.getProduct().getRice().clear();
        detailData.getProduct().getRice().add(record);
        productPriceTV.setText(String.valueOf(Math.abs(Double.valueOf(productPriceTV.getText().toString()) - ((Double.valueOf(ricePrice)) * quntity * Double.valueOf(productItemsCountTV.getText().toString())) + dishPrice * Double.valueOf(productItemsCountTV.getText().toString()))));
        detailData.getProduct().setTotalPrice(Math.abs(Double.valueOf(productPriceTV.getText().toString()) - (Double.valueOf(ricePrice) * quntity * Double.valueOf(productItemsCountTV.getText().toString())) + dishPrice * Double.valueOf(productItemsCountTV.getText().toString())));
        ricePrice = Double.valueOf(record.getPrice());
        productPriceTV.setText(String.valueOf(Math.abs(Double.valueOf(productPriceTV.getText().toString()) + ((Double.valueOf(ricePrice)) * quntity * Double.valueOf(productItemsCountTV.getText().toString()))) + dishPrice * Double.valueOf(productItemsCountTV.getText().toString())));
        detailData.getProduct().setTotalPrice(Double.valueOf(productPriceTV.getText().toString()));
        riceselect = position + 1;
        Log.e("price", detailData.getProduct().getPrice());
        Log.e("totalprice", String.valueOf(detailData.getProduct().getTotalPrice()));
    }

    @Override
    public void onSelectedDish(Dish record, int position) {
        detailData.getProduct().getDish().clear();
        detailData.getProduct().getDish().add(record);
        productPriceTV.setText(String.valueOf(Math.abs(Double.valueOf(productPriceTV.getText().toString()) - ((Double.valueOf(dishPrice)) * Double.valueOf(productItemsCountTV.getText().toString())))));
        detailData.getProduct().setTotalPrice(Math.abs(Double.valueOf(productPriceTV.getText().toString()) - (Double.valueOf(dishPrice) * Double.valueOf(productItemsCountTV.getText().toString()))));
        dishPrice = Double.valueOf(record.getPrice());
        productPriceTV.setText(String.valueOf(Math.abs(Double.valueOf(productPriceTV.getText().toString()) + ((Double.valueOf(dishPrice)) * Double.valueOf(productItemsCountTV.getText().toString())))));
        detailData.getProduct().setTotalPrice(Double.valueOf(productPriceTV.getText().toString()));
        Log.e("price", detailData.getProduct().getPrice());
        Log.e("totalprice", String.valueOf(detailData.getProduct().getTotalPrice()));
    }
}