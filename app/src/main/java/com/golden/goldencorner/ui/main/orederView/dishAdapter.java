package com.golden.goldencorner.ui.main.orederView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.model.Dish;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class dishAdapter extends RecyclerView.Adapter<dishAdapter.dishHolder> {

    public int mSelectedItem = -1;
    public AdapterListener mListener;
    private List<Dish> dataList = new ArrayList<>();

    public void fillAdapterData(List<Dish> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public List<Dish> getDataList() {
        return (dataList.size() > 0) ? dataList : new ArrayList<>();
    }

    @NonNull
    @Override
    public dishHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new dishAdapter.dishHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_product_details_select_meals, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull dishHolder holder, int position) {
        Dish record = dataList.get(position);
        holder.mealRBtn.setText(record.getDish_name());
        holder.priceTV.setText(record.getPrice() + holder.itemView.getContext().getString(R.string.sr));
        holder.mealRBtn.setChecked(position == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface AdapterListener {
        void onSelectedDish(Dish record, int position);
    }

    public class dishHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mealRBtn)
        RadioButton mealRBtn;
        @BindView(R.id.priceTV)
        TextView priceTV;

        public dishHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.mealRBtn)
        public void onViewClicked() {
            if (mListener != null) {
                mListener.onSelectedDish(dataList.get(getAdapterPosition()), getAdapterPosition());
                mSelectedItem = getAdapterPosition();
                notifyItemRangeChanged(0, dataList.size());
            }
        }
    }
}
