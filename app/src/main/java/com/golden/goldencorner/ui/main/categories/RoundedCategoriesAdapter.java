package com.golden.goldencorner.ui.main.categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import de.hdodenhof.circleimageview.CircleImageView;

public class RoundedCategoriesAdapter extends RecyclerView.Adapter<RoundedCategoriesAdapter.JavaHolder> {

    private List<Category> dataList = new ArrayList<>();

    public void fillAdapterData(List<Category> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JavaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JavaHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_categories_rounded, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JavaHolder mHolder, int position) {
        Context mContext = mHolder.itemView.getContext();

        Category record = dataList.get(position);
        mHolder.productIV.setBackgroundResource(dataList.get(position).getBackground());
        mHolder.productTitleTV.setText(record.getTitle());
        try {
            Glide.with(mContext).load(record.getImage()).placeholder(R.drawable.golden).dontAnimate().into(mHolder.productIV);
        }catch (Exception e){e.getMessage();}
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class JavaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.productIV)
        CircleImageView productIV;
        @BindView(R.id.productTitleTV)
        TextView productTitleTV;

        public JavaHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onSubCategoriesClicked(dataList.get(getAdapterPosition()),getAdapterPosition());
            }
        }
    }

    public AdapterListener mListener;

    public interface AdapterListener {
        void onSubCategoriesClicked(Category record,int position);
    }
}
