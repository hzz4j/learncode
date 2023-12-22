package org.hzz.ddd.infrastructure.routing;

import lombok.extern.slf4j.Slf4j;
import org.hzz.ddd.domain.model.location.LocationRepository;
import org.hzz.ddd.domain.model.voyage.VoyageRepository;
import org.hzz.pathfinder.api.GraphTraversalService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExternalRoutingService {
    private final GraphTraversalService graphTraversalService;
    private final LocationRepository locationRepository;
    private final VoyageRepository voyageRepository;

    public ExternalRoutingService(GraphTraversalService graphTraversalService, LocationRepository locationRepository, VoyageRepository voyageRepository) {
        this.graphTraversalService = graphTraversalService;
        this.locationRepository = locationRepository;
        this.voyageRepository = voyageRepository;
    }
}
