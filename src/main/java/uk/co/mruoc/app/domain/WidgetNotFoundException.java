package uk.co.mruoc.app.domain;

public class WidgetNotFoundException extends RuntimeException {

    public WidgetNotFoundException(long id) {
        super(Long.toString(id));
    }
}
