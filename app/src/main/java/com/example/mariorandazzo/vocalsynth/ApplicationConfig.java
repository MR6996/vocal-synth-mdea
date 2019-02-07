package com.example.mariorandazzo.vocalsynth;

public  class ApplicationConfig {

    public final static int SR = 48000;
    public final static int blockSize = 128;
    public final static int SAMPLES_NUMBER = 15;
    public static final String RESULT_DIR = "samples";

    private static final ApplicationConfig ourInstance = new ApplicationConfig();

    private ApplicationConfig() {
    }

    static ApplicationConfig getInstance() {
        return ourInstance;
    }
}
