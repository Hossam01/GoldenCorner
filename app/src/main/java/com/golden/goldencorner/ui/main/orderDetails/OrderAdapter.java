package com.golden.goldencorner.ui.main.orderDetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
    private List<Product> dataList = new ArrayList<>();

    public void fillAdapterData(List<Product> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderAdapter.OrderHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_order_details, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        Product record = dataList.get(position);
        holder.Name.setText(record.getProduct_name() + record.getQuantity() + "X");
        for (int i = 0; i < record.getProductExtension().size(); i++)
            holder.extension.append(record.getProductExtension().get(i).getExtensionName() + "\n");
        holder.Price.setText(record.getPrice() + "SR");

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.Name)
        TextView Name;
        @BindView(R.id.extension)
        TextView extension;
        @BindView(R.id.Price)
        TextView Price;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
