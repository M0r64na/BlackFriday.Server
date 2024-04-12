package common.factory.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.util.GsonLocalDateTimeTypeAdapter;

import java.time.LocalDateTime;

public final class GsonFactory {
    private static Gson instance = null;

    private GsonFactory() {}

    public static Gson getInstance() {
        if(instance == null) instance = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeTypeAdapter())
                .create();

        return instance;
    }
}