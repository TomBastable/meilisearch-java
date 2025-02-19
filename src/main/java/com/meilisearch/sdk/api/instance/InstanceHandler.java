package com.meilisearch.sdk.api.instance;

import com.meilisearch.sdk.ServiceTemplate;
import com.meilisearch.sdk.exceptions.MeiliSearchRuntimeException;
import com.meilisearch.sdk.http.factory.RequestFactory;
import com.meilisearch.sdk.http.request.HttpMethod;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InstanceHandler {
    private final ServiceTemplate serviceTemplate;
    private final RequestFactory requestFactory;

    public InstanceHandler(ServiceTemplate serviceTemplate, RequestFactory requestFactory) {
        this.serviceTemplate = serviceTemplate;
        this.requestFactory = requestFactory;
    }

    /** @return a map with health status of Meilisearch */
    public Map<String, String> health() {
        return serviceTemplate.execute(
                requestFactory.create(HttpMethod.GET, "/health", Collections.emptyMap(), null),
                HashMap.class);
    }

    /** @return true if everything is ok, false if Meilisearch is in maintenance mode */
    public boolean isHealthy() {
        try {
            this.health();
            return true;
        } catch (MeiliSearchRuntimeException e) {
            return false;
        }
    }

    /** @return a map with version information of Meilisearch */
    public Map<String, String> getVersion() {
        try {
            return serviceTemplate.execute(
                    requestFactory.create(HttpMethod.GET, "/version", Collections.emptyMap(), null),
                    HashMap.class,
                    String.class,
                    String.class);
        } catch (MeiliSearchRuntimeException e) {
            return Collections.emptyMap();
        }
    }

    public IndexStats getStats(String index) {
        String requestQuery = index + "/stats";
        return serviceTemplate.execute(
                requestFactory.create(HttpMethod.GET, requestQuery, Collections.emptyMap(), null),
                IndexStats.class);
    }

    public Stats getStats() {
        String requestQuery = "/stats";
        return serviceTemplate.execute(
                requestFactory.create(HttpMethod.GET, requestQuery, Collections.emptyMap(), null),
                Stats.class);
    }
}
