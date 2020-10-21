package com.golden.goldencorner.ui.main.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
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

public class CartFragment extends Fragment {

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
    @BindView(R.id.addCodeTV)
    TextView addCodeTV;
    @BindView(R.id.addCodeLine)
    View addCodeLine;
    @BindView(R.id.discountCodeHintTV)
    TextView discountCodeHintTV;
    @BindView(R.id.discountCodeTV)
    TextView discountCodeTV;
    @BindView(R.id.discountCodeLine)
    View discountCodeLine;
    @BindView(R.id.orderNoteTV)
    TextView orderNoteTV;
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
    @BindView(R.id.PaymentSummaryOrderTotalTV)
    TextView PaymentSummaryOrderTotalTV;
    @BindView(R.id.PaymentSummaryOrderTotalPriceTV)
    TextView PaymentSummaryOrderTotalPriceTV;
    @BindView(R.id.PaymentSummaryOrderTaxTV)
    TextView PaymentSummaryOrderTaxTV;
    @BindView(R.id.PaymentSummaryOrderTaxPriceTV)
    TextView PaymentSummaryOrderTaxPriceTV;
    @BindView(R.id.PaymentSummaryDiscountCodeTV)
    TextView PaymentSummaryDiscountCodeTV;
    @BindView(R.id.PaymentSummaryDiscountCodePriceTV)
    TextView PaymentSummaryDiscountCodePriceTV;
    @BindView(R.id.PaymentSummaryDiscountCodeNumberTV)
    TextView PaymentSummaryDiscountCodeNumberTV;
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


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cart_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        products.addAll(((MainActivity) getActivity()).getCardListProducts());

        if (products.size() == 0) {
            cardView.setVisibility(View.GONE);
            cardEmptyView.setVisibility(View.VISIBLE);
        } else {
            cardView.setVisibility(View.VISIBLE);
            cardEmptyView.setVisibility(View.GONE);
            mAdapter = new CardAdapter();
            productsRV.setAdapter(mAdapter);
            mAdapter.fillAdapterData(products,getActivity());

            Double totalPrice = getTotalPrice();
            //long dicount = totalPrice*()
            PaymentSummaryOrderTotalPriceTV.setText(totalPrice+"");
        }
    }

    private Double getTotalPrice() {
        Double totalPrice = 0.0;
        for (Product product: products) {
            totalPrice+=Double.valueOf(product.getPrice());
        }
        return totalPrice;
    }

    @OnClick({R.id.addYourOrderBtn,R.id.discountCodeTV})
    public void onAddYourOrderBtnClicked(View view) {
        if (view.getId()==R.id.addYourOrderBtn)
            ((MainActivity)getActivity()).navToDestination(R.id.nav_home);
        else if (view.getId()==R.id.discountCodeTV)
            ((MainActivity)getActivity()).navToDestination(R.id.nav_dicount_code);
    }

    @OnClick(R.id.proceedPurchaseBtn)
    public void onProceedPurchaseBtnClicked() {
        if (SharedPreferencesManager.getString(UserName)!= null){
            ((MainActivity) getActivity()).navToDestination(R.id.nav_payment);
        }
           // ((MainActivity) getActivity()).navToDestination(R.id.nav_order_evaluate);
        else {
            ViewDialog alert = new ViewDialog();
            alert.showDialog(getActivity());
        }
    }


}