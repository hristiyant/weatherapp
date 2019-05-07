package com.hristiyantodorov.weatherapp.ui.fragment.addlocationtodb;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.presenter.addlocationtodb.AddLocationToDbContracts;
import com.hristiyantodorov.weatherapp.presenter.addlocationtodb.AddLocationToDbPresenter;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.OnClick;

public class AddLocationToDbFragment extends BaseFragment
        implements AddLocationToDbContracts.View {

    private static final String TAG = "ALTDBFragment";

    @BindView(R.id.til_location_name)
    TextInputLayout tilLocationName;
    @BindView(R.id.edt_location_name)
    EditText edtLocationName;
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

        tilLocationName.setError("Invalid location name");
        RxTextView.textChanges(edtLocationName)
                .map(charSequence -> (charSequence.length() == 0) || charSequence.toString().matches("^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}"))
                .subscribe(aBoolean -> tilLocationName.setErrorEnabled(!aBoolean));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_add_location_to_db;
    }

    @OnClick(R.id.btn_save_location)
    public void onSaveLocationClick() {
        Toast.makeText(App.getInstance().getApplicationContext(),
                "sadasd", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Location Saved");
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

    }

    @Override
    public void showEmptyScreen(boolean isShowing) {

    }

    @Override
    public void showError(Throwable e) {

    }
}
