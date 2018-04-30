package com.goseeky.checkdo.DateContextProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by abhay on 30/4/18.
 * Base Processor for date time conversion from plain text
 */

public abstract class BaseProcessor {

    private List<String> tokens;
    private List<Float> vals;

    BaseProcessor(String message) {
        tokens = Arrays.asList(message.toLowerCase().split(" "));
        vals = new ArrayList<>();
        initDataMap();
        process();
    }

    List<String> getTokens() {
        return tokens;
    }


    public List<Float> getVals(){return this.vals;}

    protected abstract void process();

    protected abstract void initDataMap();

}
