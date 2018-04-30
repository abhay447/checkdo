package com.goseeky.checkdo.DateContextProcessor;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by abhay on 1/5/18.
 */

public class MonthProcessor extends BaseProcessor {
    Map<String,Integer> monthMap;
    public MonthProcessor(String message) {
        super(message);
    }

    @Override
    protected void process() {
        for (String token : getTokens()){
            if(monthMap.containsKey(token)){
                getVals().add(Float.valueOf(monthMap.get(token)));
            }
        }
    }

    @Override
    protected void initDataMap() {
        monthMap = new HashMap<>();
        monthMap.put("january", Calendar.JANUARY + 1);
        monthMap.put("february", Calendar.FEBRUARY + 1);
        monthMap.put("march", Calendar.MARCH + 1);
        monthMap.put("april", Calendar.APRIL + 1);
        monthMap.put("may", Calendar.MAY + 1);
        monthMap.put("june", Calendar.JUNE + 1);
        monthMap.put("july", Calendar.JULY + 1);
        monthMap.put("august", Calendar.AUGUST + 1);
        monthMap.put("september", Calendar.SEPTEMBER + 1);
        monthMap.put("october", Calendar.OCTOBER + 1);
        monthMap.put("november", Calendar.NOVEMBER + 1);
        monthMap.put("december", Calendar.DECEMBER + 1);

        monthMap.put("jan", Calendar.JANUARY + 1);
        monthMap.put("feb", Calendar.FEBRUARY + 1);
        monthMap.put("mar", Calendar.MARCH + 1);
        monthMap.put("apr", Calendar.APRIL + 1);
        monthMap.put("may", Calendar.MAY + 1);
        monthMap.put("jun", Calendar.JUNE + 1);
        monthMap.put("jul", Calendar.JULY + 1);
        monthMap.put("aug", Calendar.AUGUST + 1);
        monthMap.put("sept", Calendar.SEPTEMBER + 1);
        monthMap.put("oct", Calendar.OCTOBER + 1);
        monthMap.put("nov", Calendar.NOVEMBER + 1);
        monthMap.put("dec", Calendar.DECEMBER + 1);

    }
}
