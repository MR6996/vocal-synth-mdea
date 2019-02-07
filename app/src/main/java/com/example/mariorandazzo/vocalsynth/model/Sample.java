package com.example.mariorandazzo.vocalsynth.model;

public class Sample {

    private int r;
    private float theta;
    private int vowel;

    public Sample() {
    }

    public Sample(int r, float theta, int vowel) {
        this.r = r;
        this.theta = theta;
        this.vowel = vowel;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public float getTheta() {
        return theta;
    }

    public void setTheta(float theta) {
        this.theta = theta;
    }

    public int getVowel() {
        return vowel;
    }

    public void setVowel(int vowel) {
        this.vowel = vowel;
    }

}
