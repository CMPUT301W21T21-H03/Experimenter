package com.DivineInspiration.experimenter.Activity.UI.QRBarCode;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.DivineInspiration.experimenter.R;

import java.io.IOException;
import java.time.LocalDateTime;

import androidmads.library.qrgenearator.QRGEncoder;


public class QRCodeDialogFragment extends DialogFragment {

    private ImageView qrImage;
    private String message;
    private Bitmap bitmap;
    private TextView fileName;

    /**
     * When creating the QR dialog fragment
     * @param savedInstanceState
     * @return
     * dialog itself
     */
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


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                WindowMetrics windowMetrics = getActivity().getWindowManager().getCurrentWindowMetrics();
                Insets insets = windowMetrics.getWindowInsets()
                        .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());

                qrImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,(int)(( windowMetrics.getBounds().height()) * 0.4)));
            } else {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                qrImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,(int)(displayMetrics.heightPixels * 0.4)));
            }

            Log.d("Testing","" + bitmap.getHeight());

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

                        Log.e("QR", "Saved message - " + message);

                        try {
                            String name = fileName.getText().toString();
                            name = name.equals("") ? LocalDateTime.now().toString() : name;
                            if (qrFactory.saveImage(getContext(), qrgEncoder.getBitmap(), name)) {
                                Toast.makeText(getContext(), "QR code saved as " + name + ".png", Toast.LENGTH_LONG).show();
                            }
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
