package com.DivineInspiration.experimenter.Activity.UI.QRBarCode;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;
import java.io.OutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

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

    /**
     * Saves QR code into storage
     */
    public boolean saveImage(Context context, Bitmap bitmap, String fileName) throws IOException {
        /*
        thanks bud
        https://stackoverflow.com/a/63777157/12471420
         */
        // Save with location, value, bitmap returned and type of Image(JPG/PNG).
        OutputStream outputStream;

        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/"+"Experimenter");

        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        outputStream = resolver.openOutputStream(uri);

        boolean success = false;
        success =  bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        outputStream.flush();
        outputStream.close();

        return  success;
    }
}
