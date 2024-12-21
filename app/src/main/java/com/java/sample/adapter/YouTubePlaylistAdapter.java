package com.java.sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.sample.DatabaseActivity;
import com.java.sample.R;
import com.java.sample.dto.YouTubeItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class YouTubePlaylistAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<YouTubeItem> youTubeVideoList;

    public YouTubePlaylistAdapter(Context context, int layout, List<YouTubeItem> youTubeVideoList) {
        this.context = context;
        this.layout = layout;
        this.youTubeVideoList = youTubeVideoList;
    }

    @Override
    public int getCount() {
        return youTubeVideoList.size();
    }

    @Override
    public Object getItem(int i) {
        if (youTubeVideoList.isEmpty()) {
            return null;
        }
        return youTubeVideoList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtTitleVideoYoutube = view.findViewById(R.id.txtTitleYoutubbe);
            holder.imgThumbnailVideoYoutube = view.findViewById(R.id.imgThumbnailYoutube);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        YouTubeItem youTubeItem = youTubeVideoList.get(i);
        String title = youTubeItem.getSnippet().getTitle();
        String url = youTubeItem.getSnippet().getThumbnails().getDefault().getUrl();
        String videoId = youTubeItem.getSnippet().getResourceId().getVideoId();
        holder.txtTitleVideoYoutube.setText(title);
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.image_error)
                .into(holder.imgThumbnailVideoYoutube);
        return view;
    }

    private class ViewHolder {
        TextView txtTitleVideoYoutube;
        ImageView imgThumbnailVideoYoutube;
    }

}
