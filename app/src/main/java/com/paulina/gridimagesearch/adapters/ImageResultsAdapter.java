package com.paulina.gridimagesearch.adapters;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.paulina.gridimagesearch.R;
import com.paulina.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

/**
 * Created by pramos on 2/14/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    private final Random mRandom = new Random();
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        ImageResult imageInfo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.dhivImage = (DynamicHeightImageView) convertView.findViewById(R.id.dhivImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        // clear out image
        viewHolder.dhivImage.setImageResource(0);

        // populate title and remote download image url
//        tvTitle.setText(imageInfo.title);
//        try {
//            tvTitle.setText(Html.fromHtml(imageInfo.title));
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }

//        viewHolder.dhivImage.setHeightRatio(1.0);
        double positionHeight = getPositionRatio(position);
        viewHolder.dhivImage.setHeightRatio(positionHeight);

        Picasso.with(getContext())
                .load(imageInfo.fullUrl)
                .into(viewHolder.dhivImage);

        return convertView;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the column's height
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d("info", "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }
    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0;
    }

    static class ViewHolder {
        DynamicHeightImageView dhivImage;
    }
}
