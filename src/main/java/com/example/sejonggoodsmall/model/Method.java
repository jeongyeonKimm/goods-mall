package com.example.sejonggoodsmall.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Method {
    DELIVERY("delivery"),
    PICKUP("pickup"),
    BOTH("both");

    private final String value;

    Method(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Method from(String value){
        for(Method method : Method.values()){
            if(method.getValue().equals(value)){
                return method;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
