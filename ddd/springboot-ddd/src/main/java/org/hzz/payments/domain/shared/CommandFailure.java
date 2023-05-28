package org.hzz.payments.domain.shared;

import org.hzz.payments.infrastructure.util.i18n.I18nCode;

import java.util.Set;

public class CommandFailure {
    public final Set<I18nCode> codes;

    public CommandFailure(Set<I18nCode> codes) {
        this.codes = codes;
    }
}
