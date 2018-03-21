package com.example.scshin.watchout;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by scshin on 2018-03-20.
 */

public class Theme1 extends LinearLayout {
    TextView DateGre;
    TextView TxtTime;
    TextView TxtTimeBack;
    TextView TxtAPM;
    UsedAsync asyncTask;


    public Theme1(Context context) {
        super(context);
        init(context);
    }

    public Theme1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.theme1, this, true);
        DateGre = (TextView) findViewById(R.id.txtDate);
        TxtTime = (TextView) findViewById(R.id.txtTime);
        TxtTimeBack = (TextView) findViewById(R.id.txtTimeBack);
        TxtAPM = (TextView) findViewById(R.id.txtAPM);

        Typeface typeFace = ResourcesCompat.getFont(getContext(), R.font.liquidcrystal);
        TxtTime.setTypeface(typeFace);
        TxtTimeBack.setTypeface(typeFace);


        runTime();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    public void runTime(){

        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {}
                }
            }
        });
        thread.start();

        asyncTask = new UsedAsync();
        asyncTask.execute();
    }

    class UsedAsync extends AsyncTask<Integer, Integer, Integer> {
        String dateGre;
        Calendar cal;
        String dayOfWeek;
        String apm;
//        String hour;
//        String second;
//        String minute;
        String time;

        @Override
        protected Integer doInBackground(Integer... params) {
            while (isCancelled() == false){
                cal = new GregorianCalendar();

                switch (cal.get(Calendar.DAY_OF_WEEK)){
                    case 1: dayOfWeek = "일"; break;
                    case 2: dayOfWeek = "월"; break;
                    case 3: dayOfWeek = "화"; break;
                    case 4: dayOfWeek = "수"; break;
                    case 5: dayOfWeek = "목"; break;
                    case 6: dayOfWeek = "금"; break;
                    case 7: dayOfWeek = "토"; break;
                }

                switch (cal.get(Calendar.AM_PM)){
                    case 0: apm = "AM"; break;
                    case 1: apm = "PM"; break;
                    default: apm = "AM";
                }


                dateGre = String.format("%02d/%02d/%02d (%s)", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), dayOfWeek);
                apm = String.format("%s", apm);


//                hour = String.format("%02d", cal.get(Calendar.HOUR));
//                minute = String.format("%02d", cal.get(Calendar.MINUTE));
//                second = String.format("%02d", cal.get(Calendar.SECOND));

                time = String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
                publishProgress();

                try{
                    Thread.sleep(1000);
                } catch(InterruptedException ex ) {}
            }

            return null;
        }

//        @Override
//        protected void onPreExecute() {
//            cal = new GregorianCalendar();
//            timeGre = String.format("%d/%d/%d %d:%d:%d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1,
//                    cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE),
//                    cal.get(Calendar.SECOND));
//            Gre.setText(timeGre);
//
//            super.onPreExecute();
//        }

//        @Override
//        protected void onPostExecute(Integer integer) {
//            super.onPostExecute(integer);
//        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            DateGre.setText(dateGre);
            TxtTime.setText(time);
            TxtAPM.setText(apm);

            super.onProgressUpdate(values);
        }
    }
}
