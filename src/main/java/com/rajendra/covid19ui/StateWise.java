package com.rajendra.covid19ui;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateWise {

    @SerializedName("statewise")
    @Expose
    private List<StateWiseCaseDetails> statewise = null;

    public List<StateWiseCaseDetails> getStatewise() {
        return statewise;
    }

    public void setStatewise(List<StateWiseCaseDetails> statewise) {
        this.statewise = statewise;
    }
}
