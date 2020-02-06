package huitx.libztframework.utils;

public class EventBusMessage<T> {
    public String eventTag;
    public T event;
    public boolean eventBoolean;

    public EventBusMessage(String eventTag, T event) {
        this.eventTag = eventTag;
        this.event = event;
    }

    public EventBusMessage(String eventTag, T event, boolean eventBoolean) {
        this.eventTag = eventTag;
        this.event = event;
        this.eventBoolean = eventBoolean;
    }
}
