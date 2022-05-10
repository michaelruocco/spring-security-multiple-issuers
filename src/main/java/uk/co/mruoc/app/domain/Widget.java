package uk.co.mruoc.app.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Data
public class Widget {

    private final long id;
    private final String description;
}
