package net.gringrid.siso.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class SitterList {
    @SerializedName("group_first")
    public List<SitterListItem> group_first;

    @SerializedName("group_second")
    public List<SitterListItem> group_second;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
