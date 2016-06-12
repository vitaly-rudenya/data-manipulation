package com.dyggyty.manipulation.reader;

import com.dyggyty.manipulation.reader.model.SiteCollection;
import com.dyggyty.manipulation.reader.model.SiteDetails;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CsvSourceReaderTest {

    private static final String BASIC_CSV_SAMPLE = "id,name,is mobile,score\n" +
            "12000,example.com/csv1,true,454\n" +
            "12001,example.com/csv2,true,128\n" +
            "12002,example.com/csv3,false,522";
    private static final String MISSING_SITE_CSV_SAMPLE = "id,name,is mobile,score\n" +
            "12001,,true,128\n" ;

    private static final String MISSING_HEADER_CSV_SAMPLE = "12001,example.com/csv2,true,128\n" ;
    private static final String EMPTY_CSV_SAMPLE = "" ;
    private static final String FILE_NAME = "test.csv";

    @Test
    public void testParseSiteCollection() {
        SourceReader jsonReader = new CsvSourceReader();
        SiteCollection siteCollection = jsonReader.parseSiteCollection(new ByteArrayInputStream(BASIC_CSV_SAMPLE.getBytes()), FILE_NAME);

        assertEquals(FILE_NAME, siteCollection.getCollectionId());
        assertTrue("Must be 3 sites", siteCollection.getSites().size() == 3);

        SiteDetails siteDetails = siteCollection.getSites().get(1);
        assertEquals("12001", siteDetails.getId());
        assertEquals("example.com", siteDetails.getName());
        assertEquals("example.com/csv2", siteDetails.getOriginalName());
        assertEquals(Boolean.TRUE, siteDetails.getMobile());
        assertEquals(new Double(128), siteDetails.getScore());
    }

    @Test
    public void testEmptyParseSiteCollection() {
        SourceReader jsonReader = new CsvSourceReader();
        SiteCollection siteCollection = jsonReader.parseSiteCollection(new ByteArrayInputStream(MISSING_SITE_CSV_SAMPLE.getBytes()), FILE_NAME);

        assertEquals(FILE_NAME, siteCollection.getCollectionId());
        assertTrue("Must be 1 site", siteCollection.getSites().size() == 1);

        SiteDetails siteDetails = siteCollection.getSites().get(0);
        assertEquals("12001", siteDetails.getId());
        assertEquals("", siteDetails.getName());
        assertEquals("", siteDetails.getOriginalName());
        assertEquals(Boolean.TRUE, siteDetails.getMobile());
        assertEquals(new Double(128), siteDetails.getScore());
    }

    @Test
    public void testMissingHeaderParseSiteCollection() {
        SourceReader jsonReader = new CsvSourceReader();
        SiteCollection siteCollection = jsonReader.parseSiteCollection(new ByteArrayInputStream(MISSING_HEADER_CSV_SAMPLE.getBytes()), FILE_NAME);

        assertEquals(FILE_NAME, siteCollection.getCollectionId());
        assertTrue("Must be 1 site", siteCollection.getSites().size() == 1);

        SiteDetails siteDetails = siteCollection.getSites().get(0);
        assertEquals("12001", siteDetails.getId());
        assertEquals("example.com", siteDetails.getName());
        assertEquals("example.com/csv2", siteDetails.getOriginalName());
        assertEquals(Boolean.TRUE, siteDetails.getMobile());
        assertEquals(new Double(128), siteDetails.getScore());
    }

    @Test
    public void testEmptyCsvParseSiteCollection() {
        SourceReader jsonReader = new CsvSourceReader();
        SiteCollection siteCollection = jsonReader.parseSiteCollection(new ByteArrayInputStream(EMPTY_CSV_SAMPLE.getBytes()), FILE_NAME);

        assertEquals(FILE_NAME, siteCollection.getCollectionId());
        assertNull("Must be no sites", siteCollection.getSites());
    }
}
