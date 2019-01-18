package com.hristiyantodorov.weatherapp.views.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hristiyantodorov.weatherapp.views.main.MainActivity;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.utils.TimeCalculatorUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginFragment extends Fragment implements LoginContracts.View {

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.edt_username)
    EditText edtUserName;

    @BindView(R.id.edt_password)
    EditText edtPassword;

    @BindView(R.id.background)
    ConstraintLayout constraintLayout;

    private LoginContracts.Presenter loginPresenter;
    private TimeCalculatorUtil timeCalculatorUtil;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        timeCalculatorUtil = new TimeCalculatorUtil();

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_sign_in)
    public void onSignInButtonClick() {

        // TODO: 1/18/2019  Login from presenter mPresenter.loginUser(userName, password);
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPresenter(LoginContracts.Presenter presenter) {
        loginPresenter = presenter;
    }

    @Override
    public void showLoader(boolean isShowing) {
        progressBar.setVisibility(isShowing ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
