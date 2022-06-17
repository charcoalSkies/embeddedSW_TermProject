package com.example.remotewindowcontroller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FireBaseAction {
    private static DatabaseReference mDatabase;
    private static String responseData;

    public static String getDivceName(EquipmentInform equipment_inform){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(equipment_inform.user_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.e("firebase", "Error getting data", task.getException());
                }else{
                    responseData = String.valueOf(task.getResult().getValue());
                    Log.d("firebase", responseData);
                }
            }
        });
        return responseData;
    }
}
