package org.httpsknighthacks.knighthacksandroid.Tasks;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.httpsknighthacks.knighthacksandroid.Models.LiveUpdate;
import org.httpsknighthacks.knighthacksandroid.Resources.ListResponseListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class LiveUpdatesTask {

    public static final String TAG = LiveUpdatesTask.class.getSimpleName();
    public static final String UPDATES_COLLECTION = "live_updates";

    private WeakReference<Context> mContext;
    private ListResponseListener<LiveUpdate> mListResponseListener;

    private DatabaseReference mReference;
    public LiveUpdatesTask(Context context, ListResponseListener<LiveUpdate> listResponseListener) {
        this.mContext = new WeakReference<>(context);
        this.mListResponseListener = listResponseListener;

        mReference = FirebaseDatabase.getInstance().getReference();
    }

    public void retrieveUpdates() {
        showLoading();
        mReference.child(UPDATES_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<LiveUpdate> updates = new ArrayList<>();
                for (DataSnapshot liveUpdateDataSnapshot : dataSnapshot.getChildren()) {
                    LiveUpdate event = liveUpdateDataSnapshot.getValue(LiveUpdate.class);
                    updates.add(event);
                }
                mListResponseListener.onSuccess(updates);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mListResponseListener.onFailure();
            }
        });
    }

    private void showLoading() {
        mListResponseListener.onStart();
    }

    public Context getContext() {
        return mContext.get();
    }
}