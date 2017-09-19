package com.popularmovie.sasin.popularmovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Moondance on 9/19/2017 AD.
 */

public class JSONMovieConverter {

    String JSONData;
    JSONObject JSONObject;

    public JSONMovieConverter(String JSONString)
    {
        JSONData = JSONString;

        try {

            JSONObject  = new JSONObject(JSONData);

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }



    public String[] GetMovieURLs() throws JSONException {

        JSONArray resultArray = JSONObject.getJSONArray("results");

        String[] DataSet = new String[resultArray.length()];

        for (int i = 0;i < resultArray.length();i++)
        {
            JSONObject object = resultArray.getJSONObject(i);

            DataSet[i] = object.getString("poster_path").toString();
        }

        return DataSet;
    }

    public String[] GetNames() throws JSONException {

        JSONArray resultArray = JSONObject.getJSONArray("results");

        String[] DataSet = new String[resultArray.length()];

        for (int i = 0;i < resultArray.length();i++)
        {
            JSONObject object = resultArray.getJSONObject(i);

            DataSet[i] = object.getString("original_title").toString();
        }

        return DataSet;
    }


}
