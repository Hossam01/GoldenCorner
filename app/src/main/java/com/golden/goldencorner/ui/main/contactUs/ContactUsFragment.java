package com.golden.goldencorner.ui.main.contactUs;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.data.model.SimpleResponse;
import com.golden.goldencorner.ui.main.MainActivity;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUsFragment extends Fragment {

    @BindView(R.id.nameET)
    EditText nameET;
    @BindView(R.id.emailET)
    EditText emailET;
    @BindView(R.id.messageET)
    EditText messageET;
    @BindView(R.id.sendBtn)
    CircularProgressButton sendBtn;
    private ContactUsViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).titleToolbarIv.setText(getString(R.string.contact_us));
        return inflater.inflate(R.layout.contact_us_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this)
                .get(ContactUsViewModel.class);
        subscribeObserver();
    }

    private void subscribeObserver() {
        mViewModel.getContactSlider().observe(getViewLifecycleOwner(),
                new Observer<Resource<SimpleResponse>>() {
                    @Override
                    public void onChanged(Resource<SimpleResponse> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar(false);
                                    SimpleModel record = resource.getData().getData().get(0);
                                    ((MainActivity) getActivity()).showToast(record.getMessage());
                                    break;
                                case ERROR:
                                    showProgressBar(false);
                                    ((MainActivity) getActivity()).showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    showProgressBar(true);
                                    break;
                            }
                        }
                    }
                });
    }

    private void showProgressBar(boolean isLoading) {
        if (isLoading) {
            sendBtn.stopProgressAnimation();
        } else {
            sendBtn.stopAnimation();
        }
    }

    @OnClick(R.id.sendBtn)
    public void onViewClicked() {
        String name = nameET.getText().toString();
        if (TextUtils.isEmpty(name)) {
            nameET.setError(getActivity().getString(R.string.this_field_is_required));
            return;
        }
        String email = emailET.getText().toString();
        if (TextUtils.isEmpty(email)) {
            nameET.setError(getActivity().getString(R.string.this_field_is_required));
            return;
        }
        String message = messageET.getText().toString();
        if (TextUtils.isEmpty(message)) {
            nameET.setError(getActivity().getString(R.string.this_field_is_required));
            return;
        }
        mViewModel.invokeContactApi(name, email, message);
    }
}