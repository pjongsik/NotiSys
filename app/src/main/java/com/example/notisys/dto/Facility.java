package com.example.notisys.dto;

public class Facility {
    String CLOSE_STARTDATE;
    Integer RESERVATION_COUNT;
    String NAME;
    Integer UNIT;
    String RECENT_DATE;
    String FACILITY_TYPE;
    String CLOSE_DATE;
    Integer IDX;
    String MEMO;
    Integer IS_HOLIDAY;
    String INFO; // "{\"person\":4,\"deposit\":100,\"type\":\"숙박\",\"floor_space\":41.81,\"reservation_deposit\":100,\"group\":0}",
    String CLOSE_REASON;
    String CODE;
    String CLOSE_ENDDATE;


    public String getCLOSE_STARTDATE() {
        return CLOSE_STARTDATE;
    }

    public void setCLOSE_STARTDATE(String CLOSE_STARTDATE) {
        this.CLOSE_STARTDATE = CLOSE_STARTDATE;
    }

    public Integer getRESERVATION_COUNT() {
        return RESERVATION_COUNT;
    }

    public void setRESERVATION_COUNT(Integer RESERVATION_COUNT) {
        this.RESERVATION_COUNT = RESERVATION_COUNT;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public Integer getUNIT() {
        return UNIT;
    }

    public void setUNIT(Integer UNIT) {
        this.UNIT = UNIT;
    }

    public String getRECENT_DATE() {
        return RECENT_DATE;
    }

    public void setRECENT_DATE(String RECENT_DATE) {
        this.RECENT_DATE = RECENT_DATE;
    }

    public String getFACILITY_TYPE() {
        return FACILITY_TYPE;
    }

    public void setFACILITY_TYPE(String FACILITY_TYPE) {
        this.FACILITY_TYPE = FACILITY_TYPE;
    }

    public String getCLOSE_DATE() {
        return CLOSE_DATE;
    }

    public void setCLOSE_DATE(String CLOSE_DATE) {
        this.CLOSE_DATE = CLOSE_DATE;
    }

    public Integer getIDX() {
        return IDX;
    }

    public void setIDX(Integer IDX) {
        this.IDX = IDX;
    }

    public String getMEMO() {
        return MEMO;
    }

    public void setMEMO(String MEMO) {
        this.MEMO = MEMO;
    }

    public Integer getIS_HOLIDAY() {
        return IS_HOLIDAY;
    }

    public void setIS_HOLIDAY(Integer IS_HOLIDAY) {
        this.IS_HOLIDAY = IS_HOLIDAY;
    }

    public String getINFO() {
        return INFO;
    }

    public void setINFO(String INFO) {
        this.INFO = INFO;
    }

    public String getCLOSE_REASON() {
        return CLOSE_REASON;
    }

    public void setCLOSE_REASON(String CLOSE_REASON) {
        this.CLOSE_REASON = CLOSE_REASON;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getCLOSE_ENDDATE() {
        return CLOSE_ENDDATE;
    }

    public void setCLOSE_ENDDATE(String CLOSE_ENDDATE) {
        this.CLOSE_ENDDATE = CLOSE_ENDDATE;
    }

    @Override
    public String toString() {
        return "Facility{" +
                "CLOSE_STARTDATE='" + CLOSE_STARTDATE + '\'' +
                ", RESERVATION_COUNT=" + RESERVATION_COUNT +
                ", NAME='" + NAME + '\'' +
                ", UNIT=" + UNIT +
                ", RECENT_DATE='" + RECENT_DATE + '\'' +
                ", FACILITY_TYPE='" + FACILITY_TYPE + '\'' +
                ", CLOSE_DATE='" + CLOSE_DATE + '\'' +
                ", IDX=" + IDX +
                ", MEMO='" + MEMO + '\'' +
                ", IS_HOLIDAY=" + IS_HOLIDAY +
                ", INFO='" + INFO + '\'' +
                ", CLOSE_REASON='" + CLOSE_REASON + '\'' +
                ", CODE='" + CODE + '\'' +
                ", CLOSE_ENDDATE='" + CLOSE_ENDDATE + '\'' +
                '}';
    }
}
