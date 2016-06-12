package com.dyggyty.manipulation.reader;

import com.dyggyty.manipulation.reader.model.SiteCollection;
import com.dyggyty.manipulation.reader.model.SiteDetails;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

public class JsonSourceReaderTest {

    private static final String BASIC_JSON_SAMPLE = "[\n" +
            "  {\"site_id\": \"13000\", \"name\": \"example.com/json1\", \"mobile\": 1, \"score\": 21.2 },\n" +
            "  {\"site_id\": \"13001\", \"name\": \"www.example.com/json2\", \"mobile\": 0, \"score\": 97 },\n" +
            "  {\"site_id\": \"13002\", \"name\": \"example.com/json3\", \"mobile\": 0, \"score\": 311 }\n" +
            "]";
    private static final String MISSING_SITE_JSON_SAMPLE = "[\n" +
            "  {\"site_id\": \"13001\", \"mobile\": 0, \"score\": 97 }\n" +
            "]";
    private static final String EMPTY_SITE_JSON_SAMPLE = "[\n" +
            "  {\"site_id\": \"13001\", \"name\":\"\", \"mobile\": 0, \"score\": 97 }\n" +
            "]";

    private static final String EMPTY_JSON_SAMPLE = "";
    private static final String FILE_NAME = "test.json";

    @Test
    public void testParseSiteCollection() {
        SourceReader jsonReader = new JsonSourceReader();
        SiteCollection siteCollection = jsonReader.parseSiteCollection(new ByteArrayInputStream(BASIC_JSON_SAMPLE.getBytes()), FILE_NAME);

        assertEquals(FILE_NAME, siteCollection.getCollectionId());
        assertTrue("Must be 3 sites", siteCollection.getSites().size() == 3);

        SiteDetails siteDetails = siteCollection.getSites().get(1);
        assertEquals("13001", siteDetails.getId());
        assertEquals("example.com", siteDetails.getName());
        assertEquals("www.example.com/json2", siteDetails.getOriginalName());
        assertEquals(Boolean.FALSE, siteDetails.getMobile());
        assertEquals(new Double(97), siteDetails.getScore());
    }

    @Test
    public void testMissingParseSiteCollection() {
        SourceReader jsonReader = new JsonSourceReader();
        SiteCollection siteCollection = jsonReader.parseSiteCollection(new ByteArrayInputStream(EMPTY_SITE_JSON_SAMPLE.getBytes()), FILE_NAME);

        assertEquals(FILE_NAME, siteCollection.getCollectionId());
        assertTrue("Must be 1 site", siteCollection.getSites().size() == 1);

        SiteDetails siteDetails = siteCollection.getSites().get(0);
        assertEquals("13001", siteDetails.getId());
        assertEquals("", siteDetails.getName());
        assertEquals("", siteDetails.getOriginalName());
        assertEquals(Boolean.FALSE, siteDetails.getMobile());
        assertEquals(new Double(97), siteDetails.getScore());
    }

    @Test
    public void testEmptyParseSiteCollection() {
        SourceReader jsonReader = new JsonSourceReader();
        SiteCollection siteCollection = jsonReader.parseSiteCollection(new ByteArrayInputStream(MISSING_SITE_JSON_SAMPLE.getBytes()), FILE_NAME);

        assertEquals(FILE_NAME, siteCollection.getCollectionId());
        assertTrue("Must be 1 site", siteCollection.getSites().size() == 1);

        SiteDetails siteDetails = siteCollection.getSites().get(0);
        assertEquals("13001", siteDetails.getId());
        assertNull(siteDetails.getName());
        assertNull(siteDetails.getOriginalName());
        assertEquals(Boolean.FALSE, siteDetails.getMobile());
        assertEquals(new Double(97), siteDetails.getScore());
    }

    @Test
    public void testEmptyJsonParseSiteCollection() {
        SourceReader jsonReader = new JsonSourceReader();
        SiteCollection siteCollection = jsonReader.parseSiteCollection(new ByteArrayInputStream(EMPTY_JSON_SAMPLE.getBytes()), FILE_NAME);

        assertEquals(FILE_NAME, siteCollection.getCollectionId());
        assertNull("Must be no sites", siteCollection.getSites());
    }
}
