package com.hristiyantodorov.weatherapp.ui.fragment.addlocationtodb;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.presenter.addlocationtodb.AddLocationToDbContracts;
import com.hristiyantodorov.weatherapp.presenter.addlocationtodb.AddLocationToDbPresenter;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.hristiyantodorov.weatherapp.util.RxUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class AddLocationToDbFragment extends BaseFragment
        implements AddLocationToDbContracts.View {

    private static final String TAG = "ALTDBFragment";

    @BindView(R.id.input_layout_location_name)
    TextInputLayout inputLayoutLocationName;
    @BindView(R.id.edt_location_name)
    TextInputEditText edtLocationName;
    @BindView(R.id.input_layout_location_latitude)
    TextInputLayout inputLayoutLocationLatitude;
    @BindView(R.id.edt_location_latitude)
    TextInputEditText edtLocationLatitude;
    @BindView(R.id.input_layout_location_longitude)
    TextInputLayout inputLayoutLocationLongitude;
    @BindView(R.id.edt_location_longitude)
    TextInputEditText edtLocationLongitude;
    @BindView(R.id.btn_save_location)
    Button btnSaveLocation;

    private AddLocationToDbContracts.Presenter presenter;
    private CompositeDisposable compositeDisposable;

    public static AddLocationToDbFragment newInstance() {
        AddLocationToDbFragment fragment = new AddLocationToDbFragment();
        new AddLocationToDbPresenter(fragment);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        compositeDisposable = new CompositeDisposable();

        Observable<Boolean> nameObservable = RxTextView.afterTextChangeEvents(edtLocationName)
                .compose(RxUtils.applyInitialValueObservable())
                .map(charSequence -> presenter.isInputValidName(charSequence.view().getText()));

        compositeDisposable.add(nameObservable
                .subscribe(isValid -> {
                    if (isValid) {
                        inputLayoutLocationName.setError(null);
                    } else {
                        inputLayoutLocationName.setError(App.getRes().getString(R.string.fragment_add_location_to_db_invalid_name_error));
                        inputLayoutLocationName.setErrorEnabled(!isValid);
                    }
                }));

        Observable<Boolean> latitudeObservable = RxTextView.afterTextChangeEvents(edtLocationLatitude)
                .compose(RxUtils.applyInitialValueObservable())
                .map(charSequence -> presenter.isInputValidDouble(charSequence.view().getText()));

        compositeDisposable.add(latitudeObservable
                .subscribe(isValid -> {
                    if (isValid) {
                        inputLayoutLocationLatitude.setError(null);
                    } else {
                        inputLayoutLocationLatitude.setError(App.getRes().getString(R.string.fragment_add_location_to_db_invalid_latitude_error));
                        inputLayoutLocationLatitude.setErrorEnabled(!isValid);
                    }
                }));

        Observable<Boolean> longitudeObservable = RxTextView.afterTextChangeEvents(edtLocationLongitude)
                .compose(RxUtils.applyInitialValueObservable())
                .map(charSequence -> presenter.isInputValidDouble(charSequence.view().getText()));

        compositeDisposable.add(longitudeObservable
                .subscribe(isValid -> {
                    if (isValid) {
                        inputLayoutLocationLongitude.setError(null);
                    } else {
                        inputLayoutLocationLongitude.setError(App.getRes().getString(R.string.fragment_add_location_to_db_invalid_longitude_error));
                        inputLayoutLocationLongitude.setErrorEnabled(!isValid);
                    }
                }));

        compositeDisposable.add(Observable.combineLatest(
                nameObservable,
                latitudeObservable,
                longitudeObservable,
                (isNameValid, isLatitudeValid, isLongitudeValid) -> isNameValid && isLatitudeValid && isLongitudeValid
        )
                .distinctUntilChanged()
                .subscribe(isValid -> btnSaveLocation.setEnabled(isValid)));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_add_location_to_db;
    }

    @OnClick(R.id.btn_save_location)
    public void onSaveLocationClick() {
        presenter.saveLocationToDb(Objects.requireNonNull(edtLocationName.getText()).toString(),
                Double.parseDouble(Objects.requireNonNull(edtLocationLatitude.getText()).toString()),
                Double.parseDouble(Objects.requireNonNull(edtLocationLongitude.getText()).toString()));
    }

    @Override
    public void setPresenter(AddLocationToDbContracts.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoader(boolean isShowing) {
        //Not used
    }

    @Override
    public void showEmptyScreen(boolean isShowing) {
        //Not used
    }

    @Override
    public void showError(Throwable e) {
        showErrorDialog(getContext(), e);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
    }
}
