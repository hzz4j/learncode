package org.hzz.ddd.interfaces.tracking;

import org.apache.commons.lang3.ThreadUtils;
import org.hzz.ddd.domain.model.handling.HandlingEvent;
import org.hzz.ddd.domain.model.cargo.Cargo;
import org.hzz.ddd.domain.model.cargo.CargoRepository;
import org.hzz.ddd.domain.model.cargo.TrackingId;
import org.hzz.ddd.domain.model.handling.HandlingEventRepository;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Controller for tracking cargo. This interface sits immediately on top of the
 * domain layer, unlike the booking interface which has a a remote facade and supporting
 * DTOs in between.
 * <p>
 * An adapter class, designed for the tracking use case, is used to wrap the domain model
 * to make it easier to work with in a web page rendering context. We do not want to apply
 * view rendering constraints to the design of our domain model, and the adapter
 * helps us shield the domain model classes.
 * <p>
 */
@Controller
@RequestMapping("/track")
public class CargoTrackingController {


    private final CargoRepository cargoRepository;
    private final HandlingEventRepository handlingEventRepository;
    private final MessageSource messageSource;
    public CargoTrackingController(CargoRepository cargoRepository,
                                   HandlingEventRepository handlingEventRepository,
                                   MessageSource messageSource){
        this.cargoRepository = cargoRepository;
        this.handlingEventRepository = handlingEventRepository;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String get(final ModelMap model) {
        Collection<Thread> allThreads = ThreadUtils.getAllThreads();
        model.put("trackCommand", new TrackCommand()); // TODO why is this method adding a TrackCommand without id?
        return "track";
    }

    @PostMapping
    protected String onSubmit(final HttpServletRequest request,
                              final TrackCommand command,
                              final ModelMap model,
                              final BindingResult bindingResult) {
        new TrackCommandValidator().validate(command, bindingResult);

        final TrackingId trackingId = new TrackingId(command.getTrackingId());
        final Cargo cargo = cargoRepository.find(trackingId);

        if (cargo != null) {
            final Locale locale = RequestContextUtils.getLocale(request);
            final List<HandlingEvent> handlingEvents = handlingEventRepository.lookupHandlingHistoryOfCargo(trackingId).distinctEventsByCompletionTime();
            model.put("cargo", new CargoTrackingViewAdapter(cargo, messageSource, locale, handlingEvents));
        } else {
            bindingResult.rejectValue("trackingId", "cargo.unknown_id", new Object[]{command.getTrackingId()}, "Unknown tracking id");
        }
        return "track";
    }
}
