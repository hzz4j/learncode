package org.hzz.ddd.domain.model.handling;

import org.hzz.ddd.domain.model.cargo.TrackingId;

/**
 * Handling event repository.
 */
public interface HandlingEventRepository {

    /**
     * Stores a (new) handling event.
     *
     * @param event handling event to save
     */
    void store(HandlingEvent event);


    /**
     * @param trackingId cargo tracking id
     * @return The handling history of this cargo
     */
    HandlingHistory lookupHandlingHistoryOfCargo(TrackingId trackingId);

}
