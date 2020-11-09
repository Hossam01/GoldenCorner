package com.golden.goldencorner.ui.main.cart;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.LimitResponse;
import com.golden.goldencorner.data.model.LimitResponseModel;
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.data.model.Product;
import com.golden.goldencorner.ui.ViewDialog;
import com.golden.goldencorner.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.golden.goldencorner.data.Utils.AppConstant.UserName;

public class CartFragment extends Fragment implements CardAdapter.AdapterListener {

    @BindView(R.id.deliveryIV)
    ImageView deliveryIV;
    @BindView(R.id.cardEmptyTV)
    TextView cardEmptyTV;
    @BindView(R.id.addYourOrderBtn)
    CircularProgressButton addYourOrderBtn;
    @BindView(R.id.cardEmptyView)
    ConstraintLayout cardEmptyView;
    @BindView(R.id.productsRV)
    RecyclerView productsRV;
    /* @BindView(R.id.addCodeTV)
     TextView addCodeTV;
     @BindView(R.id.addCodeLine)
     View addCodeLine;
     @BindView(R.id.discountCodeHintTV)
     TextView discountCodeHintTV;
     @BindView(R.id.discountCodeTV)
     TextView discountCodeTV;
     @BindView(R.id.discountCodeLine)
     View discountCodeLine;*/
    @BindView(R.id.orderNoteTV)
    TextView orderNoteTV;
    @BindView(R.id.dicounteCodeTv)
    TextView dicounteCodeTv;
    @BindView(R.id.orderNoteRedLine)
    View orderNoteRedLine;
    @BindView(R.id.orderNoteET)
    EditText orderNoteET;
    @BindView(R.id.orderNoteLine)
    View orderNoteLine;
    @BindView(R.id.summaryTitleTV)
    TextView summaryTitleTV;
    @BindView(R.id.summaryRedLine)
    View summaryRedLine;
    @BindView(R.id.deliveryService)
    TextView PaymentSummaryOrderTotalTV;
    @BindView(R.id.PaymentSummaryOrderTotalPriceTV)
    TextView PaymentSummaryOrderTotalPriceTV;
    @BindView(R.id.discounte)
    TextView PaymentSummaryOrderTaxTV;
    @BindView(R.id.PaymentSummaryOrderTaxPriceTV)
    TextView PaymentSummaryOrderTaxPriceTV;

    @BindView(R.id.PaymentSummaryDiscountCodePriceTV)
    TextView PaymentSummaryDiscountCodePriceTV;
    /*@BindView(R.id.PaymentSummaryDiscountCodeNumberTV)
    TextView PaymentSummaryDiscountCodeNumberTV;*/
    @BindView(R.id.PaymentSummaryTotalTV)
    TextView PaymentSummaryTotalTV;
    @BindView(R.id.PaymentSummaryTotalPriceTV)
    TextView PaymentSummaryTotalPriceTV;
    @BindView(R.id.summaryLi)
    ConstraintLayout summaryLi;
    @BindView(R.id.proceedPurchaseBtn)
    CircularProgressButton proceedPurchaseBtn;
    @BindView(R.id.cardView)
    ScrollView cardView;
    private CartViewModel mViewModel;
    private CardAdapter mAdapter;
    private Meta meta = null;
    private List<Product> products = new ArrayList<>();
    LimitResponseModel limitResponseModel = new LimitResponseModel();

