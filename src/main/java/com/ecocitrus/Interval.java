package com.ecocitrus;

import javax.validation.constraints.Null;

/**
 * Created by Administrator on 2016-10-07.
 */
public enum Interval {

    SINGLETIMEPAYMENT("Single Time Payment", null),
    EVERYMONTH("Each Month", 1),
    EVERYSECONDMONTH("Every Second Month", 2),
    THIRDMONTH("Every Quarter", 3),
    SIXTHMONTH("Semi-annual", 6),
    ONCEAYEAR("Annually", 12),;

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
