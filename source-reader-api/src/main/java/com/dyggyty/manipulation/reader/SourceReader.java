package com.dyggyty.manipulation.reader;

import com.dyggyty.manipulation.reader.model.SiteCollection;

import java.io.File;
import java.io.InputStream;

public interface SourceReader {

    /**
     * Check if file is acceptable for the parser or not.
     *
     * @param file File to check.
     * @return <b>TRUE</b> if file can be parsed or <B>FALSE</B> otherwise.
     */
    boolean isAcceptable(File file);

    /**
     * Parse sites collection from input stream.
     *
     * @param contentStream Input stream to parse.
     * @param streamName    Stream name.
     * @return Site collection object to output.
     */
    SiteCollection parseSiteCollection(InputStream contentStream, String streamName);
}
