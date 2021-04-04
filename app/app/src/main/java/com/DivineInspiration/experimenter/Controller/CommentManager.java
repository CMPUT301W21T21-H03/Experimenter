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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentManager {

    // TODO What happens when a query tries to access a callback that no longer exists? We should probably handle this error.

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

        Map<String, Object> doc = new HashMap<>();
        doc.put("CommentID", comment.getCommentId());
        doc.put("CommenterID", comment.getCommenterId());
        doc.put("CommenterName", comment.getCommenterName());
        doc.put("ExperimentID", experimentID);
        doc.put("Date", comment.getDate());
        doc.put("Comment", comment.getComment());

        db.collection("Comments").document(experimentID).collection("Comments").document(comment.getCommentId()).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public void addReply (Comment reply, String commentID, String experimentID) {

        Map<String, Object> doc = new HashMap<>();
        doc.put("CommentID", reply.getCommentId());
        doc.put("CommenterID", reply.getCommenterId());
        doc.put("CommenterName", reply.getCommenterName());
        doc.put("ExperimentID", experimentID);
        doc.put("Date", reply.getDate());
        doc.put("Comment", reply.getComment());

        db.collection("Comments").document(experimentID).collection("Comments")
                .document(commentID).collection("Replies").document(reply.getCommenterId()).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Reply added to database");
                }
                else {
                    Log.d(TAG, "Reply failed to be added to database");
                }
            }
        });
    }

    // TODO Recursive delete

    public void removeComment (String commentID, String experimentID, OnCommentsReadyListener callback) {

        db.collection("Comments").document(experimentID).collection("Comments").document(commentID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "delete comment succeeded");
                }
                else {
                    Log.d(TAG, "delete comment failed");
                }
            }
        });
    }

    public void removeReply (String replyID, String commentID, String experimentID) {

        db.collection("Comments").document(experimentID).collection("Comments")
                .document(commentID).collection("Replies").document(replyID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "delete reply succeeded");
                }
                else {
                    Log.d(TAG, "delete reply failed");
                }
            }
        });
    }

    public void getExperimentComments (String experimentID, OnCommentsReadyListener callback) {

        db.collection("Comments").document(experimentID).collection("Comments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    Log.d(TAG, "Comment retrieval succeeded");
                    List<Comment> output = new ArrayList<>();
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        output.add(commentFromSnapshot(snapshot));
                    }
                    callback.onCommentsReady(output);
                }
                else {
                    Log.d(TAG, "Comment retrieval failed");
                    callback.onCommentsReady(null);
                }
            }
        });
    }

    public void getCommentReplies (String commentID, String experimentID, OnCommentsReadyListener callback) {

        db.collection("Comments").document(experimentID).collection("Comments")
                .document(experimentID).collection("Replies").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                    callback.onCommentsReady(null);
                }
            }
        });
    }

    private Comment commentFromSnapshot(QueryDocumentSnapshot snapshot) {

        return new Comment (
                snapshot.getString("CommentID"),
                snapshot.getString("CommenterID"),
                snapshot.getString("CommenterName"),
                snapshot.getDate("Date"),
                snapshot.getString("Comment")
        );
    }

}
