package com.example.mariorandazzo.vocalsynth.model;

public class Sample {

    private int frequency;
    private int gender;
    private float vowel;
    private int predictedVowel;

    public Sample(int frequency, int gender, float vowel, int predictedVowel) {
        this.frequency = frequency;
        this.gender = gender;
        this.vowel = vowel;
        this.predictedVowel = predictedVowel;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public float getVowel() {
        return vowel;
    }

    public void setVowel(float vowel) {
        this.vowel = vowel;
    }

    public int getPredictedVowel() {
        return predictedVowel;
    }

    public void setPredictedVowel(int predictedVowel) {
        this.predictedVowel = predictedVowel;
    }
}
