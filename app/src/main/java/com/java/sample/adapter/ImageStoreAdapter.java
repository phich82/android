package com.java.sample.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.sample.DatabaseActivity;
import com.java.sample.R;
import com.java.sample.dto.ImageStorage;

import java.util.List;

public class ImageStoreAdapter extends BaseAdapter {

    private DatabaseActivity context;
    private final int layout;
    private List<ImageStorage> imageStorages;

    public ImageStoreAdapter(DatabaseActivity context, int layout, List<ImageStorage> imageStorages) {
        this.context = context;
        this.layout = layout;
        this.imageStorages = imageStorages;
    }

    @Override
    public int getCount() {
        return imageStorages.size();
    }

    @Override
    public Object getItem(int i) {
        if (imageStorages.isEmpty()) {
            return null;
        }
        return imageStorages.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtName = view.findViewById(R.id.txtNameImageStorage);
            holder.txtDescription = view.findViewById(R.id.txtDescriptionImageStorage);
            holder.imgPreview = view.findViewById(R.id.imgPreviewImageStorage);
            holder.imgDelete = view.findViewById(R.id.imgDeleteImageStorage);
            //holder.imgEdit = view.findViewById(R.id.imageViewEdit);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ImageStorage imageStorage = imageStorages.get(i);
        if (imageStorage != null) {
            byte[] imageByte = imageStorage.getImage();
            if (imageByte == null) {
                holder.imgPreview.setImageResource(R.drawable.gallery);
            } else {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                holder.imgPreview.setImageBitmap(bitmap);
            }
            holder.txtName.setText(imageStorage.getName());
            holder.txtDescription.setText(imageStorage.getDescription());

            // Handle click events
            //holder.imgEdit.setOnClickListener(v -> context.showDialogEditImageStorage(imageStorage.getId()));
            holder.imgDelete.setOnClickListener(v -> context.showDialogDeleteImageStorage(imageStorage.getId()));

        }

        return view;
    }

    private class ViewHolder {
        TextView txtName;
        TextView txtDescription;
        ImageView imgPreview;
        ImageView imgDelete, imgEdit;
    }

}
