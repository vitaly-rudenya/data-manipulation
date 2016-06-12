package com.dyggyty.manipulation.reader.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class WebUtils {

    private static final String HTTP_PREFIX = "http://";
    private static final String WWW_PREFIX = "www.";

    private WebUtils(){
        //empty constructor
    }

    /**
     * Extract poor domain name from the specified URL.
     *
     * @param url URL to extract domain name.
     * @return Poor domain name value.
     * @throws URISyntaxException Occur when URL format error.
     */
    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url.toLowerCase().startsWith(HTTP_PREFIX) ? url : HTTP_PREFIX + url);
        String domain = uri.getHost();
        return domain.toLowerCase().startsWith(WWW_PREFIX) ? domain.substring(WWW_PREFIX.length()) : domain;
    }
}
