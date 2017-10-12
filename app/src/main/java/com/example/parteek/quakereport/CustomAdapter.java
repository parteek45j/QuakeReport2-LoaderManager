package com.example.parteek.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Parteek on 9/25/2017.
 */

public class CustomAdapter extends ArrayAdapter<Earthquake> {
    Context context;
    int resource;
    ArrayList<Earthquake> list;
    Earthquake earthquake;
    String msg3,msg2;
    int magnitudeResource;
    GradientDrawable magnitudeCircle;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Earthquake> list) {
        super(context, resource, list);
        this.context=context;
        this.resource=resource;
        this.list=list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=null;
        earthquake=getItem(position);
        TextView textmag,textPlace,textPlace1,textTime,textTime1;
        view= LayoutInflater.from(context).inflate(resource,parent,false);


        textmag=(TextView)view.findViewById(R.id.textmag);
        magnitudeCircle=(GradientDrawable)textmag.getBackground();
        int magnitudeColor=getmagnitudeColor(earthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);
        if(String.valueOf(earthquake.getMagnitude()).length()==4){
            String[] point=String.valueOf(earthquake.getMagnitude()).split("\\.");
            Log.e("point",point[0]);
            if (point[0].contains("0")){
                textmag.setText(String.valueOf(earthquake.getMagnitude()));
            }else {
                int magnitude = (int) Math.floor(earthquake.getMagnitude());
                textmag.setText(String.valueOf(magnitude));
            }
        }else {
            textmag.setText(String.valueOf(earthquake.getMagnitude()));
        }

        //textPlace.setText(earthquake.getLocation());
        textPlace=(TextView)view.findViewById(R.id.textplace);
        textPlace1=(TextView)view.findViewById(R.id.distance);
        splitIt(earthquake.getLocation());
        textPlace1.setText(msg3+"Near");
        textPlace.setText(msg2);

        Date date=new Date(earthquake.getTime());
        textTime=(TextView)view.findViewById(R.id.texttime);
//        SimpleDateFormat dateFormat=new SimpleDateFormat("LLL dd/yyyy");
//        String time=dateFormat.format(date);
        String time=dateFormatter(date);
        textTime.setText(time);


        textTime1=(TextView)view.findViewById(R.id.texttime1);
//        SimpleDateFormat timeFormat=new SimpleDateFormat("h:mm a");
//        String time1=timeFormat.format(date);
        String time1=timeFormatter(date);
        textTime1.setText(time1);
        return view;
    }
    String dateFormatter(Date date){
        SimpleDateFormat dateFormat=new SimpleDateFormat("LLL dd/yyyy");
        return dateFormat.format(date);
    }
    String timeFormatter(Date date){
        SimpleDateFormat timeFormat=new SimpleDateFormat("h:mm a");
        return timeFormat.format(date);
    }
    String splitIt(String msg){
//        this.msg=msg;
        if(msg.contains("of")) {
            String[] msg1 = msg.split("of");
            msg3 = msg1[0];
            msg2=msg1[1];
        }else {
            msg2=msg;
            msg3="Exactly ";
        }
        return msg3;
    }
    int getmagnitudeColor(double magnitude){

        int magnitudeFloor=(int)Math.floor(magnitude);
        switch (magnitudeFloor){
            case 1:
                magnitudeResource= R.color.color1;
                break;
            case 2:
                magnitudeResource= R.color.color2;
                break;
            case 3:
                magnitudeResource= R.color.color3;
                break;
            case 4:
                magnitudeResource= R.color.color4;
                break;
            case 5:
                magnitudeResource= R.color.color5;
                break;
            case 6:
                magnitudeResource= R.color.color6;
                break;
            case 7:
                magnitudeResource= R.color.color7;
                break;
            case 8:
                magnitudeResource= R.color.color8;
                break;
            case 9:
                magnitudeResource= R.color.color9;
                break;
            case 10:
                magnitudeResource= R.color.color10;
                break;
            default:
                magnitudeResource= R.color.color11;
                break;
        }
        return ContextCompat.getColor(getContext(),magnitudeResource);
    }
}
