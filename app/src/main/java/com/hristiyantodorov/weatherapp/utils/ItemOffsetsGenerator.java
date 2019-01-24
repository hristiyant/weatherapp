package com.hristiyantodorov.weatherapp.utils;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * @author Hristiyan Todorov
 * Generates offsets which are applied to the respective RecyclerView
 */
public class ItemOffsetsGenerator extends RecyclerView.ItemDecoration {
    int space;
    int spanCount;

    public ItemOffsetsGenerator(int space, int spanCount) {
        this.space = space;
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildLayoutPosition(view) <= spanCount) {
            outRect.top = space;
        }
        outRect.right = space;
        outRect.left = space;
        outRect.bottom = space;
    }
}