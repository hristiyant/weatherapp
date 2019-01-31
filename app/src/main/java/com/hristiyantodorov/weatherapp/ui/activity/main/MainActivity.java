package com.hristiyantodorov.weatherapp.ui.activity.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.ui.fragment.main.MainFragment;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    /*private String[] optionsNames = {"Facebook", "Google", "Twitter",};
    List<Integer> icons = new ArrayList<>(Arrays.asList(R.drawable.ic_facebook,
            R.drawable.ic_google,
            R.drawable.ic_google));

    List<Integer> colors = new ArrayList<>(Arrays.asList(Color.parseColor("#3b5998"),
            Color.parseColor("#ea4335"),
            Color.parseColor("#1da1f2")));*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        /*circleMenuLogin.setMainMenu(Color.parseColor("#BBBBBB"),
                R.drawable.ic_home_black, R.drawable.ic_add_black)
                .addSubMenu(Color.parseColor("#3b5998"),
                        R.drawable.ic_facebook)
                .addSubMenu(Color.parseColor("#ea4335"),
                        R.drawable.ic_google)
                .addSubMenu(Color.parseColor("#1da1f2"),
                        R.drawable.ic_twitter).setOnMenuSelectedListener(i -> Toast.makeText(this, "You selected " + optionsNames[i], Toast.LENGTH_SHORT));*/



        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, MainFragment.newInstance());
        fragmentTransaction.commit();
    }
}