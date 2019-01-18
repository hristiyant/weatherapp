package com.hristiyantodorov.weatherapp.views.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.hristiyantodorov.weatherapp.R;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;


public class MainFragment extends Fragment {

    @BindView(R.id.image_btn_pick_from_location)
    ImageButton btnPickFromLocation;

    @BindView(R.id.image_btn_pick_from_list)
    ImageButton btnPickFromList;

    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }

    @OnClick(R.id.image_btn_pick_from_location)
    public void OnLocationClick() {
        Log.d(TAG, "Pick location");
    }

    @OnClick(R.id.image_btn_pick_from_list)
    public void OnListClick() {
        Log.d(TAG, "Pick from list");
    }

}
