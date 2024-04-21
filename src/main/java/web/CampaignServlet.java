package web;

import application.service.interfaces.ICampaignService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import common.dto.CampaignDto;
import common.factory.service.CampaignServiceFactory;
import common.factory.service.HttpResponseBuilderFactory;
import common.factory.util.GsonFactory;
import common.builder.interfaces.IHttpResponseBuilder;
import common.mapper.ICampaignMapper;
import common.web.filter.util.FilterManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "CampaignServlet", value = "/campaigns")
public class CampaignServlet extends HttpServlet {
    private final ICampaignService campaignService = CampaignServiceFactory.getInstance();
    private final IHttpResponseBuilder httpResponseBuilder = HttpResponseBuilderFactory.getInstance();
    private final ICampaignMapper mapper = ICampaignMapper.INSTANCE;
    private final Gson gson = GsonFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CampaignDto campaignDto = mapper.toRecord(this.campaignService.getLastCreatedCampaign());
        String responseToJson = this.gson.toJson(campaignDto);

        this.httpResponseBuilder.buildHttResponse(resp, responseToJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp);

        String username = (String) req.getSession().getAttribute("username");

        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonObject reqBodyToJsonObject = this.gson.fromJson(reqBody, JsonObject.class);
        JsonObject productNamesAndDiscountPercentagesToJsonObject = reqBodyToJsonObject.getAsJsonObject("productNamesAndDiscountPercentages");
        Type typeProductNamesAndDiscountPercentages = new TypeToken<Map<String, Double>>() {}.getType();
        Map<String, Double> productNamesAndDiscountPercentages = gson.fromJson(productNamesAndDiscountPercentagesToJsonObject, typeProductNamesAndDiscountPercentages);

        this.campaignService.startCampaign(username, productNamesAndDiscountPercentages);

        this.httpResponseBuilder.buildHttResponse(resp, "", HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp);

        String username = (String) req.getSession(false).getAttribute("username");
        this.campaignService.stopCurrentCampaign(username);

        this.httpResponseBuilder.buildHttResponse(resp, "");
    }
}