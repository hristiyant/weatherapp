package com.hristiyantodorov.weatherapp.ui.fragment.login;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.model.AppDatabase;
import com.hristiyantodorov.weatherapp.model.User;
import com.hristiyantodorov.weatherapp.presenter.login.LoginContracts;
import com.hristiyantodorov.weatherapp.ui.activity.main.MainActivity;
import com.ramotion.circlemenu.CircleMenuView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment implements LoginContracts.View {
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.edt_email)
    EditText edtEmail;

    @BindView(R.id.edt_password)
    EditText edtPassword;

    @BindView(R.id.background)
    ConstraintLayout constraintLayout;

    @BindView(R.id.circle_menu_login)
    CircleMenuView circleMenuLogin;

    private LoginContracts.Presenter loginPresenter;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);

        circleMenuLogin.setEventListener(new CircleMenuView.EventListener() {
            @Override
            public void onMenuOpenAnimationStart(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuOpenAnimationStart");
            }

            @Override
            public void onMenuOpenAnimationEnd(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuOpenAnimationEnd");
            }

            @Override
            public void onMenuCloseAnimationStart(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuCloseAnimationStart");
            }

            @Override
            public void onMenuCloseAnimationEnd(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuCloseAnimationEnd");
            }

            @Override
            public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonClickAnimationStart| index: " + index);
                Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonClickAnimationEnd| index: " + index);
                Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_LONG).show();
                circleMenuLogin.setVisibility(View.GONE);
            }

        });

        return view;
    }

    @OnClick(R.id.btn_sign_in)
    public void onSignInButtonClick() {
        User loggedInUser = new User();
        loggedInUser.setEmail(edtEmail.getText().toString());
        AppDatabase db = Room.databaseBuilder(App.getInstance().getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();
        db.userDao().insert(loggedInUser);

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