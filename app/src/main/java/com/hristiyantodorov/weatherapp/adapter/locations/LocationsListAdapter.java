package com.hristiyantodorov.weatherapp.adapter.locations;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.persistence.location.LocationDbModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationsListAdapter extends RecyclerView.Adapter<LocationsListAdapter.LocationsListItemHolder> {

    private List<LocationDbModel> itemsList;
    private Context context;

    public LocationsListAdapter(List<LocationDbModel> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @NonNull
    @Override
    public LocationsListAdapter.LocationsListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationsListAdapter.LocationsListItemHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_locations_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationsListAdapter.LocationsListItemHolder holder, int position) {
        holder.bind(itemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    static class LocationsListItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_city_image)
        ImageView imgCityImage;

        @BindView(R.id.txt_city_name)
        TextView txtCityName;

        LocationsListItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(LocationDbModel item) {
            String name = item.getName();
            txtCityName.setText(name);
            Picasso.get().load(item.getImageUrl()).into(imgCityImage);
        }
    }
}