package web;

import application.service.interfaces.ICampaignService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.dto.CampaignItemDto;
import common.factory.service.CampaignServiceFactory;
import common.factory.service.HttpResponseBuilderFactory;
import common.factory.util.GsonFactory;
import common.builder.interfaces.IHttpResponseBuilder;
import common.mapper.ICampaignItemMapper;
import common.web.filter.util.FilterManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CampaignServlet", value = "/campaigns")
public class CampaignServlet extends HttpServlet {
    private final ICampaignService campaignService = CampaignServiceFactory.getInstance();
    private final IHttpResponseBuilder httpResponseBuilder = HttpResponseBuilderFactory.getInstance();
    private final ICampaignItemMapper mapper = ICampaignItemMapper.INSTANCE;
    private final Gson gson = GsonFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CampaignItemDto> campaignItems = this.campaignService.getLastCreatedCampaign().getItems().stream().map(mapper::toRecord).toList();
        String responseToJson = this.gson.toJson(campaignItems);

        this.httpResponseBuilder.buildHttResponse(resp, responseToJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp);

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
        FilterManager.process(req, resp);

        String username = req.getParameter("username");
        this.campaignService.stopCurrentCampaign(username);

        this.httpResponseBuilder.buildHttResponse(resp, "");
    }
}