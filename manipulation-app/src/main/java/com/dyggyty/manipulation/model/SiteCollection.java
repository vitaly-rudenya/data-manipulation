package com.dyggyty.manipulation.model;

import java.util.List;

/**
 * Sites collection.
 */
public class SiteCollection {

    private String collectionId;

    private List<SiteDetails> sites;

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public List<SiteDetails> getSites() {
        return sites;
    }

    public void setSites(List<SiteDetails> sites) {
        this.sites = sites;
    }
}
