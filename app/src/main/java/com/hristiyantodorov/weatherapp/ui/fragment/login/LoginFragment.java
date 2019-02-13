package com.hristiyantodorov.weatherapp.ui.fragment.login;

import android.arch.persistence.room.Room;
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
import com.hristiyantodorov.weatherapp.model.AppDatabase;
import com.hristiyantodorov.weatherapp.persistence.location.LocationDao;
import com.hristiyantodorov.weatherapp.persistence.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.persistence.location.LocationService;
import com.hristiyantodorov.weatherapp.presenter.login.LoginContracts;
import com.hristiyantodorov.weatherapp.ui.activity.main.MainActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.hristiyantodorov.weatherapp.util.AsyncResponse;
import com.ramotion.circlemenu.CircleMenuView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment
        implements LoginContracts.View, AsyncResponse<List<LocationDbModel>> {

    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.container)
    ConstraintLayout constraintLayout;
    @BindView(R.id.circle_menu_login)
    CircleMenuView circleMenuLogin;

    private LoginContracts.Presenter loginPresenter;
    private List<LocationDbModel> results;

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
        // TODO: 2/13/2019 Practice implementation
        AppDatabase testDB =
                Room.inMemoryDatabaseBuilder(App.getInstance().getApplicationContext(), AppDatabase.class).build();
        LocationDbModel testLoc =
                new LocationDbModel("testLoc", 1.1, 1.2, "url");
        LocationDao dao = testDB.locationDao();
        LocationService service = new LocationService(dao);
        service.insertLocations(testLoc);

        // TODO: 1/18/2019  Login from presenter mPresenter.loginUser(userName, password);
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    @Override
    public void setPresenter(LoginContracts.Presenter presenter) {
        loginPresenter = presenter;
    }

    @Override
    public void showLoader(boolean isShowing) {
        progressBar.setVisibility(isShowing ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showError(Throwable e) {
        if (isAdded()) {
            showErrorDialog(getContext(), e.getMessage());
        }
    }

    @Override
    public void onSuccess(List<LocationDbModel> output) {
        if (isAdded()) {
            results.addAll(output);
        }
    }

    @Override
    public void onFailure(Exception e) {
        if (isAdded()) {
            showErrorDialog(getContext(), e.getMessage());
        }
    }
}