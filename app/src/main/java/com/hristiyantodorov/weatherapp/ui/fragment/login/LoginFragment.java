package com.hristiyantodorov.weatherapp.ui.fragment.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.model.user.UserDao;
import com.hristiyantodorov.weatherapp.model.user.UserDbModel;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;
import com.hristiyantodorov.weatherapp.presenter.login.LoginContracts;
import com.hristiyantodorov.weatherapp.ui.activity.main.MainActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.hristiyantodorov.weatherapp.util.AppExecutorUtil;
import com.ramotion.circlemenu.CircleMenuView;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment implements LoginContracts.View {

    @BindView(R.id.progress_bar)
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
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

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
            }

            @Override
            public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonClickAnimationEnd| index: " + index);
                Toast.makeText(getContext(), getString(R.string.login_screen_toast_login_successful_text), Toast.LENGTH_LONG).show();
                circleMenuLogin.setVisibility(View.GONE);
            }
        });

        return view;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_login;
    }

    @OnClick(R.id.btn_sign_in)
    public void onSignInButtonClick() {
        //Test implementation - adding and entry to the database "users"
        UserDbModel user = new UserDbModel();
        String email = edtEmail.getText().toString();
        user.setEmail(email);
        UserDao userDao = PersistenceDatabase
                .getAppDatabase(App.getInstance().getApplicationContext()).userDao();
        AppExecutorUtil.getInstance().execute(() -> userDao.insertUser(user));
        // TODO: 1/18/2019  Login from presenter mPresenter.loginUser(userName, password);
        startActivity(new Intent(getContext(), MainActivity.class));
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
    public void showEmptyScreen(boolean isShowing) {
        // TODO: 3/18/2019 ADD IMPLEMENTATION
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }

}