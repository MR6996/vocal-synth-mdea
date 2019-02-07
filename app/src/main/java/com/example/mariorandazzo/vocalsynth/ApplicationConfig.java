package com.example.mariorandazzo.vocalsynth;

class ApplicationConfig {

    protected final static int SR = 48000;
    protected final static int blockSize = 128;
    protected final static int SAMPLES_NUMBER = 15;
    protected static final String FILE_NAME = "samples.csv";

    private static final ApplicationConfig ourInstance = new ApplicationConfig();

    private ApplicationConfig() {
    }

    static ApplicationConfig getInstance() {
        return ourInstance;
    }
}
