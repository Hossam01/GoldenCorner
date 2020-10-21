package com.golden.goldencorner.ui.main.termsAndCondition;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.model.ResponseTerms;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ViewDialog {
    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.terms_and_condition_fragment);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView title = dialog.findViewById(R.id.titleTVterms);
        TextView termsAndConditionsTV = dialog.findViewById(R.id.termsAndConditionsTV);

        RetrofitProvider.getClient().getTermsAndConditions()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((ResponseTerms termsAndConditionsRecords) -> {
                    title.setText(termsAndConditionsRecords.getData().get(0).getTitle());
                    termsAndConditionsTV.setText(termsAndConditionsRecords.getData().get(0).getContent());
                }, throwable -> {
                });



        dialog.show();
    }
}
