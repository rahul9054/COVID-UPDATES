package com.rajendra.covid19ui;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictDelta {
    @SerializedName("confirmed")
    @Expose
    private Integer confirmed;
    @SerializedName("deceased")
    @Expose
    private Integer deceased;
    @SerializedName("recovered")
    @Expose
    private Integer recovered;

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public Integer getDeceased() {
        return deceased;
    }

    public void setDeceased(Integer deceased) {
        this.deceased = deceased;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }
}
