package com.hristiyantodorov.weatherapp.views.weather_details;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.models.WeatherDetailsData;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.WeatherDetailsAdapter;
import com.hristiyantodorov.weatherapp.utils.ItemOffsetsGenerator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastFragment extends Fragment {

    @BindView(R.id.recycler_view_forecast)
    RecyclerView recyclerViewForecast;

    public ForecastFragment() {
    }

    public static ForecastFragment newInstance() {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_details, container, false);

        ButterKnife.bind(this, view);

        recyclerViewForecast.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewForecast.addItemDecoration(new ItemOffsetsGenerator(20, 1));
        recyclerViewForecast.setAdapter(new WeatherDetailsAdapter(feedItems(), getContext()));
        return view;
    }

    private List<WeatherDetailsData> feedItems() {
        // TODO: 1/24/2019 Add test data to arrays.
        String[] conditions = {};
        String[] temperatures = {};
        Drawable[] images = {};
        List<WeatherDetailsData> demoItems = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < conditions.length; j++) {
                WeatherDetailsData demoItem = new WeatherDetailsData(conditions[j], temperatures[j], images[j]);
                demoItems.add(demoItem);
            }
        }
        return demoItems;
    }
}
