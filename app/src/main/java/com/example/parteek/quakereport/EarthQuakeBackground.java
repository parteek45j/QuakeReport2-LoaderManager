package com.example.parteek.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Parteek on 10/12/2017.
 */

public class EarthQuakeBackground extends AsyncTaskLoader<ArrayList<Earthquake>> {
    String mUrl;
    public EarthQuakeBackground(Context context,String url) {
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        ArrayList<Earthquake> arrayList=QueryUtils2.getList(mUrl);
        return arrayList;
    }
}
