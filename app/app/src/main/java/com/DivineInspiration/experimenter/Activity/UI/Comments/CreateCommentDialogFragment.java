package com.DivineInspiration.experimenter.Activity.UI.Comments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.DivineInspiration.experimenter.Controller.CommentManager;
import com.DivineInspiration.experimenter.Model.Comment.Comment;
import com.DivineInspiration.experimenter.Model.IdGen;
import com.DivineInspiration.experimenter.R;

import java.util.Date;

public class CreateCommentDialogFragment extends DialogFragment {

    private OnCommentCreatedListener callback;

    /**
     * When the comment is created
     */
    public interface OnCommentCreatedListener {
        void onCommentAdded(Comment comment);
    }

    /**
     * Constructor for when the comment dialog fragment is created
     * @param callback
     * callback function for when the comment is done creating
     */
    public CreateCommentDialogFragment(OnCommentCreatedListener callback) {
        super();
        this.callback = callback;
    }

    /**
     * When creating the dialog
     * @param savedInstanceState
     * @return
     * the dialog itself
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.create_comment_dialog_fagment, null);
        AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.dialogColor)
                .setView(view)
                .setMessage("Create Comment")
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .create();

        EditText commentEditText = view.findViewById(R.id.create_comment_edit_text);

        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String commentText = commentEditText.getText().toString();

                if (commentText.equalsIgnoreCase("")) {
                    commentEditText.setError("Enter a comment");
                }
                else {

                    Bundle args = getArguments();
                    Comment comment = new Comment(
                            IdGen.genCommentId(args.getString("experimentID")),
                            args.getString("commenterID"),
                            args.getString("commenterName"),
                            new Date(),
                            commentText,
                            false,
                            false
                    );

                    CommentManager.getInstance().addComment(comment, args.getString("experimentID"));
                    callback.onCommentAdded(comment);
                    dialog.dismiss();
                }
            }
        });

        return dialog;
    }
}
