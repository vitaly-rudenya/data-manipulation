package com.dyggyty.manipulation.web;

import java.io.IOException;

public interface KeywordsExtractor {

    /**
     * Retrieve keywords for the specified site URL.
     *
     * @param siteUrl Site URL
     * @return Comma separated list of keywords.
     * @throws IOException Throws when IO error occur,
     */
    String getKeywords(String siteUrl) throws IOException;
}
