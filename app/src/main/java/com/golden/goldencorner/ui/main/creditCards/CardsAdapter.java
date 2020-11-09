package com.golden.goldencorner.ui.main.creditCards;

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
import com.golden.goldencorner.data.model.CardRecords;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.JavaHolder> {

    public CardsAdapter.AdapterListener mListener;
    Context context;
    private List<CardRecords> dataList = new ArrayList<>();

    public void fillAdapterData(List<CardRecords> dataList, Context context) {
        this.context = context;
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JavaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardsAdapter.JavaHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JavaHolder holder, int position) {
        Context mContext = holder.itemView.getContext();
        CardRecords record = dataList.get(position);
        Glide.with(mContext).load(record.getImage()).into(holder.cardIV);
        holder.cardNameTV.setText(record.getName());
        holder.cardNumberTV.setText(record.getCardNumber());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface AdapterListener {
        void deleteItem(CardRecords record, int position);

        void viewItem(CardRecords record, int position);
    }

    public class JavaHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardIV)
        ImageView cardIV;
        @BindView(R.id.cardNameTV)
        TextView cardNameTV;
        @BindView(R.id.cardNumberTV)
        TextView cardNumberTV;
        @BindView(R.id.addCardBtn)
        CircularProgressButton addCardBtn;

        public JavaHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @OnClick(R.id.addCardBtn)
        public void onDeleteClicked() {
//            dataList.remove(getAdapterPosition());
//            notifyDataSetChanged();
            if (mListener != null)
                mListener.deleteItem(dataList.get(getAdapterPosition()), getAdapterPosition());
        }

        @OnClick(R.id.cardIV)
        public void onClicked() {

            if (mListener != null)
                mListener.viewItem(dataList.get(getAdapterPosition()), getAdapterPosition());
        }

    }
}
