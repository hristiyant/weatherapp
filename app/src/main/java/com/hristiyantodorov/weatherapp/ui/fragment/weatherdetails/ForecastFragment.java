package com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapter.weatherdetails.WeatherDetailsAdapter;
import com.hristiyantodorov.weatherapp.model.WeatherDetailsData;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ForecastFragment extends BaseFragment {

    @BindView(R.id.recycler_view_forecast)
    RecyclerView recyclerViewForecast;

    public static ForecastFragment newInstance() {
        return new ForecastFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        recyclerViewForecast.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewForecast.setAdapter(new WeatherDetailsAdapter(feedItems(), getContext()));
        recyclerViewForecast.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_forecast;
    }

    /**
     * This method is used to feed dummy data to the adapter for testing purposes.
     *
     * @return List of WeatherDetailsData test items
     */
    private List<WeatherDetailsData> feedItems() {
        String[] conditions = {"THU\n sunny", "FRI\n foggy", "SAT\n cloudy", "SUN\n rainy"};
        String[] temperatures = {"22\u2103", "5\u2103", "18\u2103", "33\u2109"};
        int[] images = {R.drawable.ic_weather_cloudy,
                R.drawable.login_image,
                R.drawable.ic_weather_cloudy,
                R.drawable.login_image};

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