package com.android.gybottombar.fragment;

import androidx.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.gybottombar.MainActivity;
import com.android.gybottombar.R;

import java.util.Objects;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private LoginViewModel mViewModel;
    private TextView btn_login;
    private ImageView iv_close;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        btn_login = view.findViewById(R.id.btn_login);
        iv_close = view.findViewById(R.id.iv_close);
        btn_login.setOnClickListener(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String isShow = bundle.getString("isShow");
            if ("1".equals(isShow)) {
                iv_close.setVisibility(View.GONE);
            }
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        ((MainActivity) Objects.requireNonNull(getActivity())).changeFragment();
    }
}
