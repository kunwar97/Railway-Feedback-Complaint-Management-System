package com.shreytripathi.ir_feedbackmanagement.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListResponse<T> {

    @Expose
    @SerializedName("data")
    private List<T> data;

    public List<T> getData() {
        return data;
    }
}
