package com.golden.goldencorner.ui.main.orederView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.model.ProductSize;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectMealAdapter extends RecyclerView.Adapter<SelectMealAdapter.OrderHolder> {

    private List<ProductSize> dataList = new ArrayList<>();

    public void fillAdapterData(List<ProductSize> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public List<ProductSize> getDataList() {
        return (dataList.size() > 0)?dataList:new ArrayList<>();
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_product_details_select_meals, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder mHolder, int position) {
        ProductSize record = dataList.get(position);
        mHolder.mealRBtn.setText(record.getSizeName());
        mHolder.priceTV.setText(record.getPrice()+mHolder.itemView.getContext().getString(R.string.sr));
        if (record.getIsSelected()){
            mHolder.mealRBtn.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mealRBtn)
        RadioButton mealRBtn;
        @BindView(R.id.priceTV)
        TextView priceTV;

        public OrderHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.mealRBtn)
        public void onViewClicked() {
            if (mListener != null) {
                mListener.onSelectedMeal(/*dataList.get(getAdapterPosition()),*/ getAdapterPosition()-1);
            }
        }
    }

    public AdapterListener mListener;

    public interface AdapterListener {
        void onSelectedMeal(/*ProductSize record,*/ int position);
    }
}
