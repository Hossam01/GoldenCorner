package com.golden.goldencorner.ui.main.categories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
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

public class SubCategoryProductAdapter extends RecyclerView.Adapter<SubCategoryProductAdapter.JavaHolder> {

    private List<Product> dataList = new ArrayList<>();
    public void fillAdapterData(List<Product> dataList) {


        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JavaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JavaHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_sub_category_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JavaHolder mHolder, int position) {
        Product record = dataList.get(position);
        String currentLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
        if (currentLanguage.equalsIgnoreCase(AppConstant.ARABIC_LANGUAGE)){
            mHolder.nameTV.setText(record.getTitle());
            mHolder.caloriesTV.setText(record.getTitle());
        } else {
            mHolder.nameTV.setText(record.getTitleEn());
            mHolder.caloriesTV.setText(record.getTitleEn());
        }
        mHolder.caloriesTV.setText(mHolder.itemView.getContext().getString(R.string.calories)+record.getCalorie());
        mHolder.priceTV.setText(record.getPrice()+mHolder.itemView.getContext().getString(R.string.sr));

        mHolder.priceDiscountTV.setText(record.getDiscountPrice()+mHolder.itemView.getContext().getString(R.string.sr));
        Context mContext = mHolder.itemView.getContext();
        try {
            Glide.with(mContext).load(record.getImage()).placeholder(R.drawable.golden).dontAnimate().into(mHolder.categoryIV);
        }catch (Exception e){}
        if ( Double.valueOf(record.getDiscountPrice()) <= 0)
            mHolder.priceDiscountTV.setVisibility(View.INVISIBLE);
        else
            mHolder.priceTV.setPaintFlags(mHolder.priceTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Bitmap icon = null;
        if (dataList.get(position).getIsFavorite()==0)
        {
            icon = BitmapFactory.decodeResource(mHolder.itemView.getResources(),
                    R.drawable.ic_fav_62);
        }else {
            icon = BitmapFactory.decodeResource(mHolder.itemView.getResources(),
                    R.drawable.ic_fav_red_62);
        }
        mHolder.favoritesIV.setImageBitmap(icon);


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class JavaHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productIV)
        ImageView categoryIV;
        @BindView(R.id.nameTV)
        TextView nameTV;
        @BindView(R.id.caloriesTV)
        TextView caloriesTV;
        @BindView(R.id.priceTV)
        TextView priceTV;
        @BindView(R.id.priceDiscountTV)
        TextView priceDiscountTV;
        @BindView(R.id.favoritesIV)
        ImageView favoritesIV;
        @BindView(R.id.addToCartIV)
        ImageView addToCartIV;


        public JavaHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick({R.id.addToCartIV,R.id.productIV})
        public void onViewClicked(View view) {
            if (mListener != null) {
                if (view.getId()==R.id.addToCartIV)
                    mListener.onAddToCartClicked(dataList.get(getAdapterPosition()));
                if (view.getId()==R.id.productIV)
                    mListener.onAddToCartClickedDetails(dataList.get(getAdapterPosition()));
            }

        }
    }

    public AdapterListener mListener;
    public interface AdapterListener {
        void onAddToCartClicked(Product record);
        void onAddToCartClickedDetails(Product record);
    }
}
