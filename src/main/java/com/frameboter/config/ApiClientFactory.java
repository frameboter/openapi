package com.protocolofdivinity.httpclientuiservice.config;

import com.protocolofdivinity.auth.client.rest.ApiClient;

public final class ApiClientFactory {

    private ApiClientFactory() {}

    public static ApiClient createClient() {
        // Flexible priority order (override → env → system → default)
        String baseUrl =
                System.getProperty("API_BASE_URL",
                        System.getenv().getOrDefault("API_BASE_URL",
                                "http://localhost:8080")); // fallback

        ApiClient client = new ApiClient();
        client.setBasePath(baseUrl);

        return client;
    }

    public static <T> T createApi(Class<T> apiClass) {
        try {
            ApiClient client = createClient();
            return apiClass.getConstructor(ApiClient.class).newInstance(client);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create API client", e);
        }
    }
}