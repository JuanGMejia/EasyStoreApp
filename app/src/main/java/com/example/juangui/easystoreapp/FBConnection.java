package com.example.juangui.easystoreapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Juan Gui on 23/02/2017.
 */

public class FBConnection {
    DatabaseReference myRef;
    StorageReference myRefStorage;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public FBConnection() {
    }

    public void getDatabase(String referencia) {
        myRef = database.getReference(referencia);
    }

    public FirebaseStorage getStorage() {
        myRefStorage = storage.getReferenceFromUrl("gs://easystoreapp-84625.appspot.com");
        return storage;
    }

}
