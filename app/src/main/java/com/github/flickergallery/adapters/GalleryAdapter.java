package com.github.flickergallery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.flickergallery.R;
import com.github.flickergallery.models.CommonPickModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    List<CommonPickModel> commonPickModel;
    Context context;
    View view;

    public GalleryAdapter(List<CommonPickModel> commonPickModel, Context context) {
        this.commonPickModel = commonPickModel;
        this.context = context;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_adapter_view,parent,false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        final CommonPickModel commonPickModelList = commonPickModel.get(position);
        Glide.with(context).load(commonPickModelList.image).into(holder.gallery_image);

    }

    @Override
    public int getItemCount() {
        return commonPickModel.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView gallery_image;
        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            gallery_image=itemView.findViewById(R.id.gallery_image);
        }
    }
}
