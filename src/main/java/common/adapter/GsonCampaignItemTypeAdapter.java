package common.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import data.domain.CampaignItem;

import java.lang.reflect.Type;

public class GsonCampaignItemTypeAdapter implements JsonSerializer<CampaignItem> {
    @Override
    public JsonElement serialize(CampaignItem campaignItem, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject responseToJson = new JsonObject();
        responseToJson.addProperty("product", campaignItem.getProduct().getId().toString());
        responseToJson.addProperty("discountPercentage", campaignItem.getDiscountPercentage());
        responseToJson.addProperty("id", campaignItem.getId().toString());

        return responseToJson;
    }
}