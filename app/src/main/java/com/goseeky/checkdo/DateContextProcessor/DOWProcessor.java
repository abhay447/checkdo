package com.goseeky.checkdo.DateContextProcessor;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by abhay on 30/4/18.
 * Day of week processor
 */

public class DOWProcessor extends BaseProcessor {
    private Map<String,Integer> dayMap;
    private Calendar cal;

    public DOWProcessor(String message) {
        super(message);
    }

    @Override
    protected void process() {
        int thisMonth = cal.get(Calendar.MONTH);

        for(int i=0;i<getTokens().size();i++){
            cal.setTime(new Date());
            int diff = 0;
            if(i < getTokens().size() -1 && dayMap.containsKey(getTokens().get(i) + " " + getTokens().get(i+1))){
                diff = dayMap.get(getTokens().get(i) + " " + getTokens().get(i+1));
                i += 1;
            } else if (dayMap.containsKey(getTokens().get(i))){
                diff = dayMap.get(getTokens().get(i)) + 1;
            }
            if(diff > 0){
                cal.add(Calendar.DATE,diff);
                float value = cal.get(Calendar.MONTH) > thisMonth ? -cal.get(Calendar.DAY_OF_MONTH) : cal.get(Calendar.DAY_OF_MONTH);
                getVals().add(value);
            }
        }

    }

    @Override
    protected void initDataMap() {
        dayMap = new HashMap<>();
        cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);

        dayMap.put("sunday",(-weekDay + Calendar.SUNDAY)>0?-weekDay+Calendar.SUNDAY:-weekDay+Calendar.SUNDAY+7);
        dayMap.put("monday",(-weekDay + Calendar.MONDAY)>0?-weekDay+Calendar.MONDAY:-weekDay+Calendar.MONDAY+7);
        dayMap.put("tuesday",(-weekDay + Calendar.TUESDAY)>0?-weekDay+Calendar.TUESDAY:-weekDay+Calendar.TUESDAY+7);
        dayMap.put("wednesday",(-weekDay + Calendar.WEDNESDAY)>0?-weekDay+Calendar.WEDNESDAY:-weekDay+Calendar.WEDNESDAY+7);
        dayMap.put("thursday",(-weekDay + Calendar.THURSDAY)>0?-weekDay+Calendar.THURSDAY:-weekDay+Calendar.THURSDAY+7);
        dayMap.put("friday",(-weekDay + Calendar.FRIDAY)>0?-weekDay+Calendar.FRIDAY:-weekDay+Calendar.FRIDAY+7);
        dayMap.put("saturday",(-weekDay + Calendar.SATURDAY)>0?-weekDay+Calendar.SATURDAY:-weekDay+Calendar.SATURDAY+7);

        dayMap.put("next sunday",-weekDay + Calendar.SUNDAY + 7);
        dayMap.put("next monday",-weekDay + Calendar.MONDAY + 7);
        dayMap.put("next tuesday",-weekDay + Calendar.TUESDAY+ 7);
        dayMap.put("next wednesday",-weekDay + Calendar.WEDNESDAY +7);
        dayMap.put("next thursday",-weekDay + Calendar.THURSDAY + 7);
        dayMap.put("next friday",-weekDay + Calendar.FRIDAY + 7);
        dayMap.put("next saturday",-weekDay + Calendar.SATURDAY + 7);

        dayMap.put("next week",7);

        dayMap.put("today", 0);
        dayMap.put("tomorrow", 1);
        dayMap.put("day after tomorrow", 3);
    }
}
