package com.oppoindia.billionbeats.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderBoard {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rank")
    @Expose
    private String rank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

}
