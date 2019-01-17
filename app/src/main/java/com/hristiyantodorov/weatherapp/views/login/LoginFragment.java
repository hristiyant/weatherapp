package com.hristiyantodorov.weatherapp.views.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.utils.TimeCalculator;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginFragment extends Fragment implements LoginContracts.View {

    TimeCalculator tc;

    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;

    @BindView(R.id.et_username)
    EditText mUserNameEditText;

    @BindView(R.id.et_password)
    EditText mPasswordEditText;

    //more binding

    private LoginContracts.Presenter mLoginPresenter;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        tc = new TimeCalculator();
        ButterKnife.bind(this, view);
        Toast.makeText(getContext(), tc.getCurrentTime(), Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(), tc.getCurrentHours(), Toast.LENGTH_LONG).show();

        return view;
    }

    @OnClick(R.id.btn_sign_in)
    public void onSignInButtonClick() {
        String userName = mUserNameEditText.getText().toString();
        Toast.makeText(getContext(), userName, Toast.LENGTH_LONG).show();

        String password = mPasswordEditText.getText().toString();
        Toast.makeText(getContext(), password, Toast.LENGTH_LONG).show();

        //Login from presenter mPresenter.loginUser(userName, password);
    }

    @Override
    public void setPresenter(LoginContracts.Presenter presenter) {
        mLoginPresenter = presenter;
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        boolean isTrue = true;
        mProgressBar.setVisibility(isTrue ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showLoader(boolean isShowing) {
        mProgressBar.setVisibility(isShowing ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
