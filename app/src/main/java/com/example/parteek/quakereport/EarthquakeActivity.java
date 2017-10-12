/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.parteek.quakereport;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {
    ProgressDialog progressDialog;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    ArrayList<Earthquake> earthquakes;
    CustomAdapter adapter;
    ListView earthquakeListView;
    LoaderManager loaderManager;
    int loaderId=1;
    public static final String SAMPLE_JSON_RESPONSE1="http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-01-31&minmagnitude=6&limit=1";
    private static final String SAMPLE_JSON_RESPONSE3="http://earthquake.usgs.gov/fdsnws/event/1/query?format=xml&starttime=2016-01-01&endtime=2016-01-31&minmagnitude=6&limit=10";
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-01-31&&limit=5";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Retereieving....");
        earthquakeListView= (ListView)findViewById(R.id.list);
        earthquakes=new ArrayList<>();
        adapter=new CustomAdapter(this, R.layout.row,earthquakes);
        earthquakeListView.setAdapter(adapter);
        earthquakeListView.setOnItemClickListener(this);
        loaderManager=getLoaderManager();
//        if (loaderManager.getLoader(loaderId)==null) {
            loaderManager.initLoader(1, null, this).forceLoad();
//        }else {
//            loaderManager.restartLoader(loaderId,null,this).forceLoad();
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Earthquake earth=earthquakes.get(i);
        Toast.makeText(this, ""+earth.getUrl(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        //loaderManager.destroyLoader(loaderId);
        progressDialog.show();
        return new EarthQuakeBackground(EarthquakeActivity.this,USGS_REQUEST_URL);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> list) {
        adapter.clear();
        if (list!=null) {
            adapter.addAll(list);
        }
        progressDialog.dismiss();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        adapter.clear();
    }
}
