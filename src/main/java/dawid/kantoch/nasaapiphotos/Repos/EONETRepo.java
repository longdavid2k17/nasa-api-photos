package dawid.kantoch.nasaapiphotos.Repos;

import dawid.kantoch.nasaapiphotos.Models.EONETpackage.Event;
import dawid.kantoch.nasaapiphotos.Services.EONETService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EONETRepo
{
    private Logger log = LoggerFactory.getLogger(EONETRepo.class);
    private List<Event> eventList;
    private EONETService service;

    public EONETRepo(EONETService eonetService)
    {
        this.eventList = new ArrayList<>();
        this.service = eonetService;
    }

    public List<Event> getEvents(int limit) throws IOException
    {
        this.eventList = service.getData(limit);
        return eventList;
    }

    public List<Event> getEventList()
    {
        return eventList;
    }

    public void addEvent(Event event)
    {
        eventList.add(event);
    }
}
