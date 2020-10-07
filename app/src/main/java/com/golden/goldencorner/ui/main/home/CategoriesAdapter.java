package com.golden.goldencorner.ui.main.home;

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
import com.golden.goldencorner.data.model.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder> {

    private List<Category> dataList = new ArrayList<>();

    public void fillAdapterData(List<Category> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoriesHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_categories_rounded, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesHolder mHolder, int position) {
        Context mContext = mHolder.itemView.getContext();
        mHolder.categoryTitleTV.setText(dataList.get(position).getTitle());
        Glide.with(mContext).load(dataList.get(position).getImage())
                .into(mHolder.categoryIV);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class CategoriesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.productIV)
        ImageView categoryIV;
        @BindView(R.id.productTitleTV)
        TextView categoryTitleTV;

        public CategoriesHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null)
                mListener.getUserStories(dataList.get(getAdapterPosition()));
        }
    }

    public AdapterListener mListener;
    public interface AdapterListener {
        void getUserStories(Category record);
    }
}
