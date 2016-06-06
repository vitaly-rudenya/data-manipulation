package com.dyggyty.manipulation.reader;

import com.dyggyty.manipulation.reader.model.SiteCollection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface SourceReader {

    /**
     * Check if file is acceptable for the parcer or not.
     *
     * @param file File to check.
     * @return <b>TRUE</b> if file can be parced or <B>FALSE</B> otherwise.
     */
    boolean isAcceptable(File file);

    SiteCollection parceSiteCollection(File file) throws IOException;
}
