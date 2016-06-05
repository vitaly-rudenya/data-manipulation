package com.dyggyty.manipulation.reader;

import com.dyggyty.manipulation.reader.model.SiteCollection;
import com.dyggyty.manipulation.reader.model.SiteDetails;
import com.dyggyty.manipulation.utils.gson.GsonContainer;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JsonSourceReader implements SourceReader {
    @Override
    public boolean isAcceptable(File file) {
        return file.getName().toLowerCase().endsWith(".json");
    }

    @Override
    public SiteCollection parceSiteCollection(File file) throws FileNotFoundException {

        SiteCollection siteCollection = new SiteCollection();
        siteCollection.setCollectionId(file.getName());

        @SuppressWarnings("unchecked")
        SiteData[] siteData = GsonContainer.getGSON().fromJson(new FileReader(file), SiteData[].class);

        if (siteData != null && siteData.length > 0) {

            List<SiteDetails> siteDetails = Arrays.asList(siteData).stream().map(site -> {
                SiteDetails siteDetail = new SiteDetails();

                siteDetail.setId(site.getSiteId());
                siteDetail.setMobile(site.getMobile());
                siteDetail.setName(site.getName());
                siteDetail.setScore(site.getScore());

                return siteDetail;
            }).collect(Collectors.toList());
            siteCollection.setSites(siteDetails);
        }

        return siteCollection;
    }
}
