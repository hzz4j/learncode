package org.hzz.ddd.interfaces.tracking;

import org.apache.commons.lang3.ThreadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Map;

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
    @GetMapping
    public String get(final Map<String, Object> model) {
        Collection<Thread> allThreads = ThreadUtils.getAllThreads();
        model.put("trackCommand", new TrackCommand()); // TODO why is this method adding a TrackCommand without id?
        return "track";
    }
}
