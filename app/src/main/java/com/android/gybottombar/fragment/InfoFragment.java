package com.android.gybottombar.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.gybottombar.MainActivity;
import com.android.gybottombar.R;
import com.android.gybottombar.utils.UserManager;

import java.util.Objects;

public class InfoFragment extends Fragment implements View.OnClickListener {
    private TextView btn_login;

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_fragment, container, false);
        btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        if (!UserManager.isIsLogin()) {
            ((MainActivity) Objects.requireNonNull(getActivity())).goLogin();
        }
    }
}
