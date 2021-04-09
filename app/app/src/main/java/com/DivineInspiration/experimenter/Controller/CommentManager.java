package com.DivineInspiration.experimenter.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.DivineInspiration.experimenter.Model.Comment.Comment;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class talks to the Firestore database
 * in order to store and retrieve experiment's comment data.
 * The class uses singleton pattern.
 */
public class CommentManager {
    public static CommentManager singleton;         // Singleton object
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "CommentManager";

    /**
     * Interface definition for a callback to be invoked when {@link CommentManager} successfully
     * queries a list of {@link Comment} from Firestore
     */
    public interface OnCommentsReadyListener {

        /**
         * Called when {@link CommentManager} successfully queries a list of {@link Comment}
         * from Firestore
         * @param comments
         * The queried experiments
         */
        void onCommentsReady(List<Comment> comments);
    }

    /**
     * When reply comment data is retrieved from database is ready,
     * it is passed along as a parameter by the interface method.
     */
    public interface OnRepliesReadyListener {
        void onRepliesReady(List<Comment> replies, String commentID);
    }

    /**
     * Get singleton instance of the class
     * @return singleton Manager
     */
    public static CommentManager getInstance() {
        if (singleton == null) {
            singleton = new CommentManager();
        }
        return singleton;
    }

    /**
     * Delete all comments of a given experiment from the database
     * @param experimentId the experiment the comments belongs to
     * @return: void
     */
    public void deleteAllCommentOfExperiment(String experimentId){
        db.collection("Comments").document(experimentId).collection("Comments").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                int i = 0;
                int size = task.getResult().size();
                for(QueryDocumentSnapshot snapshot: task.getResult()){
                    Task<Void> del = snapshot.getReference().delete();
                    i++;
                    if (i == size) {
                        del.addOnCompleteListener(delTask->{
                            db.collection("Comments").document(experimentId).delete();
                        });
                    }
                }
            }
        });
    }

    /**
     * Adds a new comment to the database
     * @param comment comment we want to add
     * @param experimentID the experiment the comment belongs to
     * @return void
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
        doc.put("isReply", comment.isReply());
        doc.put("hasReply", comment.getHasReplies());

        // Put the Map object in the database
        db.collection("Comments")
                .document(experimentID)
                .collection("Comments")
                .document(comment.getCommentId())
                .set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
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
     * @param reply comment we want to add
     * @param commentID the comment the reply belongs to
     * @param experimentID the experiment the comment belongs to
     * @return void
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
        doc.put("isReply", reply.isReply());
        doc.put("hasReply", reply.getHasReplies());

        // Put the Map object in the database
        db.collection("Comments")
                .document(experimentID)
                .collection("Comments")
                .document(commentID)
                .collection("Replies")
                .document(reply.getCommentId())
                .set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
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

        db.collection("Comments")
                .document(experimentID)
                .collection("Comments")
                .document(commentID)
                .update("hasReply", true)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, commentID + " hasReply field updated successfully");
                        }
                        else {
                            Log.d(TAG, commentID + " hasReply field update failed");
                            throw new IllegalAccessError(commentID + " failed to update hasReply after adding reply " + reply.getCommentId());
                        }
                    }
                });
    }

    /**
     * Deletes an existing comment from the database.
     * @param commentID comment we want to delete
     * @param experimentID the experiment the comment belongs to
     * @return void
     */
    public void removeComment (String commentID, String experimentID) {
        // TODO: Recursive delete

        db.collection("Comments")
                .document(experimentID)
                .collection("Comments")
                .document(commentID)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
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
     * Deletes an existing reply from the database. (TO BE IMPLEMENTED for a future version)
     * @param replyID reply we want to delete
     * @param commentID comment we want to add
     * @param experimentID The experiment the comment belongs to
     * @return void
     */
    public void removeReply (String replyID, String commentID, String experimentID) {
        // TODO check is last reply removed
    }

    /**
     * Queries all the comments for a given experiment.
     * @param experimentID The experiment the comment belongs to
     * @param callback the class to call after the operation is done.
     *         The data is passed as a parameter of this method.
     * @return void
     */
    public void getExperimentComments (String experimentID, OnCommentsReadyListener callback) {

        db.collection("Comments")
                .document(experimentID)
                .collection("Comments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
     * @param commentID comment we want to get replies for
     * @param experimentID  the experiment the comment belongs to
     * @param callback The class to call after the operation is done.
     *         The data is passed as a parameter of this method.
     * @return void
     */
    public void getCommentReplies (String commentID, String experimentID, OnRepliesReadyListener callback) {

        db.collection("Comments")
                .document(experimentID)
                .collection("Comments")
                .document(commentID)
                .collection("Replies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    List<Comment> output = new ArrayList<>();
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        output.add(commentFromSnapshot(snapshot));
                    }
                    Log.d(TAG, commentID + " reply retrieval succeeded");
                    callback.onRepliesReady(output, commentID);
                }
                else {
                    Log.d(TAG, commentID + " reply retrieval failed");
                    callback.onRepliesReady(null, commentID);
                }
            }
        });
    }

    /**
     * This method returns a Comment object by constructing it using the data from the document snapshot
     * @param snapshot The Firestore document to retrieve the comment details from
     * @return Constructed using info from document
     */
    private Comment commentFromSnapshot(QueryDocumentSnapshot snapshot) {
        return new Comment (
                snapshot.getString("CommentID"),
                snapshot.getString("CommenterID"),
                snapshot.getString("CommenterName"),
                snapshot.getDate("Date"),
                snapshot.getString("Comment"),
                snapshot.getBoolean("isReply"),
                snapshot.getBoolean("hasReply")
        );
    }
}