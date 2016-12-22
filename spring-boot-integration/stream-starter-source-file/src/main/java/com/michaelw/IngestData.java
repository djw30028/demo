package com.michaelw;

/**
 * Created by michaelwang on 12/22/16.
 */
public class IngestData {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "IngestData{" +
                "value='" + value + '\'' +
                '}';
    }
}
