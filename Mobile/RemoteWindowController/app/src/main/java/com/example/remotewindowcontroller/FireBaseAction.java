package com.example.remotewindowcontroller;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FireBaseAction {
    private static DatabaseReference mDatabase;
    private static String responseData;

    public static String getDivceName(EquipmentInform equipment_inform_value){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(equipment_inform_value.user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EquipmentInform equipment_inform = snapshot.getValue(EquipmentInform.class);
                equipment_inform_value.equipment_name = equipment_inform.equipment_name;
                Log.i("name:", equipment_inform.equipment_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return equipment_inform_value.equipment_name;
    }
}
