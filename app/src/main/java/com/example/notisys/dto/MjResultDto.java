package com.example.notisys.dto;


import java.util.List;

public class MjResultDto {
    String result;
    List<Facility> facilityList;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Facility> getFacilityList() {
        return facilityList;
    }

    public void setFacilityList(List<Facility> facilityList) {
        this.facilityList = facilityList;
    }

    @Override
    public String toString() {
        return "MjResultDto{" +
                "result='" + result + '\'' +
                ", facilityList=" + facilityList +
                '}';
    }
}