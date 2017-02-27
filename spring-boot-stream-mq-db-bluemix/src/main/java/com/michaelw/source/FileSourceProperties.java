package com.michaelw.source;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.AssertTrue;
import java.io.File;
import java.util.regex.Pattern;

/**
 * Created by michaelwang on 12/20/16.
 */
@ConfigurationProperties("file")
public class FileSourceProperties {

    private static final String DEFAULT_DIR_XXX = System.getProperty("java.io.tmpdir") +
            File.separator + "dataflow" + File.separator + "input";

    //private static final String DEFAULT_DIR = "/usr/local/demo/input";
    private static final String DEFAULT_DIR = "/mnt/nfs/mydata";
    
    /**
     * The directory to poll for new files.
     */
    private String directory = DEFAULT_DIR;

    /**
     * Set to true to include an AcceptOnceFileListFilter which prevents duplicates.
     */
    private boolean preventDuplicates = true;

    /**
     * A simple ant pattern to match files.
     */
    private String filenamePattern;

    /**
     * A regex pattern to match files.
     */
    private Pattern filenameRegex;

    public String getDirectory() {
        return this.directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public boolean isPreventDuplicates() {
        return this.preventDuplicates;
    }

    public void setPreventDuplicates(boolean preventDuplicates) {
        this.preventDuplicates = preventDuplicates;
    }

    public String getFilenamePattern() {
        return this.filenamePattern;
    }

    public void setFilenamePattern(String filenamePattern) {
        this.filenamePattern = filenamePattern;
    }

    public Pattern getFilenameRegex() {
        return this.filenameRegex;
    }

    public void setFilenameRegex(Pattern filenameRegex) {
        this.filenameRegex = filenameRegex;
    }

    @AssertTrue(message = "filenamePattern and filenameRegex are mutually exclusive")
    public boolean isExclusivePatterns() {
        return !(this.filenamePattern != null && this.filenameRegex != null);
    }

}
