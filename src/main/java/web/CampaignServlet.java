package web;

import application.service.interfaces.ICampaignService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.factory.service.CampaignServiceFactory;
import common.factory.service.HttpResponseBuilderFactory;
import common.factory.util.GsonFactory;
import common.service.interfaces.IHttpResponseBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

@WebServlet(name = "CampaignServlet", value = "/campaigns")
public class CampaignServlet extends HttpServlet {
    private final ICampaignService campaignService = CampaignServiceFactory.getInstance();
    private final IHttpResponseBuilder httpResponseBuilder = HttpResponseBuilderFactory.getInstance();
    private final Gson gson = GsonFactory.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Type typeResponseToJson = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, Object> responseToJson = gson.fromJson(req.getReader(), typeResponseToJson);

        String username = (String) responseToJson.get("username");

        Type typeProductNamesAndDiscountPercentages = new TypeToken<Map<String, Double>>() {}.getType();
        Map<String, Double> productNamesAndDiscountPercentages = gson.fromJson(gson.toJson(responseToJson.get("productNamesAndDiscountPercentages")), typeProductNamesAndDiscountPercentages);

        this.campaignService.startCampaign(username, productNamesAndDiscountPercentages);

        this.httpResponseBuilder.buildHttResponse(resp, "", HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        this.campaignService.stopCurrentCampaign(username);

        this.httpResponseBuilder.buildHttResponse(resp, "", HttpServletResponse.SC_OK);
    }
}