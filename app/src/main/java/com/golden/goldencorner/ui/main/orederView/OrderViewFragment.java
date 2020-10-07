package com.golden.goldencorner.ui.main.orederView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.ProductDetailData;
import com.golden.goldencorner.data.model.ProductExtension;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.ui.main.MainActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderViewFragment extends Fragment implements ExtensionsAdapter.AdapterListener, SelectMealAdapter.AdapterListener {

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
    @BindView(R.id.productCommentsTV)
    TextView productCommentsTV;
    @BindView(R.id.productHintTV)
    TextView productHintTV;
    @BindView(R.id.productExpandMealCommentsBtn)
    ImageView productExpandMealCommentsBtn;
    @BindView(R.id.productDetailsCommentsRV)
    RecyclerView productDetailsCommentsRV;
    @BindView(R.id.addToCartBtn)
    CircularProgressButton addToCartBtn;
    @BindView(R.id.minusBtn)
    ImageView minusBtn;
    @BindView(R.id.pluseBtn)
    ImageView pluseBtn;
    @BindView(R.id.addEvaluteBtn)
    Button addCommentBtn;

    private OrderViewViewModel mViewModel;
    private ProductDetailData detailData = null;
    private SelectMealAdapter selectMealAdapter;
    private ExtensionsAdapter extensionsAdapter;
    private CommentAdapter commentAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this)
                .get(OrderViewViewModel.class);
        subscribeOrderObserver();
        subscribeActionsObserver();
        selectMealAdapter = new SelectMealAdapter();
        selectMealAdapter.mListener = this;
        productDetailsMailsRV.setAdapter(selectMealAdapter);
        extensionsAdapter = new ExtensionsAdapter();
        extensionsAdapter.mListener = this;
        productDetailsAdditionsRV.setAdapter(extensionsAdapter);
        commentAdapter = new CommentAdapter();
        productDetailsCommentsRV.setAdapter(commentAdapter);
        setUpSliderView();
        if (getArguments() != null) {
            long orderId = getArguments().getLong(AppConstant.PRODUCT_ID);
            mViewModel.invokeOrderApi(orderId);
        }
    }

    private ProductSliderAdapter mSliderAdapter;

    private void setUpSliderView() {
        mSliderAdapter = new ProductSliderAdapter(getContext());
        //mSliderAdapter.mListener = this;
        productDetailsSliderRV.setSliderAdapter(mSliderAdapter);
        productDetailsSliderRV.setIndicatorAnimation(IndicatorAnimationType.WORM);
        productDetailsSliderRV.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        productDetailsSliderRV.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        productDetailsSliderRV.setIndicatorUnselectedColor(Color.GRAY);
        productDetailsSliderRV.setIndicatorSelectedColor(Color.WHITE);
        productDetailsSliderRV.setScrollTimeInSec(4); //set scroll delay in seconds
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
                                    showProgressBar(false);
                                    fillOrderDetails(detailData);
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
        productItemsCountTV.setText(data.getProduct().getQuantity() + "");
        productPriceTV.setText(data.getProduct().getPrice() + getString(R.string.sr));
        productRateTV.setText(data.getProduct().getRate() + "");
        detailData.getProduct().setTotalPrice(Double.valueOf(data.getProduct().getPrice()));
        updateFavoriteIcon();
        mSliderAdapter.fillAdapterData(data.getProduct().getImages());
        selectMealAdapter.fillAdapterData(data.getProduct().getProductSize());
        extensionsAdapter.fillAdapterData(data.getProduct().getProductExtension());
        commentAdapter.fillAdapterData(data.getComments());
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

    @OnClick(R.id.productExpandMealCommentsBtn)
    public void onProductExpandMealCommentsBtnClicked() {
        if (productDetailsCommentsRV.getVisibility() == View.VISIBLE) {
            productExpandMealCommentsBtn.setRotation(180);
            productDetailsCommentsRV.setVisibility(View.GONE);
        } else {
            productExpandMealCommentsBtn.setRotation(0);
            productDetailsCommentsRV.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.addEvaluteBtn)
    public void onAddCommentBtnClicked() {
        ((MainActivity) getActivity()).navToDestination(R.id.nav_order_evaluate);
    }

    @OnClick(R.id.addToCartBtn)
    public void onAddToCartBtnClicked() {

        detailData.getProduct().setPrice(detailData.getProduct().getTotalPrice());
        ((MainActivity) getActivity()).addProductToCard(detailData.getProduct());
    }

    @OnClick(R.id.favoritesIV)
    public void onAddToFavoritesClicked() {
        updateFavoriteIcon();
        mViewModel.invokeAddToFavoritesApi(
                ((MainActivity)getActivity()).getAccessToken()
                , detailData.getProduct().getId());
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
        if (count <= 1){
            ((MainActivity)getActivity()).showToast(getString(R.string.order_quantity_must_be_at_least));
        }else {
            count--;
        }
        productItemsCountTV.setText(count+"");
        detailData.getProduct().setQuantity((float) count);
        double price = Double.valueOf(detailData.getProduct().getPrice());
        detailData.getProduct().setTotalPrice(Math.abs(count-price));
    }

    @OnClick(R.id.pluseBtn)
    public void onPluseBtnClicked() {
        double count = Double.valueOf(productItemsCountTV.getText().toString());
        count++;
        productItemsCountTV.setText(count+"");
        detailData.getProduct().setQuantity((float) count);
        double price = Double.valueOf(detailData.getProduct().getPrice());
        detailData.getProduct().setTotalPrice(Math.abs(count+price));
    }

    @Override
    public void onSelectedExtension(ProductExtension record) {
        detailData.getProduct().getProductExtension().add(record);
    }

    @Override
    public void onSelectedMeal(/*ProductSize record, */int position) {

        //todo comment because crashing
//        List<ProductSize> mList = selectMealAdapter.getDataList();
//        if (mList.size() > 0) {
//            for (ProductSize record:mList) {
//                record.setIsSelected(false);
//            }
//            mList.get(position).setIsSelected(true);
//            selectMealAdapter.fillAdapterData(mList);
//            detailData.getProduct().getProductSize().add(mList.get(position));
//        }
    }
}