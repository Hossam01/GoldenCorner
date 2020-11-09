package com.golden.goldencorner.ui.payment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.AddressRecords;
import com.golden.goldencorner.data.model.CheckoutModel;
import com.golden.goldencorner.data.model.City;
import com.golden.goldencorner.data.model.LimitResponseModel;
import com.golden.goldencorner.data.model.User;
import com.golden.goldencorner.ui.main.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    final Calendar myCalendar = Calendar.getInstance();
    @BindView(R.id.nameET)
    EditText nameET;
    @BindView(R.id.emailET)
    EditText emailET;
    @BindView(R.id.mobileET)
    EditText mobileET;
    @BindView(R.id.addressNoteET)
    EditText addressNoteET;
    @BindView(R.id.deliveryMethodRG)
    RadioGroup deliveryMethodRG;
    @BindView(R.id.deliveryMethodReceiveFromBranchRB)
    RadioButton deliveryMethodReceiveFromBranchRB;
    @BindView(R.id.deliveryMethodDeliverToAddressRB)
    RadioButton deliveryMethodDeliverToAddressRB;
    @BindView(R.id.branchNameET)
    EditText branchNameET;
    @BindView(R.id.calenderViewTV)
    TextView calenderViewTV;
    @BindView(R.id.timeViewTV)
    TextView timeViewTV;
    @BindView(R.id.deliveryMethodSpinnerET)
    Spinner deliveryMethodSpinnerET;
    @BindView(R.id.deliveryMethodCitySpinnerET)
    Spinner deliveryMethodCitySpinnerET;
    @BindView(R.id.deliveryMethodNeighborhoodET)
    EditText deliveryMethodNeighborhoodET;
    @BindView(R.id.deliveryMethodLocationViewTV)
    TextView deliveryMethodLocationViewTV;
    @BindView(R.id.discountCodeTV)
    TextView discountCodeTV;
    @BindView(R.id.addCodeTV)
    TextView addCodeTV;
    @BindView(R.id.discountCodeHintTV)
    TextView discountCodeHintTV;
    @BindView(R.id.deliveryMethodAddressET)
    EditText deliveryMethodAddressET;
    @BindView(R.id.PaymentInfoRG)
    RadioGroup PaymentInfoRG;
    @BindView(R.id.PaymentInfoPayOnReceiptRB)
    RadioButton PaymentInfoPayOnReceiptRB;
    @BindView(R.id.PaymentInfoPayOnReceiptMadaCardRB)
    RadioButton PaymentInfoPayOnReceiptMadaCardRB;
    @BindView(R.id.PaymentInfoOnlinePaymentRB)
    RadioButton PaymentInfoOnlinePaymentRB;
    @BindView(R.id.deliveryMethodReceiveFromBranchLi)
    LinearLayout deliveryMethodReceiveFromBranchLi;
    @BindView(R.id.deliveryMethodDeliverToAddressLi)
    ConstraintLayout deliveryMethodDeliverToAddressLi;
    @BindView(R.id.orderDetailsCollapseBtn)
    ImageView orderDetailsCollapseBtn;
    @BindView(R.id.deliveryMethodCollapseBtn)
    ImageView deliveryMethodCollapseBtn;
    @BindView(R.id.PaymentInfoCollapseBtn)
    ImageView PaymentInfoCollapseBtn;
    @BindView(R.id.PaymentInfoVisaCardBtn)
    ImageView PaymentInfoVisaCardBtn;
    @BindView(R.id.PaymentInfoMadaCardBtn)
    ImageView PaymentInfoMadaCardBtn;
    @BindView(R.id.PaymentInfoStcCardBtn)
    ImageView PaymentInfoStcCardBtn;
    @BindView(R.id.orderDetailsFieldsLi)
    LinearLayout orderDetailsFieldsLi;
    @BindView(R.id.PTV)
    TextView PTV;
    @BindView(R.id.PTV2)
    TextView PTV2;
    @BindView(R.id.tax)
    TextView tax;
    @BindView(R.id.PaymentSummaryOrderTotalPriceTV)
    TextView PaymentSummaryOrderTotalPriceTV;
    @BindView(R.id.PaymentSummaryOrderTaxPriceTV)
    TextView PaymentSummaryOrderTaxPriceTV;
    @BindView(R.id.PaymentSummaryDiscountCodePriceTV)
    TextView PaymentSummaryDiscountCodePriceTV;
    @BindView(R.id.PaymentSummaryTotalPriceTV)
    TextView PaymentSummaryTotalPriceTV;
    @BindView(R.id.discounte)
    TextView discounte;
    @BindView(R.id.hint)
    TextView hint;
    @BindView(R.id.addEvaluteBtn)
    CircularProgressButton addEvaluteBtn;
    @BindView(R.id.addCodeLine)
    View addCodeLine;
    @BindView(R.id.carts)
    LinearLayout carts;
    DatePickerDialog datePickerDialog;
    DatePickerDialog.OnDateSetListener date;
    LimitResponseModel foo;
    int x = 1;
    private PaymentViewModel mViewModel;
    int y = 1;
    int z = 1;
    String token;
    User data;
    int status = 0;
    double total = 0.0;
    double collect = 0.0;
    private ArrayAdapter<String> mSpinnerAdapter = null;

    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.payment_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);
        token = ((MainActivity) getActivity()).getAccessToken();
        String note = getArguments().getString("Note");
        addressNoteET.setText(note);
        mViewModel.invokeCitiesApi();
        mViewModel.invokeAddressesApi(token);
        subscribeCitiesObserver();
        subscribeAddressObserver();
        deliveryMethodRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if (checkedId == R.id.deliveryMethodReceiveFromBranchRB) {
                    deliveryMethodReceiveFromBranchLi.setVisibility(View.VISIBLE);
                    deliveryMethodDeliverToAddressLi.setVisibility(View.GONE);
                    PaymentSummaryOrderTotalPriceTV.setText("0.0");
                    total = getArguments().getDouble("lasttotal") - foo.getDiscount() - Double.valueOf(PaymentSummaryDiscountCodePriceTV.getText().toString());
                    PaymentSummaryTotalPriceTV.setText(String.valueOf(total) + "SR");
                } else if (checkedId == R.id.deliveryMethodDeliverToAddressRB) {
                    deliveryMethodDeliverToAddressLi.setVisibility(View.VISIBLE);
                    deliveryMethodReceiveFromBranchLi.setVisibility(View.GONE);
                    PaymentSummaryOrderTotalPriceTV.setText(String.valueOf(foo.getShipping_price()));
                    if (total != 0.0) {
                        total = total + foo.getDiscount();
                        PaymentSummaryTotalPriceTV.setText(String.valueOf(total) + "SR");
                    }


                }
            }
        });
        PaymentInfoRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.PaymentInfoPayOnReceiptRB) {
                    status = 1;
                    carts.setVisibility(View.GONE);
                    hint.setVisibility(View.GONE);

                } else if (i == R.id.PaymentInfoPayOnReceiptMadaCardRB) {
                    status = 5;
                    carts.setVisibility(View.GONE);
                    hint.setVisibility(View.VISIBLE);

                } else if (i == R.id.PaymentInfoOnlinePaymentRB) {
                    status = 3;
                    carts.setVisibility(View.VISIBLE);
                    hint.setVisibility(View.GONE);

                }
            }
        });
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        calenderViewTV.setText(date);
        collect = getArguments().getDouble("lasttotal");
        data = mViewModel.getDataFromShared();
        nameET.setText(data.getUsername());
        emailET.setText(data.getEmail());
        mobileET.setText(data.getMobile());
        deliveryMethodNeighborhoodET.setText(data.getRegion());
