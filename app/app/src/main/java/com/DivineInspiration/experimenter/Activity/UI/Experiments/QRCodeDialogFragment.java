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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.DivineInspiration.experimenter.R;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class QRCodeDialogFragment extends DialogFragment {
    ImageView qrImage;
    String message;
    Bitmap bitmap;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // create view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.qr_code_dialog_fragment, null);

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
                        QRGSaver qrgSaver = new QRGSaver();
                        Log.e("message: ", message);
                        qrgSaver.save(Environment.getExternalStorageDirectory().getPath() + "/Experimenter/", message, bitmap, QRGContents.ImageType.IMAGE_PNG);
                    }
                })
                .setNegativeButton("Ok", null)
                .create();

        // on save click, save QR code
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // saves qr image
//                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "QR-code" , String.format("QR code for %s", message));
////                QRGSaver qrgSaver = new QRGSaver();
////                qrgSaver.save("", message, qrgEncoder.getBitmap(), QRGContents.ImageType.IMAGE_JPEG);
//                dialog.dismiss();
//            }
//        });

        dialog.show();

        return dialog;
    }
}
