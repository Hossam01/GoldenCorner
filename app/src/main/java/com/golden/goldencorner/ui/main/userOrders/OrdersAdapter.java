package com.golden.goldencorner.ui.main.userOrders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.model.OrderRecords;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderHolder> {

    private List<OrderRecords> dataList = new ArrayList<>();

    public void fillAdapterData(List<OrderRecords> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_my_orders, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder mHolder, int position) {
        Context mContext = mHolder.itemView.getContext();
        OrderRecords record = dataList.get(position);
//        Picasso.get().load(record.getImage()).into(mHolder.productIV);
        mHolder.orderNumberTV.setText(record.getId()+"#");

        mHolder.orderDateTV.setText(record.getDate());
        mHolder.orderTotalTV.setText(record.getTotalPrice()+"");
        setDeliverStatus(mHolder, record.getDeliveryStatus());
//        String currentLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
//        if (currentLanguage.equalsIgnoreCase(AppConstant.ARABIC_LANGUAGE)) {
//            mHolder.descriptionTV.setText(record.getText());
//        } else {
//            mHolder.nameTV.setText(record.getTitleEn());
//            mHolder.descriptionTV.setText(record.getTitleEn());
//        }
//        if (record.getStatus())
    }

    private void setDeliverStatus(OrderHolder mHolder, Long status) {
        try {


            if (status == 0) {// when status is new order
                mHolder.orderNewIV.setSelected(true);
                mHolder.orderPreparedIV.setSelected(false);
                mHolder.orderOnTheWayIV.setSelected(false);
                mHolder.orderDeliveredIV.setSelected(false);
                mHolder.contactDriverBtn.setVisibility(View.GONE);
                mHolder.orderEvaluateBtn.setVisibility(View.GONE);
            } else if (status == 1) {// when status is under prepare
                mHolder.orderNewIV.setSelected(true);
                mHolder.orderPreparedIV.setSelected(true);
                mHolder.orderOnTheWayIV.setSelected(false);
                mHolder.orderDeliveredIV.setSelected(false);
                mHolder.contactDriverBtn.setVisibility(View.GONE);
                mHolder.cancelBtn.setVisibility(View.INVISIBLE);
                mHolder.orderEvaluateBtn.setVisibility(View.GONE);

            } else if (status == 2) {// when status is on The way
                mHolder.orderNewIV.setSelected(true);
                mHolder.orderPreparedIV.setSelected(true);
                mHolder.orderOnTheWayIV.setSelected(true);
                mHolder.orderDeliveredIV.setSelected(false);
                mHolder.contactDriverBtn.setVisibility(View.VISIBLE);
                mHolder.cancelBtn.setVisibility(View.INVISIBLE);
                mHolder.orderEvaluateBtn.setVisibility(View.GONE);

            } else if (status == 3 || status == null) { // when status is Delivered
                mHolder.orderNewIV.setSelected(true);
                mHolder.orderPreparedIV.setSelected(true);
                mHolder.orderOnTheWayIV.setSelected(true);
                mHolder.orderDeliveredIV.setSelected(true);
                mHolder.contactDriverBtn.setVisibility(View.INVISIBLE);
                mHolder.cancelBtn.setVisibility(View.INVISIBLE);
                mHolder.orderEvaluateBtn.setVisibility(View.VISIBLE);

            }
        }catch (NullPointerException ex){}
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.orderDetailsBtn)
        Button orderDetailsBtn;
        @BindView(R.id.cancelBtn)
        Button cancelBtn;
        @BindView(R.id.orderNewTV)
        TextView orderNewTV;
        @BindView(R.id.orderPreparedTV)
        TextView orderPreparedTV;
        @BindView(R.id.orderOnTheWayTV)
        TextView orderOnTheWayTV;
        @BindView(R.id.orderDeliveredTV)
        TextView orderDeliveredTV;
        @BindView(R.id.orderDateTV)
        TextView orderDateTV;
        @BindView(R.id.orderNumberTV)
        TextView orderNumberTV;
        @BindView(R.id.orderTotalTV)
        TextView orderTotalTV;
        @BindView(R.id.contactDriverBtn)
        TextView contactDriverBtn;
        @BindView(R.id.orderEvaluateBtn)
        TextView orderEvaluateBtn;
        @BindView(R.id.orderNewIV)
        ImageView orderNewIV;
        @BindView(R.id.orderPreparedIV)
        ImageView orderPreparedIV;
        @BindView(R.id.orderOnTheWayIV)
        ImageView orderOnTheWayIV;
        @BindView(R.id.orderDeliveredIV)
        ImageView orderDeliveredIV;

        public OrderHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.orderDetailsBtn
                , R.id.contactDriverBtn
                , R.id.orderEvaluateBtn
                ,R.id.cancelBtn})
        public void onViewClicked(View view) {
            if (mListener != null) {
                switch (view.getId()) {
                    case R.id.orderDetailsBtn:
                        mListener.onOrderDetailsClicked(dataList.get(getAdapterPosition()));
                        break;
                    case R.id.contactDriverBtn:
                        mListener.onContactDriverClicked(dataList.get(getAdapterPosition()));
                        break;
                    case R.id.orderEvaluateBtn:
                        mListener.OnOrderEvaluateClicked(dataList.get(getAdapterPosition()));
                        break;
                    case R.id.cancelBtn:
                        mListener.onCancelOrderClicked(dataList.get(getAdapterPosition()));
                        break;
                }
            }
        }
    }

    public AdapterListener mListener;

    public interface AdapterListener {
        void onCancelOrderClicked(OrderRecords record);
        void onContactDriverClicked(OrderRecords record);
        void onOrderDetailsClicked(OrderRecords record);
        void OnOrderEvaluateClicked(OrderRecords record);
    }
}
