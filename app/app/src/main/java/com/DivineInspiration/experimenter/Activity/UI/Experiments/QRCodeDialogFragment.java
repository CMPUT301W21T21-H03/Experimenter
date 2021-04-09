package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.DivineInspiration.experimenter.R;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class QRCodeDialogFragment extends DialogFragment {
    ImageView qrImage;
    String message;
    Bitmap bitmap;
    TextView fileName;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // create view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.qr_code_dialog_fragment, null);
        fileName = view.findViewById(R.id.qrFileName);
        qrImage = view.findViewById(R.id.qrImage);

        // set the string message
        message = getArguments().getString("message");
        // factory method + generate
        QRFactory qrFactory = new QRFactory();
        QRGEncoder qrgEncoder = qrFactory.generate(message);

        try {
            // Getting QR-Code as Bitmap
            bitmap = qrgEncoder.getBitmap();
            // Setting Bitmap to ImageView
            qrImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.v("Error Render", e.toString());
        }

        // create the dialog with a save and an ok button
        AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.dialogColor)
                .setView(view)
                .setMessage("QR Code")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Log.e("QR",  "Saved message - " + message);

                        try {
                            String name = fileName.getText().toString();
                            qrFactory.saveImage(getContext(), qrgEncoder.getBitmap(), name.equals("")? LocalDateTime.now().toString():name);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();


        dialog.show();

        return dialog;
    }
}
