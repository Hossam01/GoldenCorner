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

    public AdapterListener mListener;

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull JavaHolder mHolder, int position) {
        Context mContext = mHolder.itemView.getContext();
        Product record = dataList.get(position);

        Glide.with(mContext).load(record.getImage()).into(mHolder.productIV);
        mHolder.productNameTV.setText(record.getTitle());
        String currentLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
        if (currentLanguage.equalsIgnoreCase(AppConstant.ARABIC_LANGUAGE)) {
            mHolder.productNameTV.setText(record.getTitle());
        } else {
            mHolder.productNameTV.setText(record.getTitleEn());
        }
        if (record.getQuantity() == 0.0) {
            mHolder.productItemsCountTV.setText(record.getQuantity() + 1 + "");
        } else
            mHolder.productItemsCountTV.setText(record.getQuantity() + "");
        if (record.getQuantity() == 0) {
            mHolder.productPriceTV.setText(Double.valueOf(record.getTotalPrice()) / (record.getQuantity() + 1) + mContext.getString(R.string.sr));
            dataList.get(position).setPrice(String.valueOf(Double.valueOf(record.getTotalPrice()) / (record.getQuantity() + 1)));
        } else {
            mHolder.productPriceTV.setText(Double.valueOf(record.getTotalPrice()) / (record.getQuantity()) + mContext.getString(R.string.sr));
            dataList.get(position).setPrice(String.valueOf(Double.valueOf(record.getTotalPrice()) / (record.getQuantity())));
        }
        mHolder.totalPriceTV.setText(record.getTotalPrice() + mContext.getString(R.string.sr));
        for (int i = 0; i < record.getProductSize().size(); i++) {
            mHolder.riceNameTV.append(record.getProductSize().get(i).getSizeName().toString() + "(" + record.getProductSize().get(i).getPrice().toString() + ")" + "\n");
        }
        for (int i = 0; i < record.getProductExtension().size(); i++) {
            if (record.getProductExtension().get(i).isSelect())
                mHolder.riceNameTV.append(record.getProductExtension().get(i).getExtensionName().toString() + "(" + record.getProductExtension().get(i).getPrice().toString() + ")" + "\n");
        }
        for (int i = 0; i < record.getRice().size(); i++) {
            mHolder.riceNameTV.append(record.getRice().get(i).getRice_name().toString() + "(" + record.getRice().get(i).getPrice().toString() + ")" + "\n");
        }
        for (int i = 0; i < record.getDish().size(); i++) {
            mHolder.riceNameTV.append(record.getDish().get(i).getDish_name().toString() + "(" + record.getDish().get(i).getPrice().toString() + ")" + "\n");
        }
        record = null;

    }

    public interface AdapterListener {
        void onMinusBtnClicked(List<Product> record);

        void onAddBtnClicked(List<Product> record);

        void ondeleteBtnClicked(List<Product> record);

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
        @BindView(R.id.riceNameTV)
        TextView riceNameTV;

        public JavaHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.deleteBtn)
        public void onDeleteBtnClicked() {

            ((MainActivity) context).removeProductFromCard(dataList.get(getAdapterPosition()));
            dataList.remove(getAdapterPosition());
            //fillAdapterData(dataList);
            if (((MainActivity) context).getCardListProducts().size() == 0) {
                ((MainActivity) context).navToDestination(R.id.nav_home);
            }
            mListener.ondeleteBtnClicked(dataList);
            //notifyDataSetChanged();
        }

        @OnClick(R.id.minusBtn)
        public void onMinusBtnClicked() {
            long count = (long) dataList.get(getAdapterPosition()).getQuantity();
            if (count > 1)
                count--;
            dataList.get(getAdapterPosition()).setQuantity(count);
            productItemsCountTV.setText(String.valueOf(count));
            dataList.get(getAdapterPosition()).setTotalPrice(Double.valueOf(count * Double.valueOf(dataList.get(getAdapterPosition()).getPrice())));
            totalPriceTV.setText(String.valueOf(count * Double.valueOf(dataList.get(getAdapterPosition()).getPrice())));
            mListener.onMinusBtnClicked(dataList);

        }

        @OnClick(R.id.addBtn)
        public void onAddBtnClicked() {
            long count = (long) dataList.get(getAdapterPosition()).getQuantity() + 1;
            dataList.get(getAdapterPosition()).setQuantity(count);
            productItemsCountTV.setText(String.valueOf(count));
            dataList.get(getAdapterPosition()).setTotalPrice(Double.valueOf(count * Double.valueOf(dataList.get(getAdapterPosition()).getPrice())));
            totalPriceTV.setText(String.valueOf(count * Double.valueOf(dataList.get(getAdapterPosition()).getPrice())));
            mListener.onAddBtnClicked(dataList);

        }
    }
}