    @BindView(R.id.TotalWithoutTax)
    TextView TotalWithoutTax;
    @BindView(R.id.TotalWithoutTaxPriceTV)
    TextView TotalWithoutTaxPriceTV;
    @BindView(R.id.DeliveryPriceTV)
    TextView DeliveryPriceTV;
    Double totalPrice;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).titleToolbarIv.setText(getString(R.string.cart));

        return inflater.inflate(R.layout.cart_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        for (int i = 0; i < ((MainActivity) getActivity()).getCardListProducts().size(); i++) {
            if (products.size() != ((MainActivity) getActivity()).getCardListProducts().size()) {
                products.add(((MainActivity) getActivity()).getCardListProducts().get(i));
            }
        }
        if (products.size() == 0) {
            cardView.setVisibility(View.GONE);
            cardEmptyView.setVisibility(View.VISIBLE);
        } else {
            cardView.setVisibility(View.VISIBLE);
            cardEmptyView.setVisibility(View.GONE);
            mAdapter = new CardAdapter();
            productsRV.setAdapter(mAdapter);
            mAdapter.fillAdapterData(products, getActivity());
            mAdapter.mListener = this;

            totalPrice = getTotalPrice(products);
            //long dicount = totalPrice*()
            PaymentSummaryOrderTotalPriceTV.setText(totalPrice + " SR");
            mViewModel.invokeOrderLimitApi();
            subscribeLimitObserver();
        }


    }

    private Double getTotalPrice(List<Product> datalist) {
        Double totalPrice = 0.0;
        for (Product product : datalist) {
            totalPrice += Double.valueOf(product.getTotalPrice());
        }
        return totalPrice;
    }

    private void subscribeLimitObserver() {
        mViewModel.getLimitLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<LimitResponse>>() {
                    @Override
                    public void onChanged(Resource<LimitResponse> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    limitResponseModel.setId(resource.getData().getData().getId());
                                    limitResponseModel.setOrder_limit(resource.getData().getData().getOrder_limit());
                                    limitResponseModel.setDiscount(resource.getData().getData().getDiscount());
                                    limitResponseModel.setHide_bank_payment(resource.getData().getData().getHide_bank_payment());
                                    limitResponseModel.setTax(resource.getData().getData().getTax());
                                    limitResponseModel.setDiscount(resource.getData().getData().getDiscount());
                                    limitResponseModel.setShipping_price(resource.getData().getData().getShipping_price());
                                    limitResponseModel.setHide_branch_tasleem(resource.getData().getData().getHide_branch_tasleem());
                                    limitResponseModel.setHide_cupon(resource.getData().getData().getHide_cupon());
                                    limitResponseModel.setHide_delivery(resource.getData().getData().getHide_delivery());
                                    limitResponseModel.setHide_mada(resource.getData().getData().getHide_mada());
                                    limitResponseModel.setHide_elec_payment(resource.getData().getData().getHide_elec_payment());
                                    limitResponseModel.setHide_stc(resource.getData().getData().getHide_stc());
                                    limitResponseModel.setHide_visa(resource.getData().getData().getHide_visa());
                                    limitResponseModel.setHide_tasleem_payment(resource.getData().getData().getHide_tasleem_payment());
                                    if (limitResponseModel.getDiscount() == 0) {
                                        PaymentSummaryDiscountCodePriceTV.setVisibility(View.GONE);
                                        dicounteCodeTv.setVisibility(View.GONE);
                                    }
                                    double tax = limitResponseModel.getTax() / 100 * getTotalPrice(products);
                                    PaymentSummaryOrderTaxTV.setText(getResources().getString(R.string.tax) + (int) limitResponseModel.getTax() + "% VAT");
                                    PaymentSummaryOrderTaxPriceTV.setText(String.valueOf((float) tax) + " SR");
                                    TotalWithoutTaxPriceTV.setText(String.valueOf(getTotalPrice(products) - tax) + " SR");
                                    dicounteCodeTv.setText(getString(R.string.discount_code) + String.valueOf((int) limitResponseModel.getDiscount()) + "%");
                                    DeliveryPriceTV.setText(String.valueOf(limitResponseModel.getShipping_price()) + " SR");
                                    PaymentSummaryDiscountCodePriceTV.setText(String.valueOf(totalPrice * (int) limitResponseModel.getDiscount() / 100) + "SR");
                                    PaymentSummaryTotalPriceTV.setText(String.valueOf((float) ((getTotalPrice(products) - (getTotalPrice(products) * (limitResponseModel.getDiscount() / 100)) + limitResponseModel.getShipping_price()))));
                                    break;
                                case ERROR:
                                    Toast.makeText(getContext(), resource.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    Log.e("Error Cart", resource.getMessage().toString());
                                    break;
                                case LOADING:
                                    break;
                            }
                        }
                    }
                });
    }


    @OnClick(R.id.addYourOrderBtn)
    public void onAddYourOrderBtnClicked(View view) {
        ((MainActivity) getActivity()).navToDestination(R.id.nav_home);
    }

    @OnClick(R.id.proceedPurchaseBtn)
    public void onProceedPurchaseBtnClicked() {
        double total = ((getTotalPrice(products) - (getTotalPrice(products) * (limitResponseModel.getDiscount() / 100)) + limitResponseModel.getShipping_price()));
        if (SharedPreferencesManager.getString(UserName) != null) {
            if (limitResponseModel.getOrder_limit() < getTotalPrice(products)) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("list", limitResponseModel);
                bundle.putDouble("total", getTotalPrice(products));
                bundle.putDouble("lasttotal", total);
                bundle.putString("Note", orderNoteET.getText().toString());
                ((MainActivity) getActivity()).navToDestination(R.id.nav_payment, bundle);
                ((MainActivity) getActivity()).setTotal(total);
            } else {
                new AlertDialog.Builder(getContext())
                        .setMessage(getString(R.string.condition))
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(false)
                        .show();
            }
        }
        // ((MainActivity) getActivity()).navToDestination(R.id.nav_order_evaluate);
        else {
            ViewDialog alert = new ViewDialog();
            alert.showDialog(getActivity());
        }
    }


    @Override
    public void onMinusBtnClicked(List<Product> record) {
        PaymentSummaryOrderTotalPriceTV.setText(getTotalPrice(record) + " SR");
        double tax = limitResponseModel.getTax() / 100 * getTotalPrice(products);
        PaymentSummaryOrderTaxTV.setText(getResources().getString(R.string.tax) + (int) limitResponseModel.getTax() + "% VAT");
        PaymentSummaryOrderTaxPriceTV.setText(String.valueOf((float) tax) + " SR");
        TotalWithoutTaxPriceTV.setText(String.valueOf(getTotalPrice(products) - tax) + " SR");
        DeliveryPriceTV.setText(String.valueOf(limitResponseModel.getShipping_price()) + " SR");
        dicounteCodeTv.setText(getString(R.string.discount_code) + String.valueOf((int) limitResponseModel.getDiscount()) + "%");
        PaymentSummaryDiscountCodePriceTV.setText(String.valueOf(getTotalPrice(record) * (int) limitResponseModel.getDiscount() / 100) + "SR");
        PaymentSummaryTotalPriceTV.setText(String.valueOf((float) ((getTotalPrice(products) - (getTotalPrice(products) * (limitResponseModel.getDiscount() / 100)) + limitResponseModel.getShipping_price()))));

    }

    @Override
    public void onAddBtnClicked(List<Product> record) {
        PaymentSummaryOrderTotalPriceTV.setText(getTotalPrice(record) + " SR");
        double tax = limitResponseModel.getTax() / 100 * getTotalPrice(products);
        PaymentSummaryOrderTaxTV.setText(getResources().getString(R.string.tax) + (int) limitResponseModel.getTax() + "% VAT");
        PaymentSummaryOrderTaxPriceTV.setText(String.valueOf((float) tax) + " SR");
        TotalWithoutTaxPriceTV.setText(String.valueOf(getTotalPrice(products) - tax) + " SR");
        DeliveryPriceTV.setText(String.valueOf(limitResponseModel.getShipping_price()) + " SR");
        dicounteCodeTv.setText(getString(R.string.discount_code) + String.valueOf((int) limitResponseModel.getDiscount()) + "%");
        PaymentSummaryDiscountCodePriceTV.setText(String.valueOf(getTotalPrice(record) * (int) limitResponseModel.getDiscount() / 100) + "SR");
        PaymentSummaryTotalPriceTV.setText(String.valueOf((float) ((getTotalPrice(products) - (getTotalPrice(products) * (limitResponseModel.getDiscount() / 100)) + limitResponseModel.getShipping_price()))));

    }

    @Override
    public void ondeleteBtnClicked(List<Product> record) {
        PaymentSummaryOrderTotalPriceTV.setText(getTotalPrice(record) + " SR");
        PaymentSummaryTotalPriceTV.setText(String.valueOf((float) ((getTotalPrice(record) - (getTotalPrice(record) * (limitResponseModel.getDiscount() / 100)) + limitResponseModel.getShipping_price()))));
    }
}