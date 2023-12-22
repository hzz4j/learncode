package org.hzz.ddd.infrastructure.persistence.jpa;

import org.hzz.ddd.domain.model.cargo.TrackingId;
import org.hzz.ddd.domain.model.handling.HandlingEvent;
import org.hzz.ddd.domain.model.handling.HandlingEventRepository;
import org.hzz.ddd.domain.model.handling.HandlingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Hibernate implementation of HandlingEventRepository.
 */
public interface HandlingEventRepositoryJPA extends JpaRepository<HandlingEvent, Long>, HandlingEventRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    default void store(HandlingEvent event){
        save(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default HandlingHistory lookupHandlingHistoryOfCargo(TrackingId trackingId){
        return new HandlingHistory(getHandlingHistoryOfCargo(trackingId.idString()));
    }

    @Query("select he from HandlingEvent he where he.cargo.trackingId = :trackingId and he.location != NULL")
    List<HandlingEvent> getHandlingHistoryOfCargo(String trackingId);
}
