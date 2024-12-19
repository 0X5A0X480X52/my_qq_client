package cn.amatrix.DAO.HttpConnector;

import cn.amatrix.utils.configManager.managers.HttpConfigManager;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import com.alibaba.fastjson2.JSONObject;

public class HttpConnector {
    private static final String BASE_URL = HttpConfigManager.getBaseURL();
    private final HttpClient httpClient;

    public HttpConnector() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public HttpResponse<String> sendRequest(String subPath, String type, String param) throws Exception {
        JSONObject requestBodyJson = new JSONObject();
        requestBodyJson.put("type", type);
        requestBodyJson.put("param", param);
        String requestBody = requestBodyJson.toJSONString();
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + subPath))
                .header("Content-Type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
