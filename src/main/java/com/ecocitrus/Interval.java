package com.ecocitrus;

import javax.validation.constraints.Null;

/**
 * Created by Administrator on 2016-10-07.
 */
public enum Interval {

    SINGLETIMEPAYMENT("Single Time Payment", null),
    EVERYMONTH("Each Month", 12),
    EVERYSECONDMONTH("Every Second Month", 6),
    THIRDMONTH("Every Third Month", 4),
    SIXTHMONTH("Every Sixth Month", 2),
    ONCEAYEAR("Once a Year", 1),;

    private final String stringValue;
    private final Integer numValue;

    Interval(String stringValue, Integer numValue) {
        this.stringValue = stringValue;
        this.numValue = numValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public Integer getNumValue() {
        return numValue;
    }
}
