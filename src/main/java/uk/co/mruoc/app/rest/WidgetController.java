package uk.co.mruoc.app.rest;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.mruoc.app.domain.Widget;
import uk.co.mruoc.app.domain.WidgetNotFoundException;

@RestController
@RequestMapping("/widgets")
@RequiredArgsConstructor
public class WidgetController {

    private final Map<Long, Widget> widgets;

    public WidgetController() {
        this(buildWidgets());
    }

    @GetMapping("/{id}")
    public Widget get(@PathVariable("id") long id) {
        return Optional.ofNullable(widgets.get(id)).orElseThrow(() -> new WidgetNotFoundException(id));
    }

    private static Map<Long, Widget> buildWidgets() {
        return Map.of(
                1L, new Widget(1, "first widget"),
                2L, new Widget(2, "second widget"));
    }
}
