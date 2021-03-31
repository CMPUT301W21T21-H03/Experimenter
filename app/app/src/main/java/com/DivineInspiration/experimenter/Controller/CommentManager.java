package com.DivineInspiration.experimenter.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.DivineInspiration.experimenter.Model.Comment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentManager {

    public static CommentManager singleton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "CommentManager";

    public interface OnCommentsReadyListener {
        void onCommentsReady(List<Comment> comments);
    }

    public CommentManager() { }

    public static CommentManager getInstance() {
        if (singleton == null) {
            singleton = new CommentManager();
        }
        return singleton;
    }

    public void addComment(Comment comment, String experimentID) {

        String collectionPath = "/Experiments/" + experimentID;
        Map<String, Object> doc = new HashMap<>();
        doc.put("CommentID", comment.getCommentId());
        doc.put("Commenter", comment.getCommentator());
        doc.put("ExperimentID", experimentID);
        doc.put("Date", comment.getDate());
        doc.put("Comment", comment.getComment());
        doc.put("Replies", comment.getReplies());

        db.collection(collectionPath).document(comment.getCommentId()).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Comment added to database");
                }
                else {
                    Log.d(TAG, "Comment failed to be added to database");
                }
            }
        });
    }

    public void addReply (Comment reply, String commentID) {

    }

    public void removeComment (String commentID) {


    }

    public void removeReply (String replyID) {

    }

    public void getExperimentComments (String experimentID, OnCommentsReadyListener callback) {

        String collectionPath = "/Experiments/" + experimentID;
        db.collection(collectionPath).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    List<Comment> output = new ArrayList<>();
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        output.add(commentFromSnapshot(snapshot));
                    }
                    callback.onCommentsReady(output);
                }
                else {
                    Log.d(TAG, "Comment retrieval failed");
                }
            }
        });
    }

    public void getCommentReplies (String commentID) {
    }

    private Comment commentFromSnapshot(QueryDocumentSnapshot snapshot) {
        return null;
    }

}
