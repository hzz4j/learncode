package org.hzz.ddd.domain.service;

import org.hzz.ddd.domain.model.cargo.Itinerary;
import org.hzz.ddd.domain.model.cargo.RouteSpecification;

import java.util.List;

/**
 * Routing service.
 *
 */
public interface RoutingService {

    /**
     * @param routeSpecification route specification
     * @return A list of itineraries that satisfy the specification. May be an empty list if no route is found.
     */
    List<Itinerary> fetchRoutesForSpecification(RouteSpecification routeSpecification);

}
