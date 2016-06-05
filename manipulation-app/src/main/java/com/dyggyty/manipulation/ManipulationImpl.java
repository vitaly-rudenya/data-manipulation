package com.dyggyty.manipulation;

import com.dyggyty.manipulation.reader.SourceReader;
import com.dyggyty.manipulation.reader.model.SiteCollection;
import com.dyggyty.manipulation.reader.model.SiteDetails;
import com.dyggyty.manipulation.utils.gson.GsonContainer;
import com.dyggyty.manipulation.web.KeywordsExtractor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.List;

@Component
public class ManipulationImpl implements Manipulation {

    private Log logger = LogFactory.getLog(getClass());

    @Autowired(required = false)
    private Collection<SourceReader> sourceReaders;
    private KeywordsExtractor keywordsExtractor;

    @Override
    public void parceFolder(String path, String output) {

        if (CollectionUtils.isEmpty(sourceReaders)) {
            logger.error("No source readers were registered");
            return;
        }

        File folder = new File(path);
        if (!folder.exists()) {
            logger.error("Can't filnd the folder " + path);
            return;
        }

        if (!folder.isDirectory()) {
            logger.error(path + " is not a folder");
            return;
        }

        if (!folder.canRead()) {
            logger.error("Can;t read form the " + path);
            return;
        }

        try (OutputStreamWriter streamWriter = new OutputStreamWriter(output == null ? System.out : new FileOutputStream(output))) {
            folder.listFiles(pathname -> {
                sourceReaders.stream()
                        .filter(sourceReader -> sourceReader.isAcceptable(pathname))
                        .forEach(sourceReader -> writeSiteCollection(streamWriter, sourceReader, pathname));
                return false;
            });
        } catch (IOException e) {
            logger.error("Can't write to the file: " + path, e);
        }
    }

    private void writeSiteCollection(OutputStreamWriter streamWriter, SourceReader sourceReader, File pathname) {

        try {
            SiteCollection siteCollection = sourceReader.parceSiteCollection(pathname);
            if (siteCollection != null) {

                List<SiteDetails> siteDetails = siteCollection.getSites();
                //Extracting site keywords
                if (keywordsExtractor != null && CollectionUtils.isNotEmpty(siteDetails)) {
                    siteDetails.stream()
                            .filter(site -> !StringUtils.isEmpty(site.getName()))
                            .forEach(site -> site.setKeywords(keywordsExtractor.getKeywords(site.getName())));
                }

                String siteCollectionJson = GsonContainer.getGSON().toJson(siteCollection);
                streamWriter.write(siteCollectionJson);
                streamWriter.write("\n");
            }
        } catch (IOException e) {
            logger.error("Can't write to the output file: ", e);
        }
    }
}
