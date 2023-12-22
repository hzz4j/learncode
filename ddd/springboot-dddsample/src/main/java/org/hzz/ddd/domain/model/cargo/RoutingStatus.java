package org.hzz.ddd.domain.model.cargo;

import org.hzz.ddd.domain.shared.ValueObject;

/**
 * Routing status.
 */
public enum RoutingStatus implements ValueObject<RoutingStatus> {
    NOT_ROUTED, ROUTED, MISROUTED;

    @Override
    public boolean sameValueAs(final RoutingStatus other) {
        return this.equals(other);
    }

}
