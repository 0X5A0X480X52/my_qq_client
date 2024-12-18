package cn.amatrix.utils.configManager.managers;

import cn.amatrix.utils.configManager.ConfigManager;

public class HttpConfigManager  {

    private static final String DEFALT_BASE_URL = "http://localhost:8080/demo_webapp";

    public static String getBaseURL() {
        String baseUrl = ConfigManager.getProperty("http.base_url");
        if (baseUrl == null) {
            System.out.println("http.base_url is null");
            baseUrl = DEFALT_BASE_URL;
            System.out.println("http.base_url is set to default: " + baseUrl);
        }

        return baseUrl;
    }
    
}
