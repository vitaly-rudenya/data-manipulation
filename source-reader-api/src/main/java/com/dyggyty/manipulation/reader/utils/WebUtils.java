package com.dyggyty.manipulation.reader.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class WebUtils {

    private static final String HTTP_PREFIX = "http://";

    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url.toLowerCase().startsWith(HTTP_PREFIX) ? url : HTTP_PREFIX + url);
        String domain = uri.getHost();
        return domain.toLowerCase().startsWith("www.") ? domain.substring(4) : domain;
    }
}