//        date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel();
//            }
//
//        };
        branchNameET.setText(((MainActivity) getActivity()).getSelectedBranchName());

        foo = (LimitResponseModel) getArguments().getParcelable("list");

        if (foo.getHide_branch_tasleem() == 1) {
            deliveryMethodReceiveFromBranchLi.setVisibility(View.GONE);
            deliveryMethodReceiveFromBranchRB.setVisibility(View.GONE);
        }
        if (foo.getHide_cupon() == 1) {
            discountCodeHintTV.setVisibility(View.GONE);
            addCodeTV.setVisibility(View.GONE);
            discountCodeTV.setVisibility(View.GONE);
            addCodeLine.setVisibility(View.GONE);
        }
        if (foo.getHide_delivery() == 1) {
            deliveryMethodDeliverToAddressLi.setVisibility(View.GONE);
            PaymentInfoOnlinePaymentRB.setVisibility(View.GONE);
            PaymentSummaryOrderTotalPriceTV.setVisibility(View.INVISIBLE);
        }
        if (foo.getHide_elec_payment() == 1) {
            PaymentInfoOnlinePaymentRB.setVisibility(View.GONE);
        }
        if (foo.getHide_visa() == 1) {
            PaymentInfoVisaCardBtn.setVisibility(View.GONE);
        }
        if (foo.getHide_stc() == 1) {
            PaymentInfoStcCardBtn.setVisibility(View.GONE);
        }
        if (foo.getHide_mada() == 1) {
            PaymentInfoMadaCardBtn.setVisibility(View.GONE);
        }
        if (foo.getHide_bank_payment() == 1)
            if (foo.getDiscount() == 0) {
                discounte.setVisibility(View.GONE);
                PaymentSummaryOrderTaxPriceTV.setVisibility(View.GONE);
            }


        if (getArguments() != null) {
            PTV.setText(String.valueOf(getArguments().getDouble("total")) + "SR");
            tax.setText(getString(R.string.tax) + foo.getTax() + "% VAT");
            PTV2.setText(String.valueOf(Double.valueOf(foo.getTax() / 100 * getArguments().getDouble("total"))) + "SR");
            PaymentSummaryOrderTotalPriceTV.setText(String.valueOf(Double.valueOf(foo.getShipping_price())) + "SR");
            discounte.setText(getString(R.string.discount_code) + foo.getDiscount() + "%");
            PaymentSummaryOrderTaxPriceTV.setText(String.valueOf(Double.valueOf(getArguments().getDouble("total") * foo.getDiscount() / 100)) + "SR");
            PaymentSummaryTotalPriceTV.setText(String.valueOf(getArguments().getDouble("lasttotal") - Double.valueOf(PaymentSummaryDiscountCodePriceTV.getText().toString())) + "SR");
        }
    }

    private void subscribeCitiesObserver() {
        mViewModel.getCitiesLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<City>>>() {
                    @Override
                    public void onChanged(Resource<List<City>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    setUpSpinnerUi(resource.getData());
                                    break;
                                case ERROR:
                                    ((MainActivity) getActivity()).showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    break;
                            }
                        }
                    }
                });
    }

    private void subscribeAddressObserver() {
        mViewModel.getAddressesLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<AddressRecords>>>() {
                    @Override
                    public void onChanged(Resource<List<AddressRecords>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    setUpSpinnerUiAddress(resource.getData());
                                    break;
                                case ERROR:
                                    ((MainActivity) getActivity()).showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    break;
                            }
                        }
                    }
                });
    }

    @OnClick(R.id.timeViewTV)
    public void setCalenderViewTV(View view) {
//        if (view.getId()==R.id.calenderViewTV)
//        {
//            new DatePickerDialog(getContext(), date, myCalendar
//                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//        }
//        else if (view.getId()==R.id.timeViewTV)
        ((MainActivity) getActivity()).navToDestination(R.id.deliveryDateFragment);
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        calenderViewTV.setText(sdf.format(myCalendar.getTime()));
    }

    private void setUpSpinnerUi(List<City> dataList) {
        mSpinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deliveryMethodCitySpinnerET.setAdapter(mSpinnerAdapter);
        deliveryMethodCitySpinnerET.setOnItemSelectedListener(this);
        for (City records : dataList) {
            String currentLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
            String mLanguage = SharedPreferencesManager.getString(AppConstant.FLAG_CURRENT_LANGUAGE);
            if (currentLanguage.equalsIgnoreCase(mLanguage))
                mSpinnerAdapter.add(records.getName());
            else
                mSpinnerAdapter.add(records.getNameEn());
        }
        mSpinnerAdapter.notifyDataSetChanged();
    }

    private void setUpSpinnerUiAddress(List<AddressRecords> dataList) {
        mSpinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deliveryMethodSpinnerET.setAdapter(mSpinnerAdapter);
        deliveryMethodSpinnerET.setOnItemSelectedListener(this);
        for (AddressRecords records : dataList) {
            mSpinnerAdapter.add(records.getName());
        }
        mSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @OnClick({R.id.deliveryMethodLocationViewTV
            , R.id.deliveryMethodNavLocationBtn})
    public void onPaymentLocationClicked() {
        Bundle bundle = new Bundle();
        bundle.putInt("fragment", 2);
        ((MainActivity) getActivity()).navToDestination(R.id.nav_map_fragment, bundle);
    }

    @OnClick({R.id.orderDetailsCollapseBtn
            , R.id.deliveryMethodCollapseBtn,
            R.id.PaymentInfoCollapseBtn, R.id.discountCodeTV})
    public void expend(View view) {
        if (view.getId() == R.id.orderDetailsCollapseBtn) {
            if (x == 1) {
                orderDetailsFieldsLi.setVisibility(View.GONE);
                x = 0;
            } else if (x == 0) {
                orderDetailsFieldsLi.setVisibility(View.VISIBLE);
                x = 1;
            }
        } else if (view.getId() == R.id.deliveryMethodCollapseBtn) {
            if (y == 1) {
                deliveryMethodRG.setVisibility(View.GONE);
                y = 0;
            } else if (y == 0) {
                deliveryMethodRG.setVisibility(View.VISIBLE);
                y = 1;
            }
        } else if (view.getId() == R.id.PaymentInfoCollapseBtn) {
            if (z == 1) {
                PaymentInfoRG.setVisibility(View.GONE);
                z = 0;
            } else if (z == 0) {
                PaymentInfoRG.setVisibility(View.VISIBLE);
                z = 1;
            }
        } else if (view.getId() == R.id.discountCodeTV) {
            Bundle bundle = new Bundle();
            bundle.putDouble("collect", collect);
            ((MainActivity) getActivity()).navToDestination(R.id.nav_dicount_code, bundle);
        }
    }

    private void subscribeActionsObserver() {
        mViewModel.getcheckoutLiveData().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<CheckoutModel>>>() {
                    @Override
                    public void onChanged(Resource<List<CheckoutModel>> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    ((MainActivity) getActivity()).showProgressBar(false);

                                    // ((MainActivity) getActivity()).showToast("Done");

                                    Bundle bundle = new Bundle();
                                    bundle.putInt("Number", resource.getData().get(0).getOrder_number());
                                    bundle.putString("Date", resource.getData().get(0).getOrder_date());
                                    ((MainActivity) getActivity()).navToDestination(R.id.billFragment, bundle);
                                    break;
                                case ERROR:
                                    ((MainActivity) getActivity()).showProgressBar(false);
                                    ((MainActivity) getActivity()).showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    ((MainActivity) getActivity()).showProgressBar(true);
                                    break;
                            }
                        }
                    }
                });
    }

    @OnClick(R.id.addEvaluteBtn)
    public void checkOut(View view) {

        if (nameET.getText().toString().length() == 0) {
            nameET.setError(getString(R.string.this_field_is_required));
        }
        if (emailET.getText().toString().length() == 0) {
            emailET.setError(getString(R.string.this_field_is_required));
        }
        if (mobileET.getText().toString().length() == 0) {
            mobileET.setError(getString(R.string.this_field_is_required));
        }
        if (status == 0) {
            new AlertDialog.Builder(getActivity())
                    .setMessage(getString(R.string.select_payment_method))
                    .setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            String riceString = "";
            String dishString = "";

            String time = ((MainActivity) getActivity()).gettime();
            long branch_id = ((MainActivity) getActivity()).getSelectedBranchId();
            int numberitem = ((MainActivity) getActivity()).getCardListProducts().size();
            String x = String.valueOf(((MainActivity) getActivity()).getCardListProducts().get(0).getId());
            StringBuilder sb = new StringBuilder(x);

            for (int i = 0; i < numberitem; i++) {

                if (i == 0) {
                    continue;
                } else {
                    String z = String.valueOf(((MainActivity) getActivity()).getCardListProducts().get(i).getId());
                    sb.append("/" + z);
                }
            }

            String name = String.valueOf(((MainActivity) getActivity()).getCardListProducts().get(0).getTitle());
            StringBuilder nb = new StringBuilder(name);
            for (int i = 0; i < numberitem; i++) {

                if (i == 0) {
                    continue;
                } else {
                    String z = String.valueOf(((MainActivity) getActivity()).getCardListProducts().get(i).getProduct_name());
                    nb.append("/" + z);
                }
            }

            if (((MainActivity) getActivity()).getCardListProducts().get(0).getRice().size() == 0) {
                riceString = "";
            } else {


                String rice = String.valueOf(((MainActivity) getActivity()).getCardListProducts().get(0).getRice().get(0).getId());
                StringBuilder rb = new StringBuilder(rice);
                for (int i = 0; i < numberitem; i++) {

                    if (i == 0) {
                        continue;
                    } else {
                        String z = String.valueOf(((MainActivity) getActivity()).getCardListProducts().get(i).getRice().get(i).getId());
                        rb.append("/" + z);
                    }
                }
                riceString = rb.toString();
            }


            if (((MainActivity) getActivity()).getCardListProducts().get(0).getDish().size() == 0) {
                dishString = "";


            } else {
                String dish = String.valueOf(((MainActivity) getActivity()).getCardListProducts().get(0).getDish().get(0).getId());
                StringBuilder db = new StringBuilder(dish);
                for (int i = 0; i < numberitem; i++) {

                    if (i == 0) {
                        continue;
                    } else {
                        String z = String.valueOf(((MainActivity) getActivity()).getCardListProducts().get(i).getDish().get(i).getId());
                        db.append("/" + z);
                    }
                }
                dishString = db.toString();

            }
            String price = String.valueOf(((MainActivity) getActivity()).getCardListProducts().get(0).getPrice());
            StringBuilder pb = new StringBuilder(price);
            for (int i = 0; i < numberitem; i++) {

                if (i == 0) {
                    continue;
                } else {
                    String z = String.valueOf(((MainActivity) getActivity()).getCardListProducts().get(i).getPrice());
                    pb.append("/" + z);
                }
            }

            String quntity = String.valueOf(((MainActivity) getActivity()).getCardListProducts().get(0).getQuantity());
            StringBuilder qb = new StringBuilder(quntity);
            for (int i = 0; i < numberitem; i++) {

                if (i == 0) {
                    continue;
                } else {
                    String z = String.valueOf(((MainActivity) getActivity()).getCardListProducts().get(i).getQuantity());
                    qb.append("/" + z);
                }
            }
            StringBuilder pxb = new StringBuilder("extention");
            StringBuilder eb = new StringBuilder("");
            if (((MainActivity) getActivity()).getCardListProducts().get(0).getProductExtension().size() > 0) {
                String extention = String.valueOf(((MainActivity) getActivity()).getCardListProducts().get(0).getProductExtension().get(0).getId());
                eb = new StringBuilder(extention);
                pxb = new StringBuilder(eb.toString());
                for (int i = 0; i < numberitem; i++) {

                    for (int j = 0; j < ((MainActivity) getActivity()).getCardListProducts().get(i).getProductExtension().size(); j++) {
                        if (j == 0) {
                            continue;
                        } else {
                            String z = String.valueOf(((MainActivity) getActivity()).getCardListProducts().get(i).getProductExtension().get(j).getId());
                            eb.append("," + z);
                        }
                    }
                    pxb.append("/" + eb.toString());
                }
            }

            String total = String.valueOf(((MainActivity) getActivity()).getCardListProducts().get(0).getTotalPrice());
            StringBuilder tb = new StringBuilder(total);
            for (int i = 0; i < numberitem; i++) {

                if (i == 0) {
                    continue;
                } else {
                    String z = String.valueOf(((MainActivity) getActivity()).getCardListProducts().get(i).getTotalPrice());
                    tb.append("/" + z);
                }
            }

            mViewModel.invokeCheckoutApi(((MainActivity) getActivity()).getAccessToken(), nameET.getText().toString(), data.getCountry(),
                    data.getCity(),
                    data.getRegion(), deliveryMethodAddressET.getText().toString(), mobileET.getText().toString(), addressNoteET.getText().toString()
                    , status, time, String.valueOf(branch_id), 0, Double.valueOf(PaymentSummaryTotalPriceTV.getText().toString()), numberitem, numberitem, sb.toString(), nb.toString(), pb.toString(), qb.toString(), eb.toString(), pxb.toString(), riceString, dishString, tb.toString(), ((MainActivity) getActivity()).getLat(), ((MainActivity) getActivity()).getLang(), ((MainActivity) getActivity()).getCopun());
            ((MainActivity) getActivity()).clearCardListProducts();
            subscribeActionsObserver();
        }
    }

}