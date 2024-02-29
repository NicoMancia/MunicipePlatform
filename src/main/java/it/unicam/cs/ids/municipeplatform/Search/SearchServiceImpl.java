package it.unicam.cs.ids.municipeplatform.Search;

import it.unicam.cs.ids.municipeplatform.Content.ContentEntity;
import it.unicam.cs.ids.municipeplatform.Content.ContentRepository;
import it.unicam.cs.ids.municipeplatform.Contest.ContestEntity;
import it.unicam.cs.ids.municipeplatform.Contest.ContestRepository;
import it.unicam.cs.ids.municipeplatform.Event.EventCategory;
import it.unicam.cs.ids.municipeplatform.Event.EventEntity;
import it.unicam.cs.ids.municipeplatform.Event.EventRepository;
import it.unicam.cs.ids.municipeplatform.Itinerary.ItineraryEntity;
import it.unicam.cs.ids.municipeplatform.Itinerary.ItineraryRepository;
import it.unicam.cs.ids.municipeplatform.POI.POIEntity;
import it.unicam.cs.ids.municipeplatform.POI.POIRepository;
import it.unicam.cs.ids.municipeplatform.POI.PoiCategory;
import it.unicam.cs.ids.municipeplatform.User.UserRepository;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {
    private final ContestRepository contestRepository;
    private final ItineraryRepository itineraryRepository;
    private final POIRepository pointOfInterestRepository;
    private final EventRepository eventRepository;
    private final ContentRepository contentRepository;
    private final UserRepository userRepository;

    public SearchServiceImpl(ContestRepository contestRepository,
                             ItineraryRepository itineraryRepository,
                             POIRepository pointOfInterestRepository,
                             EventRepository eventRepository,
                             ContentRepository contentRepository,  UserRepository userRepository) {

        this.contestRepository = contestRepository;
        this.itineraryRepository = itineraryRepository;
        this.pointOfInterestRepository = pointOfInterestRepository;
        this.eventRepository = eventRepository;
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
    }

    /**
     * Searches for contests by name, start date, end date, and type. Returns a list of contests matching the criteria.
     *
     * @param name The name of the contest to match.
     * @param startDate The start date of the contest to match.
     * @param endDate The end date of the contest to match.
     * @param type The type of the contest to match.
     * @return A list of contests that match the search criteria.
     */
    @Override
    public List<ContestEntity> searchContests(String name, Date startDate, Date endDate, String type) {
        return contestRepository.findByNameAndInitialDateBetweenAndType(name, startDate, endDate, type);
    }

    /**
     * Searches for content by name, description, creation date, and content type. Filters the results to match the content type if specified.
     *
     * @param name         The name of the content to match.
     * @param description  The description of the content to match.
     * @param creationDate The creation date of the content to match.
     * @param contentType  The type of content to match.
     * @return A list of content that matches the search criteria.
     */
    @Override
    public List<ContentEntity> searchContent(String name, String description, Date creationDate, String contentType) {
        List<ContentEntity> results = contentRepository.findByNameAndDescriptionAndCreationDate(name, description, creationDate);
        if (contentType != null && !contentType.isEmpty()) {
            return results.stream()
                    .filter(content -> {
                        try {
                            return contentType.equals(content.getContentType());
                        } catch (ExecutionControl.NotImplementedException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        }
        return results;
    }

    /**
     * Searches for itineraries by name, description, creation date, and difficulty level. Returns a list of itineraries matching the criteria.
     *
     * @param name The name of the itinerary to match.
     * @param description The description of the itinerary to match.
     * @param creationDate The creation date of the itinerary to match.
     * @return A list of itineraries that match the search criteria.
     */
    @Override
    public List<ItineraryEntity> searchItineraries(String name, String description, LocalDateTime creationDate) {
        return itineraryRepository.findByNameAndDescriptionAndCreationDate(
                name, description, creationDate);
    }

    /**
     * Searches for points of interest by name, description, category, and location. Returns a list of points of interest matching the criteria.
     *
     * @param name The name of the point of interest to match.
     * @param description The description of the point of interest to match.
     * @param category The category of the point of interest to match.
     * @return A list of points of interest that match the search criteria.
     */
    @Override
    public List<POIEntity> searchPointsOfInterest(String name, String description, PoiCategory category) {
        return pointOfInterestRepository.findByNameAndDescriptionAndCategory(
                name, description, category);
    }

    /**
     * Searches for events by name, description, start date, and end date. Returns a list of events matching the criteria.
     *
     * @param name The name of the event to match.
     * @param description The description of the event to match.
     * @param startDate The start date of the event to match.
     * @param endDate The end date of the event to match.
     * @return A list of events that match the search criteria.
     */
    @Override
    public List<EventEntity> searchEvents(String name, String description, EventCategory category, LocalDateTime startDate,LocalDateTime endDate) {
        return eventRepository.findEventsByNameAndDescriptionAndCategoryWithinDateRange(
                name, description, category, startDate, endDate);
    }
}