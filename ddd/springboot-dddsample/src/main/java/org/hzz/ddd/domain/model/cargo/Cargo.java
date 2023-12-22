package org.hzz.ddd.domain.model.cargo;


import org.apache.commons.lang3.Validate;
import org.hzz.ddd.domain.model.handling.HandlingHistory;
import org.hzz.ddd.domain.model.location.Location;
import org.hzz.ddd.domain.shared.Entity;

import javax.persistence.*;
import java.util.List;

@javax.persistence.Entity(name="Cargo")
@Table(name = "Cargo")
public class Cargo implements Entity<Cargo> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(name = "tracking_id", unique = true)
    public String trackingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_id")
    public Location origin;

    @Embedded
    public RouteSpecification routeSpecification;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cargo_id")
    public List<Leg> itinerary; // TODO figure out if we can map an Itinerary object instead

    @Embedded
    public Delivery delivery;

    public Cargo() {
        // Needed by Hibernate
    }

    public Cargo(final TrackingId trackingId, final RouteSpecification routeSpecification) {
        Validate.notNull(trackingId, "Tracking ID is required");
        Validate.notNull(routeSpecification, "Route specification is required");

        this.trackingId = trackingId.idString();
        // Cargo origin never changes, even if the route specification changes.
        // However, at creation, cargo origin can be derived from the initial route specification.
        this.origin = routeSpecification.origin();
        this.routeSpecification = routeSpecification;

        this.delivery = Delivery.derivedFrom(
                this.routeSpecification, null, HandlingHistory.EMPTY
        );
    }

    public Cargo(TrackingId trackingId, RouteSpecification routeSpecification, Itinerary itinerary) {
        Validate.notNull(trackingId, "Tracking ID is required");
        Validate.notNull(routeSpecification, "Route specification is required");
        this.trackingId = trackingId.idString();
        this.origin = routeSpecification.origin();
        this.routeSpecification = routeSpecification;
        this.itinerary = itinerary.legs();

        this.delivery = Delivery.derivedFrom(
                this.routeSpecification, new Itinerary(this.itinerary), HandlingHistory.EMPTY
        );
    }

    /**
     * The tracking id is the identity of this entity, and is unique.
     *
     * @return Tracking id.
     */
    public TrackingId trackingId() {
        return new TrackingId(trackingId);
    }

    /**
     * @return Origin location.
     */
    public Location origin() {
        return origin;
    }

    /**
     * @return The delivery. Never null.
     */
    public Delivery delivery() {
        return delivery;
    }

    /**
     * @return The itinerary. Never null.
     */
    public Itinerary itinerary() {
        if (itinerary == null || itinerary.isEmpty()) {
            return Itinerary.EMPTY_ITINERARY;
        }
        return new Itinerary(itinerary);
    }

    /**
     * @return The route specification.
     */
    public RouteSpecification routeSpecification() {
        return routeSpecification;
    }

    /**
     * Specifies a new route for this cargo.
     *
     * @param routeSpecification route specification.
     */
    public void specifyNewRoute(final RouteSpecification routeSpecification) {
        Validate.notNull(routeSpecification, "Route specification is required");

        this.routeSpecification = routeSpecification;
        Itinerary itineraryForRouting = this.itinerary != null && !this.itinerary.isEmpty() ? new Itinerary(this.itinerary) : null;
        // Handling consistency within the Cargo aggregate synchronously
        this.delivery = delivery.updateOnRouting(this.routeSpecification, itineraryForRouting);
    }

    /**
     * Attach a new itinerary to this cargo.
     *
     * @param itinerary an itinerary. May not be null.
     */
    public void assignToRoute(final Itinerary itinerary) {
        Validate.notNull(itinerary, "Itinerary is required for assignment");

        this.itinerary = itinerary.legs();
        // Handling consistency within the Cargo aggregate synchronously
        this.delivery = delivery.updateOnRouting(this.routeSpecification, itinerary);
    }

    /**
     * Updates all aspects of the cargo aggregate status
     * based on the current route specification, itinerary and handling of the cargo.
     * <p/>
     * When either of those three changes, i.e. when a new route is specified for the cargo,
     * the cargo is assigned to a route or when the cargo is handled, the status must be
     * re-calculated.
     * <p/>
     * {@link RouteSpecification} and {@link Itinerary} are both inside the Cargo
     * aggregate, so changes to them cause the status to be updated <b>synchronously</b>,
     * but changes to the delivery history (when a cargo is handled) cause the status update
     * to happen <b>asynchronously</b> since {@link se.citerus.dddsample.domain.model.handling.HandlingEvent} is in a different aggregate.
     *
     * @param handlingHistory handling history
     */
    public void deriveDeliveryProgress(final HandlingHistory handlingHistory) {
        // Delivery is a value object, so we can simply discard the old one
        // and replace it with a new
        this.delivery = Delivery.derivedFrom(routeSpecification(), itinerary(), handlingHistory.filterOnCargo(new TrackingId(this.trackingId)));
    }

    @Override
    public boolean sameIdentityAs(final Cargo other) {
        return other != null && trackingId.equals(other.trackingId);
    }

    /**
     * @param object to compare
     * @return True if they have the same identity
     * @see #sameIdentityAs(Cargo)
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        final Cargo other = (Cargo) object;
        return sameIdentityAs(other);
    }

    /**
     * @return Hash code of tracking id.
     */
    @Override
    public int hashCode() {
        return trackingId.hashCode();
    }

    @Override
    public String toString() {
        return trackingId;
    }


}
