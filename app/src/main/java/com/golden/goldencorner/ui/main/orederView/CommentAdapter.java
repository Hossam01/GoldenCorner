package com.golden.goldencorner.ui.main.orederView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.golden.goldencorner.R;
import com.golden.goldencorner.data.model.Comment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.OrderHolder> {

    private List<Comment> dataList = new ArrayList<>();

    public void fillAdapterData(List<Comment> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_product_details_comments, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder mHolder, int position) {
        Context mContext = mHolder.itemView.getContext();
        Comment record = dataList.get(position);
        mHolder.commentNameTV.setText(record.getUser().getName());
        mHolder.commentDateTV.setText(record.getCreatedAt());
        mHolder.commentBodyTV.setText(record.getComment());
        if (record.getRate() > 0)
            mHolder.commentRateTV.setRating(record.getRate());
        Glide.with(mContext).load(record.getUser().getAvatar()).into(mHolder.commentIV);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class OrderHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.commentIV)
        ImageView commentIV;
        @BindView(R.id.commentCV)
        CardView commentCV;
        @BindView(R.id.commentNameTV)
        TextView commentNameTV;
        @BindView(R.id.commentDateTV)
        TextView commentDateTV;
        @BindView(R.id.commentRateTV)
        RatingBar commentRateTV;
        @BindView(R.id.commentBodyTV)
        TextView commentBodyTV;

        public OrderHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
