package common.factory.util;

import com.google.gson.Gson;

public final class GsonFactory {
    private static Gson instance = null;

    private GsonFactory() {}

    public static Gson getInstance() {
        if(instance == null) instance = new Gson();

        return instance;
    }
}