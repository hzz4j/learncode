package org.hzz.ddd.infrastructure.persistence.jpa;

import org.hzz.ddd.domain.model.cargo.Cargo;
import org.hzz.ddd.domain.model.cargo.CargoRepository;
import org.hzz.ddd.domain.model.cargo.TrackingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Hibernate implementation of CargoRepository.
 */
public interface CargoRepositoryJPA extends JpaRepository<Cargo,Long>, CargoRepository {

    @Override
    default Cargo find(TrackingId trackingId) {
        return findByTrackingId(trackingId.idString());
    }

    @Override
    default void store(Cargo cargo){
        save(cargo);
    }

    @Query("SELECT c FROM Cargo c WHERE c.trackingId = :trackingId")
    Cargo findByTrackingId(String trackingId);

    @Override
    default List<Cargo> getAll(){
        return findAll();
    }



    @Query(value = "SELECT UPPER(SUBSTRING(REPLACE(CAST(UUID() AS CHAR),'-',''),1,9)) AS id",
    nativeQuery = true)
    String nextTrackingIdString();

    @Override
    default TrackingId nextTrackingId(){
        return new TrackingId(nextTrackingIdString());
    }
}
