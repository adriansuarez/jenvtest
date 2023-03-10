package com.csviri.jenvtest;

import java.io.File;

public final class APIServerConfigBuilder {

    public static final String JENVTEST_DOWNLOAD_BINARIES = "JENVTEST_DOWNLOAD_BINARIES";
    public static final String JENVTEST_DIR_ENV_VAR = "JENVTEST_DIR";
    public static final String JENVTEST_API_SERVER_VERSION_ENV_VAR = "JENVTEST_API_SERVER_VERSION";

    public static final String DIRECTORY_NAME = ".jenvtest";

    private String jenvtestDir;
    private String apiServerVersion;
    private Boolean downloadBinaries;

    public APIServerConfigBuilder() {
    }

    public static APIServerConfigBuilder anAPIServerConfig() {
        return new APIServerConfigBuilder();
    }

    public APIServerConfigBuilder withJenvtestDir(String jenvtestDir) {
        this.jenvtestDir = jenvtestDir;
        return this;
    }

    public APIServerConfigBuilder withApiServerVersion(String apiServerVersion) {
        this.apiServerVersion = apiServerVersion;
        return this;
    }

    public APIServerConfigBuilder withDownloadBinaries(boolean downloadBinaries) {
        this.downloadBinaries = downloadBinaries;
        return this;
    }

    public APIServerConfig build() {
        if (jenvtestDir == null) {
            var jenvtestDirFromEnvVar = System.getenv(JENVTEST_DIR_ENV_VAR);
            if (jenvtestDirFromEnvVar != null) {
                this.jenvtestDir = jenvtestDirFromEnvVar;
            } else {
                this.jenvtestDir = new File(System.getProperty("user.home"), DIRECTORY_NAME).getPath();
            }
        }
        if (downloadBinaries == null) {
            var downloadBinariesEnvVal = System.getenv(JENVTEST_DOWNLOAD_BINARIES);
            if (downloadBinariesEnvVal != null) {
                this.downloadBinaries = Boolean.parseBoolean(downloadBinariesEnvVal);
            } else {
                this.downloadBinaries = true;
            }
        }
        if (apiServerVersion == null) {
            var apiServerVersionEnvVar = System.getenv(JENVTEST_DIR_ENV_VAR);
            if (apiServerVersionEnvVar != null) {
                this.apiServerVersion = apiServerVersionEnvVar;
            }
        }
        return new APIServerConfig(jenvtestDir, apiServerVersion, downloadBinaries);
    }
}
