package com.hristiyantodorov.weatherapp.views.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hristiyantodorov.weatherapp.ApiTestActivity;
import com.hristiyantodorov.weatherapp.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainFragment extends Fragment {

   /* @BindView(R.id.image_btn_pick_from_location)
    ImageButton btnPickFromLocation;

    @BindView(R.id.image_btn_pick_from_list)
    ImageButton btnPickFromList;*/

    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.image_btn_pick_from_location)
    public void onButtonPickFromLocationClick() {
        Intent intent = new Intent(getActivity(), ApiTestActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.image_btn_pick_from_list)
    public void onButtonPickFromListClick() {

    }

}
