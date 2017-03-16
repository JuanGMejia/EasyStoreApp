package com.example.juangui.easystoreapp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

/**
 * Created by Juan Gui on 23/02/2017.
 */

public class FBConnection{
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    StorageReference myRefStorage;

    public FBConnection() {
    }

    public void getDatabase(String referencia) {
        myRef=database.getReference(referencia);
    }

    public FirebaseStorage getStorage() {
        myRefStorage= storage.getReferenceFromUrl("gs://easystoreapp-84625.appspot.com");
        return storage;
    }

}
