package com.goseeky.checkdo.DateContextProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abhay on 30/4/18.
 * This class identifies getTokens() for Date of Month processing
 */

public class DOMProcessor extends BaseProcessor {
    private Map<String,Integer> dateMap;

    public DOMProcessor(String message) {
        super(message);
        initDataMap();
    }

    @Override
    protected void process(){
        for(int i=0;i<getTokens().size();i++){
            if(dateMap.containsKey(getTokens().get(i))){
                if(i<getTokens().size()-1 && dateMap.containsKey(getTokens().get(i) + " " + getTokens().get(i + 1))){
                    getVals().add(Float.valueOf(dateMap.get(getTokens().get(i) + " " + getTokens().get(i + 1))));
                    i += 1;
                } else{
                    getVals().add(Float.valueOf(dateMap.get(getTokens().get(i))));
                }
            }else if(i<getTokens().size()-1 && dateMap.containsKey(getTokens().get(i) + " " + getTokens().get(i + 1))){
                getVals().add(Float.valueOf(dateMap.get(getTokens().get(i) + " " + getTokens().get(i + 1))));
                i += 1;
            }
        }
    }

    @Override
    protected void initDataMap() {
        dateMap = new HashMap<>();
        dateMap.put("first",1);
        dateMap.put("second",2);
        dateMap.put("third",3);
        dateMap.put("fourth",4);
        dateMap.put("fifth",5);
        dateMap.put("sixth",6);
        dateMap.put("seventh",7);
        dateMap.put("eighth",8);
        dateMap.put("nineth",9);
        dateMap.put("tenth",10);
        dateMap.put("eleventh",11);
        dateMap.put("twelvth",12);
        dateMap.put("thirteenth",13);
        dateMap.put("forteenth",14);
        dateMap.put("fifteenth",15);
        dateMap.put("sixteenth",16);
        dateMap.put("seventeenth",17);
        dateMap.put("eighteenth",18);
        dateMap.put("nineteenth",19);
        dateMap.put("twentieth",20);
        dateMap.put("twenty first",21);
        dateMap.put("twenty second",22);
        dateMap.put("twenty third",23);
        dateMap.put("twenty fourth",24);
        dateMap.put("twenty fifth",25);
        dateMap.put("twenty sixth",26);
        dateMap.put("twenty seventh",27);
        dateMap.put("twenty eighth",28);
        dateMap.put("twenty nineth",29);
        dateMap.put("thirtieth",30);
        dateMap.put("thirty first",31);
        dateMap.put("1st",1);
        dateMap.put("2nd",2);
        dateMap.put("3rd",3);
        dateMap.put("4th",4);
        dateMap.put("5th",5);
        dateMap.put("6th",6);
        dateMap.put("7th",7);
        dateMap.put("8th",8);
        dateMap.put("9th",9);
        dateMap.put("10th",10);
        dateMap.put("11th",11);
        dateMap.put("12th",12);
        dateMap.put("13th",13);
        dateMap.put("14th",14);
        dateMap.put("15th",15);
        dateMap.put("16th",16);
        dateMap.put("17th",17);
        dateMap.put("18th",18);
        dateMap.put("19th",19);
        dateMap.put("20th",20);
        dateMap.put("21st",21);
        dateMap.put("22nd",22);
        dateMap.put("23rd",23);
        dateMap.put("24th",24);
        dateMap.put("25th",25);
        dateMap.put("26th",26);
        dateMap.put("27th",27);
        dateMap.put("28th",28);
        dateMap.put("29th",29);
        dateMap.put("30th",30);
        dateMap.put("31st",31);

    }
}
