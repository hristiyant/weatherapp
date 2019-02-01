package com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapters.weatherdetails.WeatherDetailsAdapter;
import com.hristiyantodorov.weatherapp.models.WeatherDetailsData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastFragment extends Fragment {

    @BindView(R.id.recycler_view_forecast)
    RecyclerView recyclerViewForecast;

    public static ForecastFragment newInstance() {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        ButterKnife.bind(this, view);

        recyclerViewForecast.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewForecast.setAdapter(new WeatherDetailsAdapter(feedItems(), requireContext()));
        recyclerViewForecast.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    /**
     * This method is used to feed dummy data to the adapter for testing purposes.
     *
     * @return List of WeatherDetailsData test items
     */
    
    private List<WeatherDetailsData> feedItems() {
        String[] conditions = {"sunny", "foggy", "cloudy", "rainy"};
        String[] temperatures = {"22\u2103", "5\u2103", "18\u2103", "33\u2103"};
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