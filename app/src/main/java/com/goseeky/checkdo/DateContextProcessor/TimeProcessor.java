package com.goseeky.checkdo.DateContextProcessor;

import com.goseeky.checkdo.util.EnglishNumberToWords;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abhay on 1/5/18.
 * Processor to get time of day
 */

public class TimeProcessor extends BaseProcessor {


    private Map<String,Float> timeMap;

    public TimeProcessor(String message) {
        super(message);
    }

    @Override
    protected void process() {
//        List<String> timePrefixes = new ArrayList<>();
//        timePrefixes.add("by");
//        timePrefixes.add("at");
//        timePrefixes.add("before");

        for(int i=1;i<getTokens().size();i++){
            if(timeMap.containsKey(getTokens().get(i))
                    && timeMap.get(getTokens().get(i))<13){

                float tokensTime = timeMap.get(getTokens().get(i));
                if(i<getTokens().size() - 2
                        && timeMap.containsKey(getTokens().get(i+1) + " " + getTokens().get(i+2))){
                    tokensTime += timeMap.get(getTokens().get(i+1) + " " + getTokens().get(i+2))/100.0;
                    i += 2;
                } else if(i<getTokens().size()-1
                        &&  timeMap.containsKey(getTokens().get(i+1))){
                    tokensTime += timeMap.get(getTokens().get(i+1))/100.0;
                    i += 1;
                }
                if(i<getTokens().size()-1 && getTokens().get(i+1).replace(".","").equals("pm")){
                    tokensTime += 12;
                }
                getVals().add(tokensTime);
            }
        }
    }

    @Override
    protected void initDataMap() {
        timeMap = new HashMap<>();
        for(int i = 0 ;i<=60;i++){
            timeMap.put(String.valueOf(i), (float) i);
            timeMap.put(EnglishNumberToWords.convert(i), (float) i);
        }
    }
}
