package com.paulina.gridimagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pramos on 2/14/15.
 */
public class ImageResult implements Serializable { // makes ImageResult object capable of being encoded into the intent extra system
    private static final long serialVersionUID = 8724804852739320502L;
    public String fullUrl;
    public String thumbUrl;
    public String title;
    public int width;
    public int height;

    // parses individual item
    // new ImageResult(raw item json)
    public ImageResult(JSONObject json) {
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // parses array list of items
    // take an array of json images and return arraylist of image results
    // ImageResult.fromJSONArray([..., ...])
    public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();
        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new ImageResult(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
