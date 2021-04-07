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

/**
 * This class talks to the Firestore database
 * in order to store and retrieve experiment's comment data.
 * The class uses singleton pattern.
 */
public class CommentManager {

    // TODO What happens when a query tries to access a callback that no longer exists? We should probably handle this error.

    public static CommentManager singleton;             // Singleton object
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "CommentManager";

    /**
     * When comment data is retrieved from database is ready,
     * it is passed along as a parameter by the interface method.
     * Utilized for:
     */
    public interface OnCommentsReadyListener {
        void onCommentsReady(List<Comment> comments);
    }

    /**
     * Empty constructor
     */
    public CommentManager() { }

    /**
     * Get singleton instance of the class
     * @return: singleton:CommentManager
     */
    public static CommentManager getInstance() {
        if (singleton == null) {
            singleton = new CommentManager();
        }
        return singleton;
    }

    /**
     * Adds a new comment to the database.
     * @param: comment:Comment (comment we want to add).
     * @param: experimentID:String (The experiment the comment belongs to).
     * @return: void
     */
    public void addComment(Comment comment, String experimentID) {
        // Construct the Map object
        Map<String, Object> doc = new HashMap<>();
        doc.put("CommentID", comment.getCommentId());
        doc.put("CommenterID", comment.getCommenterId());
        doc.put("CommenterName", comment.getCommenterName());
        doc.put("ExperimentID", experimentID);
        doc.put("Date", comment.getDate());
        doc.put("Comment", comment.getComment());

        // Put the Map object in the database
        db.collection("Comments").document(experimentID).collection("Comments").document(comment.getCommentId()).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Comment added to database");
                } else {
                    Log.d(TAG, "Comment failed to be added to database");
                }
            }
        });
    }

    /**
     * Adds a new reply to the database.
     * @param: reply:Comment (comment we want to add).
     * @param: commentID:Comment (the comment the reply belongs to).
     * @param: experimentID:String (The experiment the comment belongs to).
     * @return: void
     */
    public void addReply (Comment reply, String commentID, String experimentID) {
        // Construct the Map object
        Map<String, Object> doc = new HashMap<>();
        doc.put("CommentID", reply.getCommentId());
        doc.put("CommenterID", reply.getCommenterId());
        doc.put("CommenterName", reply.getCommenterName());
        doc.put("ExperimentID", experimentID);
        doc.put("Date", reply.getDate());
        doc.put("Comment", reply.getComment());

        // Put the Map object in the database
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
    /**
     * Deletes an existing comment from the database.
     * @param: comment:Comment (comment we want to delete).
     * @param: experimentID:String (The experiment the comment belongs to).
     * @return: void
     */
    public void removeComment (String commentID, String experimentID) {

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

    /**
     * Deletes an existing reply from the database.
     * @param: replyID:String (reply we want to delete).
     * @param: commentID:String (comment we want to add).
     * @param: experimentID:String (The experiment the comment belongs to).
     * @return: void
     */
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

    /**
     * Queries all the comments for a given experiment.
     * @param: experimentID:String (The experiment the comment belongs to).
     * @param: callback:OnCommentsReadyListener (The class to call after the operation is done).
     *         The data is passed as a parameter of this method.
     * @return: void
     */
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
                } else {
                    Log.d(TAG, "Comment retrieval failed");
                    callback.onCommentsReady(null);
                }
            }
        });
    }

    /**
     * Queries all the comments for a given experiment.
     * @param: commentID:String (comment we want to get replies for).
     * @param: experimentID:String (The experiment the comment belongs to).
     * @param: callback:OnCommentsReadyListener (The class to call after the operation is done).
     *         The data is passed as a parameter of this method.
     * @return: void
     */
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

    /**
     * This method returns a Comment object by constructing it using the data from the document snapshot.
     * @param: snapshot:QueryDocumentSnapshot (The Firestore document to retrieve the comment details from).
     * @return: :Comment (Constructed using info from document).
     */
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