package cn.amatrix.DAO.HttpConnector;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import com.alibaba.fastjson2.JSON;
import java.util.HashMap;
import java.util.Map;

public class HttpConnector {
    private static final String BASE_URL = "http://localhost:1145/demo_webapp";
    // private static final String BASE_URL = "http://47.97.117.157:8080/demo_webapp";
    private final HttpClient httpClient;

    public HttpConnector() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public HttpResponse<String> sendRequest(String subPath, String type, String param) throws Exception {
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("type", type);
        requestBodyMap.put("param", JSON.parse(param));
        String requestBody = JSON.toJSONString(requestBodyMap);
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + subPath))
                .header("Content-Type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
