package com.example.juangui.easystoreapp;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by Juan Gui on 23/02/2017.
 */

public class FBConnection {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseDatabase database=FirebaseDatabase.getInstance();


    public FirebaseDatabase getDatabase() {
        return database;
    }

    public void setDatabase(FirebaseDatabase database) {
        this.database = database;
    }

    public FirebaseStorage getStorage() {

        return storage;
    }

    public void setStorage(FirebaseStorage storage) {
        this.storage = storage;
    }
}
