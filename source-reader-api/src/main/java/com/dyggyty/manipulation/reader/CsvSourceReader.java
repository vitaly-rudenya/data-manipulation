package com.dyggyty.manipulation.reader;

import au.com.bytecode.opencsv.CSVReader;
import com.dyggyty.manipulation.reader.model.SiteCollection;
import com.dyggyty.manipulation.reader.model.SiteDetails;
import com.dyggyty.manipulation.reader.utils.WebUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvSourceReader implements SourceReader {

    private final Log logger = LogFactory.getLog(getClass());

    @Override
    public boolean isAcceptable(File file) {
        return file.getName().toLowerCase().endsWith(".csv");
    }

    @Override
    public SiteCollection parseSiteCollection(InputStream contentStream, String streamName) {

        SiteCollection siteCollection = new SiteCollection();
        siteCollection.setCollectionId(streamName);

        try (CSVReader reader = new CSVReader(new InputStreamReader(contentStream))) {
            String[] nextLine = reader.readNext();

            //skip the CSV header
            if (nextLine != null && nextLine.length > 0 && nextLine[0].toLowerCase().startsWith("id")) {
                nextLine = reader.readNext();
            }

            if (nextLine != null) {
                List<SiteDetails> siteDetails = new ArrayList<>();
                do {
                    //Ski[p empty strings
                    if (nextLine.length >= 4) {
                        SiteDetails siteDetail = new SiteDetails();

                        siteDetail.setId(nextLine[0]);

                        String siteName = nextLine[1];
                        siteDetail.setOriginalName(siteName);
                        if (siteName != null) {
                            try {
                                siteDetail.setName(WebUtils.getDomainName(siteName));
                            } catch (URISyntaxException ex) {
                                logger.error("Invalid URL: " + siteName);
                                siteDetail.setName(siteName);
                            }
                        }

                        String isMobile = nextLine[2];
                        if (isMobile != null) {
                            siteDetail.setMobile(Boolean.valueOf(isMobile));
                        }


                        String score = nextLine[3];
                        if (score != null && score.length() > 0) {
                            try {
                                siteDetail.setScore(Double.valueOf(score));
                            } catch (NumberFormatException nfe) {
                                logger.error("Can't parse score " + score, nfe);
                            }
                        }

                        siteDetails.add(siteDetail);
                    }
                } while ((nextLine = reader.readNext()) != null);

                siteCollection.setSites(siteDetails);
            }
        } catch (IOException ex) {
            logger.error("Can't read data from " + streamName);
        }

        return siteCollection;
    }
}

