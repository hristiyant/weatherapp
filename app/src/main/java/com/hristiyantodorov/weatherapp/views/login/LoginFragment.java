package com.hristiyantodorov.weatherapp.views.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.utils.TimeCalculatorUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.constraint.Constraints.TAG;


public class LoginFragment extends Fragment implements LoginContracts.View {

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.edt_username)
    EditText userNameEditText;

    @BindView(R.id.edt_password)
    EditText passwordEditText;

    private LoginContracts.Presenter loginPresenter;
    private TimeCalculatorUtil timeCalculator;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        timeCalculator = new TimeCalculatorUtil();
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_sign_in)
    public void onSignInButtonClick() {

        // TODO: 1/18/2019  Login from presenter mPresenter.loginUser(userName, password);

        Log.d(TAG, timeCalculator.getGreetingBasedOnCurrentTime());
    }

    @Override
    public void setPresenter(LoginContracts.Presenter presenter) {
        loginPresenter = presenter;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        boolean isTrue = true;
        progressBar.setVisibility(isTrue ? View.GONE : View.VISIBLE);
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
