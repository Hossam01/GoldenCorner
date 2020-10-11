package com.golden.goldencorner.ui.main.branches;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.BranchRecords;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BranchesAdapter extends RecyclerView.Adapter<BranchesAdapter.BranchHolder> {

    private List<BranchRecords> dataList = new ArrayList<>();
    public void fillAdapterData(List<BranchRecords> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BranchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BranchHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_branches, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BranchHolder mHolder, int position) {
        String currentLang = SharedPreferencesManager.getCurrentLang();
        if (currentLang.equalsIgnoreCase(AppConstant.ARABIC_LANGUAGE)) {
            mHolder.branchTitleTV.setText(dataList.get(position).getName());
        } else {
            mHolder.branchTitleTV.setText(dataList.get(position).getNameEn());
        }

        mHolder.branchDistanceTV.setText(dataList.get(position).getDistance());
        Context mContext = mHolder.itemView.getContext();
        Glide.with(mContext).load(dataList.get(position)
                .getImage()).placeholder(R.drawable.golden).into(mHolder.branchesIV);
       SimpleDateFormat sdf=new SimpleDateFormat("kk:mm:ss", Locale.ENGLISH);
        String timeDate=sdf.format(new Date());
        Log.d("time",timeDate);
        if (checktimings(timeDate,dataList.get(position).getClosedTime(),dataList.get(position).getOpenTime())==true) {
            mHolder.openTV.setText(R.string.open_now);
            Drawable drawable = ResourcesCompat.getDrawable(
                    mHolder.itemView.getContext().getResources()
                    , R.drawable.rounded_grean_bg, null);
            mHolder.openIV.setImageDrawable(drawable);
        } else {
            mHolder.openTV.setText(R.string.close_now);
            Drawable drawable = ResourcesCompat.getDrawable(
                    mHolder.itemView.getContext().getResources()
                    , R.drawable.rounded_red_bg, null);
            mHolder.openIV.setImageDrawable(drawable);
        }
    }
    private boolean checktimings(String time, String endtime,String startTime) {

        String pattern = "kk:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);
            Date date3 = sdf.parse(startTime);

            if(date1.after(date3)&&date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class BranchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.branchesIV)
        ImageView branchesIV;
        @BindView(R.id.branchTitleTV)
        TextView branchTitleTV;
        @BindView(R.id.branchDistanceTV)
        TextView branchDistanceTV;
        @BindView(R.id.openIV)
        ImageView openIV;
        @BindView(R.id.openTV)
        TextView openTV;

        public BranchHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null)
                mListener.onSelectBranch(dataList.get(getAdapterPosition()), getAdapterPosition());
        }
    }

    public AdapterListener mListener;

    public interface AdapterListener {
        void onSelectBranch(BranchRecords record, int position);
    }
}
