package common.factory.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.adapter.GsonCampaignItemTypeAdapter;
import common.adapter.GsonLocalDateTimeTypeAdapter;
import common.adapter.GsonOrderItemTypeAdapter;
import data.domain.CampaignItem;
import data.domain.OrderItem;

import java.time.LocalDateTime;

public final class GsonFactory {
    private static Gson instance = null;

    private GsonFactory() {}

    public static Gson getInstance() {
        if(instance == null) instance = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeTypeAdapter())
                .registerTypeAdapter(OrderItem.class, new GsonOrderItemTypeAdapter())
                .registerTypeAdapter(CampaignItem.class, new GsonCampaignItemTypeAdapter())
                .create();

        return instance;
    }
}