package com.hristiyantodorov.weatherapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.hristiyantodorov.weatherapp.R;

public class OtherView extends LoadingView{

    public OtherView(Context context) {
        super(context);
        initialize();
    }

    public OtherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public OtherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize(){
        View root = inflate(getContext(), R.layout.other_view,getContentFrame());
    }
}
