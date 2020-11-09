package com.golden.goldencorner.ui.main.orederView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.model.Rice;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RiceAdapter extends RecyclerView.Adapter<RiceAdapter.RiceHolder> {
    public int mSelectedItem = -1;
    public AdapterListener mListener;
    float quantity = 0;
    private List<Rice> dataList = new ArrayList<>();

    public void fillAdapterData(List<Rice> dataList, float quantity) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        this.quantity = quantity;
        notifyDataSetChanged();
    }

    public List<Rice> getDataList() {
        return (dataList.size() > 0) ? dataList : new ArrayList<>();
    }

    @NonNull
    @Override
    public RiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RiceAdapter.RiceHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_product_details_select_meals, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RiceHolder holder, int position) {
        Rice record = dataList.get(position);
        holder.mealRBtn.setText(record.getRice_name());
        holder.priceTV.setText(String.valueOf(Float.valueOf(record.getPrice()) * quantity) + holder.itemView.getContext().getString(R.string.sr));
        holder.mealRBtn.setChecked(position == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface AdapterListener {
        void onSelectedRice(Rice record, int position);
    }

    public class RiceHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mealRBtn)
        RadioButton mealRBtn;
        @BindView(R.id.priceTV)
        TextView priceTV;

        public RiceHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.mealRBtn)
        public void onViewClicked() {
            if (mListener != null) {
                mListener.onSelectedRice(dataList.get(getAdapterPosition()), getAdapterPosition());
                mSelectedItem = getAdapterPosition();
                notifyItemRangeChanged(0, dataList.size());
            }
        }
    }
}
