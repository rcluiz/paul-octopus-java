package com.ciandt.paul.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciandt.paul.Config;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

/**
 * Utility class to handle GCS operations
 */
@Service
public class GCSUtils {

    private static Logger logger = LoggerFactory.getLogger(GCSUtils.class.getName());

    @Autowired
    private Config config;

    private static Storage storage;
    
    /**
     * Read the content of a text file from GCS
     */
    public String readFile(String bucket, String filename) throws IOException {
        if (config.isDebugEnabled()) {
            logger.debug("Reading file from GCS... Bucket name = " + bucket + ", filename = " + filename);
        }
        Blob blob = getStorage().get(bucket, filename);
        return new String(blob.getContent());
    }

    /**
     * Write a text file to GCS
     */
    public void writeFile(String bucket, String filename, String content) throws IOException {
        if (config.isDebugEnabled()) {
            logger.debug("Uploading a file to GCS. Bucket name = " + bucket + ", filename = " + filename);
        }

        Storage storage = getStorage();
        BlobInfo blobInfo = BlobInfo.newBuilder(bucket, filename)
                .setContentType("text/csv")
                .build();
        storage.create(blobInfo, content.getBytes());
    }

    /**
     * Creates an instance of GCS service
     *
     * @return Instance of GCS service
     * @throws IOException
     */
    private Storage getStorage() throws IOException {
        if (storage != null) {
            return storage;
        } else {
            Storage storage = StorageOptions.getDefaultInstance().getService();
            return storage;
        }
    }


}
