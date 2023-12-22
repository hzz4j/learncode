package org.hzz.ddd.domain.model.location;

import org.apache.commons.lang3.Validate;
import org.hzz.ddd.domain.shared.Entity;

import javax.persistence.*;

@javax.persistence.Entity(name = "Location")
@Table(name = "Location")
public class Location implements Entity<Location> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(nullable = false, unique = true, updatable = false)
    public String unlocode;

    @Column(nullable = false)
    public String name;
    /**
     * Special Location object that marks an unknown location.
     */
    public static final Location UNKNOWN = new Location(
            new UnLocode("XXXXX"), "Unknown location"
    );

    /**
     * Package-level constructor, visible for test and sample data purposes.
     *
     * @param unLocode UN Locode
     * @param name     location name
     * @throws IllegalArgumentException if the UN Locode or name is null
     */
    public Location(final UnLocode unLocode, final String name) {
        Validate.notNull(unLocode);
        Validate.notNull(name);

        this.unlocode = unLocode.idString();
        this.name = name;
    }

    // Used by JPA
    public Location(String unloCode, String name) {
        this.unlocode = unloCode;
        this.name = name;
    }

    /**
     * @return UN Locode for this location.
     */
    public UnLocode unLocode() {
        return new UnLocode(unlocode);
    }

    /**
     * @return Actual name of this location, e.g. "Stockholm".
     */
    public String name() {
        return name;
    }

    /**
     * @param object to compare
     * @return Since this is an entiy this will be true iff UN locodes are equal.
     */
    @Override
    public boolean equals(final Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        return sameIdentityAs(other);
    }

    @Override
    public boolean sameIdentityAs(final Location other) {
        return this.unlocode.equals(other.unlocode);
    }

    /**
     * @return Hash code of UN locode.
     */
    @Override
    public int hashCode() {
        return unlocode.hashCode();
    }

    @Override
    public String toString() {
        return name + " [" + unlocode + "]";
    }

    Location() {
        // Needed by Hibernate
    }

    public void setId(long id) {
        this.id = id;
    }
}
