package com.dyggyty.manipulation;

public interface Manipulation {

    /**
     * Parse specified folder.
     *
     * @param path       Path to the folder to parse.
     * @param outputFile Output file name.
     */
    void parseFolder(String path, String outputFile);
}
