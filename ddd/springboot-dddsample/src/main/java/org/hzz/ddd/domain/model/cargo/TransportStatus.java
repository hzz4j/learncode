package org.hzz.ddd.domain.model.cargo;

import org.hzz.ddd.domain.shared.ValueObject;

/**
 * Represents the different transport statuses for a cargo.
 */
public enum TransportStatus implements ValueObject<TransportStatus> {
    NOT_RECEIVED, IN_PORT, ONBOARD_CARRIER, CLAIMED, UNKNOWN;

    @Override
    public boolean sameValueAs(final TransportStatus other) {
        return this.equals(other);
    }
}