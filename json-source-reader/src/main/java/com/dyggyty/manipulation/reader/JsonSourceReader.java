package com.dyggyty.manipulation.reader;

import com.dyggyty.manipulation.reader.model.SiteCollection;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class JsonSourceReader implements SourceReader {
    @Override
    public boolean isAcceptable(File file) {
        return file.getName().toLowerCase().endsWith(".json");
    }

    @Override
    public SiteCollection parceSiteCollection(File file) {
        return null;
    }
}
