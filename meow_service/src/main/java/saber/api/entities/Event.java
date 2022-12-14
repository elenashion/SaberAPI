package saber.api.entities;

public class Event
{
    public enum EventType
    {
        MODE_CHANGED,
        SOMETHING_WRONG
        ;
    }

    private final EventType eventType;

    private final Object newParameter;

    private Integer eventsTurn;

    public Event(EventType eventType)
    {
        this(eventType, null);
    }

    public Event(EventType eventType, Object newParameter)
    {
        this.eventType = eventType;
        this.newParameter = newParameter;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Object getNewParameter()
    {
        return newParameter;
    }

    public void setEventsTurn(int turn)
    {
        this.eventsTurn = turn;
    }

    public Integer getEventsTurn()
    {
        return eventsTurn;
    }
}
