package io.github.kimmking.gateway.router;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class RoundEndpointRouter implements HttpEndpointRouter {
    private static final AtomicLong CURRENT = new AtomicLong(0);

    @Override
    public String route(List<String> endpoints) {
        int size = endpoints.size();
        if (size < 2) {
            return endpoints.get(0);
        }

        long nextIndex = CURRENT.incrementAndGet() % size;
        return endpoints.get((int) nextIndex);
    }
}
