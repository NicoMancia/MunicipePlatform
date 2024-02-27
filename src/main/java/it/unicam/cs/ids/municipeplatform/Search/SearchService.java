package it.unicam.cs.ids.municipeplatform.Search;

import it.unicam.cs.ids.municipeplatform.Content.ContentEntity;
import it.unicam.cs.ids.municipeplatform.Contest.ContestEntity;
import it.unicam.cs.ids.municipeplatform.Event.EventEntity;
import it.unicam.cs.ids.municipeplatform.Itinerary.ItineraryEntity;
import it.unicam.cs.ids.municipeplatform.Location;
import it.unicam.cs.ids.municipeplatform.POI.POIEntity;
import it.unicam.cs.ids.municipeplatform.POI.PoiCategory;

import java.util.Date;
import java.util.List;

public interface SearchService {
    List<ContestEntity> searchContests(String name, Date startDate, Date endDate, String type);
    List<ContentEntity> searchContent(String name, String description, Date creationDate, String contentType);
    List<ItineraryEntity> searchItineraries(String name, String description, Date creationDate);
    List<POIEntity> searchPointsOfInterest(String name, String description, PoiCategory category, Location location);
    List<EventEntity> searchEvents(String name, String description, Date startDate, Date endDate);
}