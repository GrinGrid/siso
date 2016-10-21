package net.gringrid.siso.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class Parent {


    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
