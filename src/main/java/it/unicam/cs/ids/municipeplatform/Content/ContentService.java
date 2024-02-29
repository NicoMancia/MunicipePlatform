package it.unicam.cs.ids.municipeplatform.Content;
import it.unicam.cs.ids.municipeplatform.Event.EventEntity;
import it.unicam.cs.ids.municipeplatform.Itinerary.ItineraryEntity;
import it.unicam.cs.ids.municipeplatform.POI.POIEntity;

import java.util.List;

public interface ContentService {
    ItineraryEntity createNewItinerary(ItineraryEntity itinerary, List<Long> contents);

    POIEntity createNewPointOfInterest(POIEntity pointOfInterest);

    EventEntity createNewEvent(EventEntity event);

    ItineraryEntity getItinerary(Long id);

    POIEntity getPoi(Long id);

    EventEntity getEvent(Long id);

    ContentEntity getContent(Long id);

    List<EventEntity> getAllEvent();

    List<ItineraryEntity> getAllItinerary();

    List<POIEntity> getAllPoi();
    List<ContentEntity> getAllContent();

    List<ContentEntity> getAllPending();

    List<ContentEntity> getAllContentByUserId(Long userId);

    void updateEvent(EventEntity event);

    void updatePoi(POIEntity pointOfInterest);

    void updateItinerary(ItineraryEntity itinerary, List<Long> contents);

    void approveEvent(Long id);

    void approvePOI(Long id);

    void approveItinerary(Long id);

    boolean canUserApproveContent(Long contentId, Long userId);
}
