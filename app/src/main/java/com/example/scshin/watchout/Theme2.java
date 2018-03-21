package com.example.scshin.watchout;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
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

public class Theme2 extends LinearLayout {
    TextView DateGre;
    TextView TxtTime;
    TextView TxtTimeBack;
    TextView TxtTimeTick;
    UsedAsync asyncTask;
    ProgressHandler handler;

    String timeTick;
    Boolean check = true;

    public Theme2(Context context) {
        super(context);
        init(context);
    }

    public Theme2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.theme2, this, true);
        DateGre = (TextView) findViewById(R.id.txtDate);
        TxtTime = (TextView) findViewById(R.id.txtTime);
        TxtTimeBack = (TextView) findViewById(R.id.txtTimeBack);
        TxtTimeTick = (TextView) findViewById(R.id.txtTimeTick);

        Typeface typeFace = ResourcesCompat.getFont(getContext(), R.font.liquidcrystal);
        TxtTime.setTypeface(typeFace);
        TxtTimeBack.setTypeface(typeFace);
        TxtTimeTick.setTypeface(typeFace);
        TxtTimeTick.setPaintFlags(TxtTimeTick.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);


        handler = new ProgressHandler();
        runTime();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    public void runTime(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if(check){
                            timeTick = "  :  ";
                            check  = false;
                        } else {
                            timeTick = "";
                            check = true;
                        }

                        Message message = handler.obtainMessage();
                        handler.sendMessage(message);

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

                dateGre = String.format("%02d/%02d/%02d (%s)", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), dayOfWeek);

//                hour = String.format("%02d", cal.get(Calendar.HOUR));
//                minute = String.format("%02d", cal.get(Calendar.MINUTE));
//                second = String.format("%02d", cal.get(Calendar.SECOND));

                time = String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
                publishProgress();

                try{
                    Thread.sleep(1000);
                } catch(InterruptedException ex ) {}
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
//
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            DateGre.setText(dateGre);
            TxtTime.setText(time);
        }
    }

    class ProgressHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            TxtTimeTick.setText(timeTick);
        }
    }
}
