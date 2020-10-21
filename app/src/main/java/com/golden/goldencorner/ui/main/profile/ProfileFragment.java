package com.golden.goldencorner.ui.main.profile;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.model.ProfileResponse;
import com.golden.goldencorner.data.model.User;
import com.golden.goldencorner.ui.main.MainActivity;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;


public class ProfileFragment extends Fragment {

    private NavController navController;
    @BindView(R.id.nameTv)
    TextView nameTv;
    @BindView(R.id.firstNameET)
    EditText firstNameET;
    @BindView(R.id.lastNameTv)
    TextView lastNameTv;
    @BindView(R.id.lastNameET)
    EditText lastNameET;
    @BindView(R.id.emailTv)
    TextView emailTv;
    @BindView(R.id.emailET)
    EditText emailET;
    @BindView(R.id.passwordTv)
    TextView passwordTv;
    @BindView(R.id.passwordET)
    TextView passwordET;
    @BindView(R.id.editPasswordBtn)
    CircularProgressButton editPasswordBtn;
    @BindView(R.id.mobileNumberProfileET)
    TextView mobileNumberProfileET;
    @BindView(R.id.mobileNumberET)
    TextView mobileNumberET;
    @BindView(R.id.editMobileNumberBtn)
    CircularProgressButton editMobileNumberBtn;
    @BindView(R.id.myPointsTv)
    TextView myPointsTv;
    @BindView(R.id.myPointsET)
    EditText myPointsET;
    @BindView(R.id.saveBtn)
    CircularProgressButton saveBtn;
    private ProfileViewModel mViewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).titleToolbarIv.setText(getString(R.string.my_account));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        bindUserData();

        editPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_nav_account_to_editPasswordFragment);
            }
        });
        editMobileNumberBtn.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       navController.navigate(R.id.action_nav_account_to_editMobileFragment);
                                                   }
                                               }
        );
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
    }


    private void editProfile() {
        String fName = firstNameET.getText().toString();
        String lName = lastNameET.getText().toString();
        String email = emailET.getText().toString();

        if (TextUtils.isEmpty(fName)) {
            firstNameET.setError(getString(R.string.this_field_is_required));
            return;
        }
        if (TextUtils.isEmpty(lName)) {
            lastNameET.setError(getString(R.string.this_field_is_required));
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError(getString(R.string.invalid_email));
            return;
        }

        showProgressBar(true);
        mViewModel.updateProfileData(fName, lName, email, null,
                null, null).subscribe(new Consumer<ProfileResponse>() {
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

    private void bindUserData() {
        User data = mViewModel.getDataFromShared();
        firstNameET.setText(data.getName());
        lastNameET.setText(data.getUsername());
        emailET.setText(data.getEmail());
        mobileNumberET.setText(data.getMobile());
        if (data.getPoint()!=null)
            myPointsET.setText(String.valueOf(data.getPoint()));

    }

    private void showProgressBar(boolean isLoading) {
        if (isLoading) {
            saveBtn.startMorphAnimation();
            saveBtn.setBackground(getContext().getDrawable(R.drawable.rounded_red_bg));
        } else {
            saveBtn.stopAnimation();
            saveBtn.setBackground(getContext().getDrawable(R.drawable.login_button_shape));
        }
    }


}