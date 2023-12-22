package org.hzz.ddd.interfaces.booking.facade.internal;

import org.hzz.ddd.application.BookingService;
import org.hzz.ddd.domain.model.cargo.CargoRepository;
import org.hzz.ddd.domain.model.location.LocationRepository;
import org.hzz.ddd.domain.model.voyage.VoyageRepository;
import org.hzz.ddd.interfaces.booking.facade.BookingServiceFacade;
import org.hzz.ddd.interfaces.booking.facade.dto.CargoRoutingDTO;
import org.hzz.ddd.interfaces.booking.facade.dto.LocationDTO;
import org.hzz.ddd.interfaces.booking.facade.dto.RouteCandidateDTO;

import java.rmi.RemoteException;
import java.time.Instant;
import java.util.List;

public class BookingServiceFacadeImpl implements BookingServiceFacade {

    private BookingService bookingService;
    private LocationRepository locationRepository;
    private CargoRepository cargoRepository;
    private VoyageRepository voyageRepository;

    public BookingServiceFacadeImpl(BookingService bookingService, LocationRepository locationRepository,
                                    CargoRepository cargoRepository, VoyageRepository voyageRepository) {
        this.bookingService = bookingService;
        this.locationRepository = locationRepository;
        this.cargoRepository = cargoRepository;
        this.voyageRepository = voyageRepository;
    }

    @Override
    public String bookNewCargo(String origin, String destination, Instant arrivalDeadline) throws RemoteException {
        return null;
    }

    @Override
    public CargoRoutingDTO loadCargoForRouting(String trackingId) throws RemoteException {
        return null;
    }

    @Override
    public void assignCargoToRoute(String trackingId, RouteCandidateDTO route) throws RemoteException {

    }

    @Override
    public void changeDestination(String trackingId, String destinationUnLocode) throws RemoteException {

    }

    @Override
    public List<RouteCandidateDTO> requestPossibleRoutesForCargo(String trackingId) throws RemoteException {
        return null;
    }

    @Override
    public List<LocationDTO> listShippingLocations() throws RemoteException {
        return null;
    }

    @Override
    public List<CargoRoutingDTO> listAllCargos() throws RemoteException {
        return null;
    }
}
