package org.hzz.ddd.infrastructure.persistence.jpa;

import org.hzz.ddd.domain.model.voyage.Voyage;
import org.hzz.ddd.domain.model.voyage.VoyageNumber;
import org.hzz.ddd.domain.model.voyage.VoyageRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Hibernate implementation of CarrierMovementRepository.
 */
public interface VoyageRepositoryJPA extends JpaRepository<Voyage, Long>, VoyageRepository {

    @Override
    default Voyage find(final VoyageNumber voyageNumber) {
        return findByVoyageNumber(voyageNumber.idString());
    }

    @Query("select v from Voyage v where v.voyageNumber = :voyageNumber")
    Voyage findByVoyageNumber(String voyageNumber);

    @Override
    default void store(Voyage voyage) {
        save(voyage);
    }
}