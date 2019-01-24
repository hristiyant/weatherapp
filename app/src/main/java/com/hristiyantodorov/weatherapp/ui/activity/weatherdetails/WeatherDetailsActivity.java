package com.hristiyantodorov.weatherapp.ui.activity.weatherdetails;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.ViewPagerAdapter;
import com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails.ForecastFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.image_view_thumbnail)
    ImageView imageViewThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(view -> {
            // TODO: 1/24/2019 Add implementation.
        });

        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        // TODO: 1/24/2019 Change tab names.
        mViewPagerAdapter.addFragment(ForecastFragment.newInstance(), "Tab 1");
        mViewPagerAdapter.addFragment(ForecastFragment.newInstance(), "Tab 2");
        mViewPagerAdapter.addFragment(ForecastFragment.newInstance(), "Tab 3");
        viewPager.setAdapter(mViewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        Picasso.get().load(R.drawable.ic_avatar).fit().into(imageViewThumbnail);
    }

}
