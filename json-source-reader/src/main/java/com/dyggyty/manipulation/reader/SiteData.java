package com.dyggyty.manipulation.reader;

import com.google.gson.annotations.SerializedName;

public class SiteData {

    @SerializedName("site_id")
    private String siteId;
    private String name;
    private Boolean mobile;
    private Double score;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getMobile() {
        return mobile;
    }

    public void setMobile(Boolean mobile) {
        this.mobile = mobile;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
