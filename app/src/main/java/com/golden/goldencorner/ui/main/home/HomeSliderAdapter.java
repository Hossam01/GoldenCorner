package com.golden.goldencorner.ui.main.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.golden.goldencorner.R;
import com.golden.goldencorner.data.model.HomeSliderRecords;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeSliderAdapter extends SliderViewAdapter<HomeSliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<HomeSliderRecords> mSliderItems = new ArrayList<>();
    private int position = 0;

    public void fillAdapterData(List<HomeSliderRecords> dataList) {
        this.mSliderItems.clear();
        this.mSliderItems.addAll(dataList);
        notifyDataSetChanged();
    }

    public HomeSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_slider, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        Context mContext = viewHolder.itemView.getContext();
        this.position = position;
        HomeSliderRecords sliderItem = mSliderItems.get(position);
        Glide.with(mContext).load(sliderItem.getImage())
                .into(viewHolder.homeSliderImage);
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder implements View.OnClickListener {
        @BindView(R.id.homeSliderIV)
        ImageView homeSliderImage;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            homeSliderImage = itemView.findViewById(R.id.homeSliderIV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null)
                mListener.onSliderClicked(mSliderItems.get(position));
        }
    }

    public AdapterListener mListener;
    public interface AdapterListener {
        void onSliderClicked(HomeSliderRecords record);
    }
}
