package com.hristiyantodorov.weatherapp.ui.fragment.locations;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.presenter.locations.AddLocationToDbContracts;
import com.hristiyantodorov.weatherapp.presenter.locations.AddLocationToDbPresenter;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class AddLocationToDbFragment extends BaseFragment
        implements AddLocationToDbContracts.View {

    private static final String TAG = "ALTDBFragment";

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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
