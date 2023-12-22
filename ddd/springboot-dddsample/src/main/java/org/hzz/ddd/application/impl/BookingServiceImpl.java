package org.hzz.ddd.application.impl;

import lombok.extern.slf4j.Slf4j;
import org.hzz.ddd.application.BookingService;
import org.hzz.ddd.domain.model.cargo.CargoFactory;
import org.hzz.ddd.domain.model.cargo.CargoRepository;
import org.hzz.ddd.domain.model.cargo.Itinerary;
import org.hzz.ddd.domain.model.cargo.TrackingId;
import org.hzz.ddd.domain.model.location.LocationRepository;
import org.hzz.ddd.domain.model.location.UnLocode;
import org.hzz.ddd.domain.service.RoutingService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    private final CargoRepository cargoRepository;
    private final LocationRepository locationRepository;
    private final RoutingService routingService;
    private final CargoFactory cargoFactory;

    public BookingServiceImpl(final CargoRepository cargoRepository,
                              final LocationRepository locationRepository,
                              final RoutingService routingService,
                              final CargoFactory cargoFactory) {
        this.cargoRepository = cargoRepository;
        this.locationRepository = locationRepository;
        this.routingService = routingService;
        this.cargoFactory = cargoFactory;
    }

    @Override
    public TrackingId bookNewCargo(UnLocode origin, UnLocode destination, Instant arrivalDeadline) {
        return null;
    }

    @Override
    public List<Itinerary> requestPossibleRoutesForCargo(TrackingId trackingId) {
        return null;
    }

    @Override
    public void assignCargoToRoute(Itinerary itinerary, TrackingId trackingId) {

    }

    @Override
    public void changeDestination(TrackingId trackingId, UnLocode unLocode) {

    }
}
