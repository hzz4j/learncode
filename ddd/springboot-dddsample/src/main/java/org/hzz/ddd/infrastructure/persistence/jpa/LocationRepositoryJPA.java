package org.hzz.ddd.infrastructure.persistence.jpa;

import org.hzz.ddd.domain.model.location.Location;
import org.hzz.ddd.domain.model.location.LocationRepository;
import org.hzz.ddd.domain.model.location.UnLocode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepositoryJPA extends JpaRepository<Location, Long>, LocationRepository {

    @Override
    default Location find(final UnLocode unLocode) {
        return findByUnLoCode(unLocode.idString());
    }

    @Query("select loc from Location loc where loc.unlocode = :unlocode")
    Location findByUnLoCode(String unlocode);


    @Override
    default List<Location> getAll() {
        return findAll();
    }

    @Override
    default Location store(Location location) {
        return save(location);
    }
}