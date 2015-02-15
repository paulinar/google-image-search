package com.paulina.gridimagesearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paulina.gridimagesearch.R;
import com.paulina.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pramos on 2/14/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageInfo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);

        }
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        // clear out image
        ivImage.setImageResource(0);

        // populate title and remote download image url
        tvTitle.setText(imageInfo.title);
//        try {
//            tvTitle.setText(Html.fromHtml(imageInfo.title));
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }

        Picasso.with(getContext())
                .load(imageInfo.thumbUrl)
                .into(ivImage);

        return convertView;
    }
}
