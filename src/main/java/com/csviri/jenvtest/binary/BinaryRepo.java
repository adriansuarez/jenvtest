package com.csviri.jenvtest.binary;

import com.csviri.jenvtest.JenvtestException;
import com.csviri.jenvtest.Utils;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BinaryRepo {

    private static final Logger log = LoggerFactory.getLogger(BinaryRepo.class);

    private static final String BUCKET_NAME = "kubebuilder-tools";

    private final OSInfoProvider osInfoProvider;

    public BinaryRepo(OSInfoProvider osInfoProvider) {
        this.osInfoProvider = osInfoProvider;
    }


    public File downloadVersionToTempFile(String version) {
        try {
            String url = "https://storage.googleapis.com/kubebuilder-tools/kubebuilder-tools-" + version +
                    "-" + osInfoProvider.getOSName() + "-" + osInfoProvider.getOSArch() + ".tar.gz";

            File tempFile = File.createTempFile("kubebuilder-tools-"+version, ".tar.gz");
            log.debug("Downloading binary from url: {} to Temp file: {}", url, tempFile.getPath());
            FileUtils.copyURLToFile(new URL(url), tempFile);
            return tempFile;
        } catch (IOException e) {
            throw new JenvtestException(e);
        }
    }


    public Stream<String> listObjectNames() {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        var blobs = storage.get(BUCKET_NAME).list();
        return StreamSupport.stream(blobs.iterateAll().spliterator(), false).map(b -> b.asBlobInfo().getName());
    }

}
