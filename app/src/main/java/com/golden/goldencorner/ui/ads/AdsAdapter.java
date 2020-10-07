package com.golden.goldencorner.ui.ads;

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
import com.golden.goldencorner.data.model.AdsRecords;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.JavaHolder> {

    private List<AdsRecords> dataList = new ArrayList<>();
    Context context;

    public void fillAdapterData(List<AdsRecords> dataList,Context context) {
        this.context=context;
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JavaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JavaHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_ads, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JavaHolder mHolder, int position) {
        mHolder.adsTitleTV.setText(dataList.get(position).getTitle());
        Glide.with(context).load(dataList.get(position).getImage()).into(mHolder.adsIV);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class JavaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.adsTitleTV)
        TextView adsTitleTV;
        @BindView(R.id.adsIV)
        ImageView adsIV;

        public JavaHolder(View view) {
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
        void getUserStories(AdsRecords record);
    }
}
