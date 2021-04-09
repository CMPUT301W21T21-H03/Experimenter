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

public class CreateReplyDialogFragment extends DialogFragment {

    private OnReplyCreatedListener callback;

    /**
     * Reply is created listener
     */
    public interface OnReplyCreatedListener {
        void onReplyAdded(Comment reply, String commentID);
    }

    /**
     * Constructor for when the reply dialog is created
     * @param callback
     * callback for when the reply is done created
     */
    public CreateReplyDialogFragment(OnReplyCreatedListener callback) {
        super();
        this.callback = callback;
    }

    /**
     * When the dialog is being created
     * @param savedInstanceState
     * bundle
     * @return
     * dialog itself
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.create_comment_dialog_fagment, null);
        AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.dialogColor)
                .setView(view)
                .setMessage("Create Reply")
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .create();

        EditText commentEditText = view.findViewById(R.id.create_comment_edit_text);

        dialog.show();
        // create reply
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String commentText = commentEditText.getText().toString();

                if (commentText.equalsIgnoreCase("")) {
                    commentEditText.setError("Enter a comment");
                }
                else {

                    Bundle args = getArguments();
                    Comment reply = new Comment(
                            IdGen.genCommentId(args.getString("experimentID")),
                            args.getString("commenterID"),
                            args.getString("commenterName"),
                            new Date(),
                            commentText,
                            true,
                            false
                    );

                    CommentManager.getInstance().addReply(reply, args.getString("commentID"), args.getString("experimentID"));
                    callback.onReplyAdded(reply, args.getString("commentID"));
                    dialog.dismiss();
                }
            }
        });

        return dialog;
    }
}
