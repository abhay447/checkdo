package com.goseeky.checkdo.util;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;

import com.goseeky.checkdo.DateContextProcessor.DOMProcessor;
import com.goseeky.checkdo.DateContextProcessor.DOWProcessor;
import com.goseeky.checkdo.DateContextProcessor.MonthProcessor;
import com.goseeky.checkdo.DateContextProcessor.TimeProcessor;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by abhay on 1/5/18.
 * Utility to generate date time
 */

public class DateGenUtil {

    private String message;
    private String dateTimeString;

    public DateGenUtil(String message) {
        this.message = message;
        process();
    }

    @SuppressLint("DefaultLocale")
    private void process() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int hour = cal.get(Calendar.HOUR_OF_DAY) + 1;
        int minute = cal.get(Calendar.MINUTE);

        //Process DOM
        DOMProcessor domProcessor = new DOMProcessor(message);
        if(domProcessor.getVals().size() > 0 ){
            dayOfMonth = Math.round(domProcessor.getVals().get(domProcessor.getVals().size() - 1));
        }
        DOWProcessor dowProcessor = new DOWProcessor(message);
        if(dowProcessor.getVals().size() > 0 ){
            dayOfMonth = Math.round(dowProcessor.getVals().get(dowProcessor.getVals().size() - 1 ));
            if(dayOfMonth < 0){
                dayOfMonth = -dayOfMonth;
                month += 1;
            }
        }

        MonthProcessor monthProcessor = new MonthProcessor(message);
        if(monthProcessor.getVals().size()>0){
            month = Math.round(monthProcessor.getVals().get(monthProcessor.getVals().size() - 1));
        }

        TimeProcessor timeProcessor = new TimeProcessor(message);
        if(timeProcessor.getVals().size()>0){
            hour = Math.round(timeProcessor.getVals().get(timeProcessor.getVals().size() -1 ));
            minute = Math.round(timeProcessor.getVals().get(timeProcessor.getVals().size()-1) * 100) % 100;
        }

        dateTimeString = String.valueOf(year) + "-"
                + String.format("%02d",month) + "-"
                + String.format("%02d",dayOfMonth) + " "
                + String.format("%02d",hour) + ":"
                + String.format("%02d",minute) + ":00.000";
    }

    public String getDateTimeString() {
        return dateTimeString;
    }

    public Long getTimeStampFromString(String mDateTimeString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(mDateTimeString);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            return timestamp.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1L;
        }

    }
}
