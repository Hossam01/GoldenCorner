package com.golden.goldencorner.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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


public class EditPasswordFragment extends Fragment {


    @BindView(R.id.newPasswordT)
    EditText newPasswordT;
    @BindView(R.id.saveBtn)
    CircularProgressButton saveBtn;
    private ProfileViewModel mViewModel;

    public EditPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).titleToolbarIv.setText(getString(R.string.password));
        return inflater.inflate(R.layout.fragment_edit_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });

    }

    private void editProfile() {
        String password = newPasswordT.getText().toString();

        if (password.isEmpty()) {
            newPasswordT.setError(getString(R.string.this_field_is_required));
            return;
        }
        showProgressBar(true);
        mViewModel.editPassword(password).subscribe(new Consumer<ProfileResponse>() {
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
            saveBtn.startMorphAnimation();
            saveBtn.setBackground(getContext().getDrawable(R.drawable.rounded_red_bg));
        } else {
            saveBtn.stopAnimation();
            saveBtn.setBackground(getContext().getDrawable(R.drawable.login_button_shape));
        }
    }

}