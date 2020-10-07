package com.golden.goldencorner.ui.main.profile;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.model.ProfileResponse;
import com.golden.goldencorner.ui.main.MainActivity;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class EditMobileFragment extends Fragment {


    @BindView(R.id.newMovbileET)
    EditText newMovbileET;
    @BindView(R.id.saveBtn)
    CircularProgressButton sendBtn;
    @BindView(R.id.mProgressPar)
    ProgressBar mProgressPar;
    @BindView(R.id.oldMobileET)
    TextView oldMobileET;
    private ProfileViewModel mViewModel;

    public EditMobileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ((MainActivity) getActivity()).titleToolbarIv.setText(getString(R.string.mobile_number));
        return inflater.inflate(R.layout.fragment_edit_mobile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        oldMobileET.setText(mViewModel.getDataFromShared().getMobile());
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(newMovbileET.getText().toString())) {
                    newMovbileET.setError(getString(R.string.this_field_is_required));
                    return;
                } else {
                    resetPasswordRequest();
                }
            }
        });
    }

    private void resetPasswordRequest() {
        String mobile = newMovbileET.getText().toString();


        if (mobile.isEmpty()) {
            newMovbileET.setError(getString(R.string.this_field_is_required));
            return;
        }
        showProgressBar(true);
        mViewModel.editMobile(mobile).subscribe(new Consumer<ProfileResponse>() {
            @Override
            public void accept(ProfileResponse editProfileResponse) throws Exception {
                mViewModel.saveDataToShared(editProfileResponse.getData().get(0).getModel());
                Toast.makeText(getContext(), getString(R.string.successful_message), Toast.LENGTH_SHORT).show();
                showProgressBar(false);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i("abood", "accept: " + throwable.getMessage());
                showProgressBar(false);
            }
        });

    }


    private void showProgressBar(boolean isLoading) {
        if (isLoading) {
            sendBtn.startMorphAnimation();
            sendBtn.setBackground(getContext().getDrawable(R.drawable.rounded_red_bg));
        } else {
            sendBtn.stopAnimation();
            sendBtn.setBackground(getContext().getDrawable(R.drawable.login_button_shape));
        }
    }
}