package com.DivineInspiration.experimenter.Model;

public class BinomialTrial extends Trial{
    public int success;
    public int failure;

    public BinomialTrial(int success, int failure) {
        this.success = success;
        this.failure = failure;
    }
}
