package com.example.juangui.easystoreapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Juan Gui on 16/02/2017.
 */
public class HomeActivityTest {
    @Test
    public void onCreate() throws Exception {

    }

    @Test
    public void onCreateOptionsMenu() throws Exception {

    }

    @Test
    public void onOptionsItemSelected() throws Exception {
        SellProductsTest SPT = new SellProductsTest();
        SPT.onCreateView();
        assertTrue(false);
    }

}