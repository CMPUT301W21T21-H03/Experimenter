package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class QRFactory {
    private String message;
    private Bitmap bitmap;

    public QRFactory() {
        // generates QR code from string
    }

    public QRGEncoder generate(String s) {
        // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
        QRGEncoder qrgEncoder = new QRGEncoder(s, null, QRGContents.Type.TEXT, 1000);
        qrgEncoder.setColorBlack(Color.BLACK);
        qrgEncoder.setColorWhite(Color.WHITE);

        return qrgEncoder;
    }

    public void saveImage() {
        // Save with location, value, bitmap returned and type of Image(JPG/PNG).
        QRGSaver qrgSaver = new QRGSaver();
        qrgSaver.save("./", message, bitmap, QRGContents.ImageType.IMAGE_JPEG);
    }
}
