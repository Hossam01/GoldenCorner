package com.golden.goldencorner.ui.main.cart;

import android.content.Context;
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
import com.golden.goldencorner.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.JavaHolder> {

    private List<Product> dataList = new ArrayList<>();
    Context context;
    public void fillAdapterData(List<Product> dataList,Context context) {
        this.context=context;
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JavaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JavaHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_card_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JavaHolder mHolder, int position) {
        Context mContext = mHolder.itemView.getContext();
        Product record = dataList.get(position);

        Glide.with(mContext).load(record.getImage()).into(mHolder.productIV);
        mHolder.productNameTV.setText(record.getTitle());
        String currentLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
        if (currentLanguage.equalsIgnoreCase(AppConstant.ARABIC_LANGUAGE)){
            mHolder.productNameTV.setText(record.getTitle());
        } else {
            mHolder.productNameTV.setText(record.getTitleEn());
        }
        if (record.getQuantity()==0.0) {
            mHolder.productItemsCountTV.setText(record.getQuantity() +1+ "");
        }
        else
            mHolder.productItemsCountTV.setText(record.getQuantity() +"");
        mHolder.productPriceTV.setText(record.getPrice()+mContext.getString(R.string.sr));
        mHolder.totalPriceTV.setText(record.getTotalPrice()+mContext.getString(R.string.sr));

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class JavaHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productIV)
        ImageView productIV;
        @BindView(R.id.productNameTV)
        TextView productNameTV;
        @BindView(R.id.deleteBtn)
        TextView deleteBtn;
        @BindView(R.id.minusBtn)
        ImageView minusBtn;
        @BindView(R.id.productItemsCountTV)
        TextView productItemsCountTV;
        @BindView(R.id.addBtn)
        ImageView addBtn;
        @BindView(R.id.productPriceTV)
        TextView productPriceTV;
        @BindView(R.id.totalPriceHintTV)
        TextView totalPriceHintTV;
        @BindView(R.id.totalPriceTV)
        TextView totalPriceTV;

        public JavaHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.deleteBtn)
        public void onDeleteBtnClicked() {

            ((MainActivity)context).removeProductFromCard(dataList.get(getAdapterPosition()));
            dataList.remove(getAdapterPosition());

            //fillAdapterData(dataList);
            notifyDataSetChanged();
        }

        @OnClick(R.id.minusBtn)
        public void onMinusBtnClicked() {
            long count = (long) dataList.get(getAdapterPosition()).getQuantity();
            if (count > 1)
                count--;
            dataList.get(getAdapterPosition()).setQuantity(count);
            productItemsCountTV.setText(String.valueOf(count));
        }

        @OnClick(R.id.addBtn)
        public void onAddBtnClicked() {
            long count = (long) dataList.get(getAdapterPosition()).getQuantity()+1;
            dataList.get(getAdapterPosition()).setQuantity(count);
            productItemsCountTV.setText(String.valueOf(count));
        }
    }

//    public AdapterListener mListener;
//    public interface AdapterListener {
//        void getUserStories(AdsRecords record);
//    }
}
