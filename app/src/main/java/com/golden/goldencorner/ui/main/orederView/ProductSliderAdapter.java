package com.golden.goldencorner.ui.main.orederView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.golden.goldencorner.R;
import com.golden.goldencorner.data.model.ProductImages;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductSliderAdapter extends SliderViewAdapter<ProductSliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<ProductImages> mSliderItems = new ArrayList<>();
    private int position = 0;

    public void fillAdapterData(List<ProductImages> dataList) {
        this.mSliderItems.clear();
        this.mSliderItems.addAll(dataList);
        notifyDataSetChanged();
    }

    public ProductSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_view_slider, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        Context mContext = viewHolder.itemView.getContext();
        this.position = position;
        ProductImages sliderItem = mSliderItems.get(position);
        Glide.with(mContext).load(sliderItem.getImage())
                .into(viewHolder.homeSliderImage);
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        @BindView(R.id.homeSliderIV)
        ImageView homeSliderImage;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            homeSliderImage = itemView.findViewById(R.id.homeSliderIV);
        }
    }
}
