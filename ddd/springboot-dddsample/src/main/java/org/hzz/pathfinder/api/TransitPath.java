package org.hzz.pathfinder.api;

import java.util.Collections;
import java.util.List;

public class TransitPath {
    private final List<TransitEdge> transitEdges;

    /**
     * Constructor.
     *
     * @param transitEdges The legs for this itinerary.
     */
    public TransitPath(final List<TransitEdge> transitEdges) {
        this.transitEdges = transitEdges;
    }

    /**
     * @return An unmodifiable list DTOs.
     */
    public List<TransitEdge> getTransitEdges() {
        return Collections.unmodifiableList(transitEdges);
    }

}
