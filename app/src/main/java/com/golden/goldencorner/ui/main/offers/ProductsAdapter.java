package com.golden.goldencorner.ui.main.offers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.LatestProductHolder> {

    private List<Product> dataList = new ArrayList<>();

    public void fillAdapterData(List<Product> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LatestProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LatestProductHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LatestProductHolder mHolder, int position) {
        Context mContext = mHolder.itemView.getContext();
        Product record = dataList.get(position);
        Glide.with(mContext).load(record.getImage()).into(mHolder.productIV);
        String currentLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
        if (currentLanguage.equalsIgnoreCase(AppConstant.ARABIC_LANGUAGE)){
            mHolder.nameTV.setText(record.getTitle());
            mHolder.descriptionTV.setText(record.getText());
        } else {
            mHolder.nameTV.setText(record.getTitleEn());
            mHolder.descriptionTV.setText(record.getTitleEn());
        }
        mHolder.priceTV.setText(record.getPrice()+mHolder.itemView.getContext().getString(R.string.sr));
        float discount = record.getDiscountPrice();
        mHolder.discountTV.setText((record.getDiscountPrice())+mHolder.itemView.getContext().getString(R.string.sr));
        Bitmap icon = null;
        if (record.getIsFavorite() == 0) {
            icon = BitmapFactory.decodeResource(mContext.getResources(),
                    R.drawable.ic_fav_62);
        } else {
            icon = BitmapFactory.decodeResource(mContext.getResources(),
                    R.drawable.ic_fav_red_62);
        }
        mHolder.addToFavIV.setImageBitmap(icon);
        if (discount == 0){
            mHolder.discountTV.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class LatestProductHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productIV)
        ImageView productIV;
        @BindView(R.id.discountTV)
        TextView discountTV;
        @BindView(R.id.nameTV)
        TextView nameTV;
        @BindView(R.id.descriptionTV)
        TextView descriptionTV;
        @BindView(R.id.priceTV)
        TextView priceTV;
        @BindView(R.id.priceDiscountTV)
        TextView priceDiscountTV;
        @BindView(R.id.addToCartIV)
        ImageView addToCartIV;
        @BindView(R.id.addToFavIV)
        ImageView addToFavIV;
        @BindView(R.id.viewLineBottom)
        View viewLineBottom;

        public LatestProductHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.addToCartIV)
        public void onAddToCartIVClicked() {
            if (mListener != null)
                mListener.onAddToCart(dataList.get(getAdapterPosition()));
        }

        @OnClick(R.id.addToFavIV)
        public void onAddToFavIVClicked() {
            if (mListener != null)
                mListener.onAddOrDeleteFavorites(dataList.get(getAdapterPosition()));
        }
    }

    public AdapterListener mListener;
    public interface AdapterListener {
        void onAddToCart(Product record);
        void onAddOrDeleteFavorites(Product record);
    }
}
