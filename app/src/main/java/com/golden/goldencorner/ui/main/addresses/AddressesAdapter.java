package com.golden.goldencorner.ui.main.addresses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.model.AddressRecords;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.BranchHolder> {

    private List<AddressRecords> dataList = new ArrayList<>();

    public void fillAdapterData(List<AddressRecords> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BranchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BranchHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_address_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BranchHolder mHolder, int position) {
        Context mContext = mHolder.itemView.getContext();
        AddressRecords records = dataList.get(position);
        mHolder.addressNameTV.setText(mContext.getString(R.string.address_name)+": "+records.getName());
        mHolder.userNameTV.setText(mContext.getString(R.string.name)+": "+records.getUser().getName());
        mHolder.userCityTV.setText(mContext.getString(R.string.city)+": "+records.getUser().getCity());
        mHolder.userEmailTV.setText(mContext.getString(R.string.email)+": "+records.getUser().getEmail());
        mHolder.userMobileTV.setText(mContext.getString(R.string.mobile_number)+": "+records.getUser().getMobile());
        mHolder.userNeighborhoodTV.setText(mContext.getString(R.string.neighborhood)+": "+records.getState());
        mHolder.userStreetTV.setText(mContext.getString(R.string.street)+": "+records.getAddress());
        mHolder.addressNoteTV.setText(mContext.getString(R.string.address_note)+": "+records.getAddress());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class BranchHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.addressNameTV)
        TextView addressNameTV;
        @BindView(R.id.userNameTV)
        TextView userNameTV;
        @BindView(R.id.userMobileTV)
        TextView userMobileTV;
        @BindView(R.id.userEmailTV)
        TextView userEmailTV;
        @BindView(R.id.userCityTV)
        TextView userCityTV;
        @BindView(R.id.userNeighborhoodTV)
        TextView userNeighborhoodTV;
        @BindView(R.id.userStreetTV)
        TextView userStreetTV;
        @BindView(R.id.addressNoteTV)
        TextView addressNoteTV;
        @BindView(R.id.editAddressBtn)
        CircularProgressButton editAddressBtn;
        @BindView(R.id.space)
        Space space;
        @BindView(R.id.deleteAddressBtn)
        CircularProgressButton deleteAddressBtn;

        public BranchHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.editAddressBtn)
        public void onEditAddressBtnClicked() {
            if (mListener != null)
                mListener.onEditAddress(dataList.get(getAdapterPosition()));
        }
        @OnClick(R.id.deleteAddressBtn)
        public void onDeleteAddressBtnClicked() {
            if (mListener != null)
                mListener.onDeleteAddress(dataList.get(getAdapterPosition()));
        }
    }

    public AdapterListener mListener;

    public interface AdapterListener {
        void onEditAddress(AddressRecords record);
        void onDeleteAddress(AddressRecords record);
    }
}
