package com.golden.goldencorner.ui.main.orederView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.model.ProductExtension;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExtensionsAdapter extends RecyclerView.Adapter<ExtensionsAdapter.OrderHolder> {

    private List<ProductExtension> dataList = new ArrayList<>();

    public void fillAdapterData(List<ProductExtension> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_product_details_extintions_meals, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder mHolder, int position) {
        ProductExtension record = dataList.get(position);
        mHolder.extensionCB.setText(record.getExtensionName());
        mHolder.priceTV.setText(record.getPrice()+mHolder.itemView.getContext().getString(R.string.sr));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class OrderHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.extensionCB)
        CheckBox extensionCB;
        @BindView(R.id.priceTV)
        TextView priceTV;

        public OrderHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.extensionCB)
        public void onViewClicked() {
            if (mListener != null)
                if (extensionCB.isChecked()) {
                    mListener.onSelectedExtension(
                            dataList.get(getAdapterPosition()), dataList.get(getAdapterPosition()).getPrice(),true
                    );
                }
            else {
                    mListener.onSelectedExtension(
                            dataList.get(getAdapterPosition()), dataList.get(getAdapterPosition()).getPrice(),false
                    );
                }
        }
    }

    public AdapterListener mListener;

    public interface AdapterListener {
        void onSelectedExtension(ProductExtension record,String s,boolean checked);
    }
}
