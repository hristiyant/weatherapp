package com.hristiyantodorov.weatherapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.hristiyantodorov.weatherapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingView extends RelativeLayout {
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.content_frame)
    FrameLayout frameLayout;

    public LoadingView(Context context) {
        super(context);
        initialize();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        View root = inflate(getContext(), R.layout.loading_view, this);
        ButterKnife.bind(this);
    }

    public void setLoading(boolean loading) {
        progressBar.setVisibility(loading ? VISIBLE : GONE);
        progressBar.setRotation(180);
    }

    protected FrameLayout getContentFrame() {
        return frameLayout;
    }
}
