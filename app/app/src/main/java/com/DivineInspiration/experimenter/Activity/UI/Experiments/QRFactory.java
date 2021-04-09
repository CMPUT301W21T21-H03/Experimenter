package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class QRFactory {
    // message is the QR's string representation

    /**
     * Generates the QR code from a string and returns a QR encoder object from
     * https://github.com/androidmads/QRGenerator
     * @param s
     * a string that will be represented with the QR code/image
     * @return
     * the QR encoder object from https://github.com/androidmads/QRGenerator
     */
    public QRGEncoder generate(String s) {
        // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
        QRGEncoder qrgEncoder = new QRGEncoder(s, null, QRGContents.Type.TEXT, 1000);
        qrgEncoder.setColorBlack(Color.BLACK);
        qrgEncoder.setColorWhite(Color.WHITE);

        return qrgEncoder;
    }

//    /**
//     * Saves QR code into storage
//     */
//    public void saveImage(Context context, Bitmap bitmap) {
//        // Save with location, value, bitmap returned and type of Image(JPG/PNG).
//        QRGSaver qrgSaver = new QRGSaver();
//
////        Log.d("QR CODE", Boolean.toString(fileSaved));
//    }
}
