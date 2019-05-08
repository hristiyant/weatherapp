package com.hristiyantodorov.weatherapp.ui.fragment.addlocationtodb;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.presenter.addlocationtodb.AddLocationToDbContracts;
import com.hristiyantodorov.weatherapp.presenter.addlocationtodb.AddLocationToDbPresenter;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

public class AddLocationToDbFragment extends BaseFragment
        implements AddLocationToDbContracts.View {

    private static final String TAG = "ALTDBFragment";

    @BindView(R.id.input_layout_location_name)
    TextInputLayout inputLayoutLocationName;
    @BindView(R.id.edt_location_name)
    TextInputEditText edtLocationName;
    @BindView(R.id.edt_location_latitude)
    EditText edtLocationLatitude;
    @BindView(R.id.edt_location_longitude)
    EditText edtLocationLongitude;

    private AddLocationToDbContracts.Presenter presenter;

    public static AddLocationToDbFragment newInstance() {
        AddLocationToDbFragment fragment = new AddLocationToDbFragment();
        new AddLocationToDbPresenter(fragment);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputLayoutLocationName.setError("Invalid location name");
        inputLayoutLocationName.setErrorEnabled(true);

        Observable<Boolean> locationNameObservable = RxTextView.textChanges(edtLocationName)
                .map(charSequence -> presenter.validateInputString(charSequence))
                .distinctUntilChanged();

        locationNameObservable.subscribe(aBoolean -> inputLayoutLocationName.setErrorEnabled(!aBoolean));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_add_location_to_db;
    }

    @OnClick(R.id.btn_save_location)
    public void onSaveLocationClick() {
        presenter.saveLocationToDb(edtLocationName.getText().toString(),
                Double.parseDouble(edtLocationLatitude.getText().toString()),
                Double.parseDouble(edtLocationLongitude.getText().toString()));
    }

    @Override
    public void setPresenter(AddLocationToDbContracts.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoader(boolean isShowing) {
        // TODO: 8.5.2019 Not implemented yet.
    }

    @Override
    public void showEmptyScreen(boolean isShowing) {
        //Not used
    }

    @Override
    public void showError(Throwable e) {
        showErrorDialog(getContext(), e);
    }
}
