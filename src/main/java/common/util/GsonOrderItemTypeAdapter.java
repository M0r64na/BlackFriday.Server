package common.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import data.model.entity.OrderItem;

import java.lang.reflect.Type;

public class GsonOrderItemTypeAdapter implements JsonSerializer<OrderItem> {
    @Override
    public JsonElement serialize(OrderItem orderItem, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject responseToJson = new JsonObject();
        responseToJson.addProperty("product", orderItem.getProduct().getId().toString());
        responseToJson.addProperty("productQuantity", orderItem.getProductQuantity());
        responseToJson.addProperty("id", orderItem.getId().toString());

        return responseToJson;
    }
}