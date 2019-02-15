package com.hristiyantodorov.weatherapp.ui.fragment.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
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
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;
import com.ramotion.circlemenu.CircleMenuView;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.constraint.Constraints.TAG;

public class LoginFragment extends BaseFragment implements LoginContracts.View {
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
        return new LoginFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_login;
    }

    @OnClick(R.id.btn_sign_in)
    public void onSignInButtonClick() {
        // TODO: 2/13/2019 Test implementation
        UserDbModel user = new UserDbModel();
        String email = edtEmail.getText().toString();
        user.setEmail(email);
        UserDao userDao = PersistenceDatabase
                .getAppDatabase(App.getInstance().getApplicationContext()).userDao();
        AppExecutorUtil.getInstance().execute(() -> userDao.insertUser(user));

        // TODO: 2/13/2019 SharedPrefUtil class test
        SharedPrefUtil.write(SharedPrefUtil.LOGGED_USER, edtEmail.getText().toString());
        String result = SharedPrefUtil.read(SharedPrefUtil.LOGGED_USER,null);
        Log.d(TAG, "logged_user: " + result);

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
    public void showError(Throwable e) {
        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}