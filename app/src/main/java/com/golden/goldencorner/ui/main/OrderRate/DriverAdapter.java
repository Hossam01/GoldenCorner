package com.golden.goldencorner.ui.main.OrderRate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Qustions.Answer;
import com.golden.goldencorner.data.Qustions.DriverItem;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverHolder> {

    public AdapterListener mListener;
    Answer answer = new Answer();
    private List<DriverItem> dataList = new ArrayList<>();

    public void fillAdapterData(List<DriverItem> dataList, Answer answer) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        this.answer = answer;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DriverAdapter.DriverHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DriverAdapter.DriverHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.rate_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DriverAdapter.DriverHolder holder, int position) {
        Context mContext = holder.itemView.getContext();
        DriverItem record = dataList.get(position);
        String currentLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
        if (currentLanguage.equalsIgnoreCase(AppConstant.ARABIC_LANGUAGE)) {
            holder.deliveryDriverTv.setText(record.getTitle());
        } else {
            holder.deliveryDriverTv.setText(record.getTitleEn());
        }
        holder.driverWeakRB.setText(answer.getJsonMember1());
        holder.driverAcceptableRB.setText(answer.getJsonMember2());
        holder.driverAverageRB.setText(answer.getJsonMember3());
        holder.driverVeryGoodRB.setText(answer.getJsonMember4());
        holder.driverexcellentRB.setText(answer.getJsonMember5());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface AdapterListener {
        void onAddToCart(String question_id, String rate, String type);
    }

    public class DriverHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.deliveryDriverTv)
        TextView deliveryDriverTv;
        @BindView(R.id.deliveryDriverRG)
        RadioGroup deliveryDriverRG;
        @BindView(R.id.driverWeakRB)
        RadioButton driverWeakRB;
        @BindView(R.id.driverAcceptableRB)
        RadioButton driverAcceptableRB;
        @BindView(R.id.driverAverageRB)
        RadioButton driverAverageRB;
        @BindView(R.id.driverVeryGoodRB)
        RadioButton driverVeryGoodRB;
        @BindView(R.id.driverexcellentRB)
        RadioButton driverexcellentRB;

        public DriverHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.driverWeakRB)
        public void clickdriverWeakRB() {
            mListener.onAddToCart(String.valueOf(dataList.get(getAdapterPosition()).getId()), "1", String.valueOf(dataList.get(getAdapterPosition()).getType()));
        }

        @OnClick(R.id.driverAcceptableRB)
        public void clickdriverAcceptableRB() {
            mListener.onAddToCart(String.valueOf(dataList.get(getAdapterPosition()).getId()), "2", String.valueOf(dataList.get(getAdapterPosition()).getType()));
        }

        @OnClick(R.id.driverAverageRB)
        public void clickdriverAverageRB() {
            mListener.onAddToCart(String.valueOf(dataList.get(getAdapterPosition()).getId()), "3", String.valueOf(dataList.get(getAdapterPosition()).getType()));
        }

        @OnClick(R.id.driverVeryGoodRB)
        public void clickdriverVeryGoodRB() {
            mListener.onAddToCart(String.valueOf(dataList.get(getAdapterPosition()).getId()), "4", String.valueOf(dataList.get(getAdapterPosition()).getType()));
        }

        @OnClick(R.id.driverexcellentRB)
        public void clickdriverexcellentRB() {
            mListener.onAddToCart(String.valueOf(dataList.get(getAdapterPosition()).getId()), "5", String.valueOf(dataList.get(getAdapterPosition()).getType()));
        }

    }
}
