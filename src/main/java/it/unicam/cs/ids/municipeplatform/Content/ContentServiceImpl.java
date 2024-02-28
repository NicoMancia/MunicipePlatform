package it.unicam.cs.ids.municipeplatform.Content;
import it.unicam.cs.ids.municipeplatform.Event.EventEntity;
import it.unicam.cs.ids.municipeplatform.Event.EventRepository;
import it.unicam.cs.ids.municipeplatform.Itinerary.ItineraryEntity;
import it.unicam.cs.ids.municipeplatform.Itinerary.ItineraryRepository;
import it.unicam.cs.ids.municipeplatform.POI.POIEntity;
import it.unicam.cs.ids.municipeplatform.POI.POIRepository;
import it.unicam.cs.ids.municipeplatform.TownHall.TownHallRepository;
import it.unicam.cs.ids.municipeplatform.User.UserEntity;
import it.unicam.cs.ids.municipeplatform.User.UserRepository;

import it.unicam.cs.ids.municipeplatform.User.*;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ContentServiceImpl implements ContentService {
    private final ItineraryRepository itineraryRepository;
    private final POIRepository pointOfInterestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    public ContentServiceImpl(ItineraryRepository itineraryRepository,
                              POIRepository pointOfInterestRepository,
                              EventRepository eventRepository,
                              UserRepository userRepository, ContentRepository contentRepository) {

        this.itineraryRepository = itineraryRepository;
        this.pointOfInterestRepository = pointOfInterestRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
    }

    /*
     * Determines the default approval status for a given role within the platform. This method assigns an
     * {@code ApprovalStatus.ACCEPTED} status to roles with higher authority or specific functions
     * (PlatformManager, Animator, Curator, AuthorizedContributor) and assigns {@code ApprovalStatus.PENDING}
     * to all other roles.
     *
     * @param role The role of the user for whom the approval status is to be determined.
     * @return The default {@link ApprovalStatus} based on the user's role; either {@code ACCEPTED} for
     * specific roles or {@code PENDING} for others.
     */
    private StateContent genericApprovalDecision(UserRole role) {
        return switch (role) {
            case PLATFORM_MANAGER, ANIMATOR, CURATOR, AUTHORIZED_CONTRIBUTOR -> StateContent.ACCEPTED;
            default -> StateContent.PENDING;
        };
    }

    /*
     * Retrieves the default approval status for a user based on their role within a specific town hall. This method
     * first verifies the existence of the user and town hall by their IDs. It then checks the user's roles in the
     * specified town hall and determines the approval status using {@code genericApprovalDecision} method. If the user
     * has a role associated with the town hall, their default approval status (ACCEPTED or PENDING) is returned; otherwise,
     * an empty Optional is returned.
     *
     * @param id The ID of the user.
     * @param townHallId The ID of the town hall.
     * @return An {@link Optional} containing the {@link ApprovalStatus} if the user has a role in the town hall;
     * otherwise, empty.
     * @throws IllegalArgumentException if the user or town hall does not exist.
     */
    private Optional<StateContent> getDefaultApprovalStatusFromUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("| ERROR | User doesn't exist"));

        List<UserEntity> roles = userRepository.findTownHallRolesByUserId(id);

        for (UserEntity role : roles) {
            return Optional.of(genericApprovalDecision(role.getEnumUser()));
        }

        return Optional.empty();
    }

    /**
     * Creates and saves a new itinerary with the specified contents. It sets the creator and town hall based on their IDs,
     * adds each content by ID to the itinerary, and assigns an approval status based on the creator's role in the specified
     * town hall. Throws an exception if the itinerary is null, the creator or town hall doesn't exist, any content doesn't
     * exist, or if the user doesn't have a defined approval status in the town hall.
     *
     * @param itinerary The itinerary to be created.
     * @param contents A list of content IDs to be included in the itinerary.
     * @return The saved itinerary entity.
     * @throws IllegalArgumentException if the itinerary is null, the creator or town hall does not exist, any of the
     * specified content does not exist, or if the user does not have an approval status.
     */
    public ItineraryEntity createNewItinerary(ItineraryEntity itinerary,List<Long> contents) {
        if (itinerary == null) {
            throw new IllegalArgumentException("| ERROR | Itinerary is NULL");
        }

        itinerary.setCreator(userRepository.findById(itinerary.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("| ERROR | Creator doesn't exist")));

        for (Long id : contents)
        {
            ContentEntity content = contentRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("| ERROR | Content doesn't exist"));
            itinerary.getContents().add(content);
        }

        itinerary.setStatus(getDefaultApprovalStatusFromUser(itinerary.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("| ERROR | User doesn't have an approval status")));

        return itineraryRepository.save(itinerary);
    }

    /**
     * Creates and saves a new point of interest (POI). Validates and sets the creator and town hall by their IDs, and
     * determines the default approval status based on the creator's role within the specified town hall. Throws an
     * exception if the POI is null, if the creator or town hall does not exist, or if the creator lacks a defined
     * approval status in the town hall.
     *
     * @param pointOfInterest The point of interest to be created.
     * @return The saved point of interest entity.
     * @throws IllegalArgumentException if the point of interest is null, the creator or town hall does not exist, or
     * if the user does not have an approval status.
     */
    public POIEntity createNewPointOfInterest(POIEntity pointOfInterest) {
        if (pointOfInterest == null) {
            throw new IllegalArgumentException("| ERROR | PointOfInterest is NULL");
        }

        pointOfInterest.setCreator(userRepository.findById(pointOfInterest.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("| ERROR | Creator doesn't exist")));

        pointOfInterest.setStatus(getDefaultApprovalStatusFromUser(pointOfInterest.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("| ERROR | User doesn't have an approval status")));

        return pointOfInterestRepository.save(pointOfInterest);
    }

    /**
     * Creates and saves a new event. It validates and sets the event's creator and associated town hall by their IDs,
     * and assigns an approval status based on the creator's role in the specified town hall. If the event is null,
     * the creator or town hall does not exist, or the creator does not have a predefined approval status in the town hall,
     * an exception is thrown.
     *
     * @param event The event to be created.
     * @return The saved event entity.
     * @throws IllegalArgumentException if the event is null, the creator or
     * town hall does not exist, or if the user does not have an approval status.
     */
    public EventEntity createNewEvent(EventEntity event) {
        if (event == null) {
            throw new IllegalArgumentException("| ERROR | Event is NULL");
        }

        event.setCreator(userRepository.findById(event.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("| ERROR | Creator doesn't exist")));

        event.setStatus(getDefaultApprovalStatusFromUser(event.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("| ERROR | User doesn't have an approval status")));

        return eventRepository.save(event);
    }

    /**
     * Retrieves an itinerary by its ID. Throws NoSuchElementException if no itinerary is found.
     *
     * @param id The ID of the itinerary to retrieve.
     * @return The retrieved itinerary.
     */
    @Override
    public ItineraryEntity getItinerary(Long id)
    {
        return itineraryRepository.findById(id).orElseThrow();
    }

    /**
     * Retrieves a point of interest (POI) by its ID. Throws NoSuchElementException if no POI is found.
     *
     * @param id The ID of the point of interest to retrieve.
     * @return The retrieved point of interest.
     */
    @Override
    public POIEntity getPoi(Long id)
    {
        return pointOfInterestRepository.findById(id).orElseThrow();
    }

    /**
     * Retrieves an event by its ID. Throws NoSuchElementException if no event is found.
     *
     * @param id The ID of the event to retrieve.
     * @return The retrieved event.
     */
    @Override
    public EventEntity getEvent(Long id)
    {
        return eventRepository.findById(id).orElseThrow();
    }

    /**
     * Retrieves a content by its ID. Throws NoSuchElementException if no content is found.
     *
     * @param id The ID of the content to retrieve.
     * @return The retrieved content.
     */
    @Override
    public ContentEntity getContent(Long id) {
        return contentRepository.findById(id).orElseThrow();
    }

    /**
     * Retrieves all events available in the system.
     *
     * @return A list of all events.
     */
    public List<EventEntity> getAllEvent() {
        return StreamSupport.stream(eventRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves all itineraries available in the system.
     *
     * @return A list of all itineraries.
     */
    public List<ItineraryEntity> getAllItinerary() {
        return StreamSupport.stream(itineraryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all points of interest available in the system.
     *
     * @return A list of all points of interest.
     */
    public List<POIEntity> getAllPoi() {
        return StreamSupport.stream(pointOfInterestRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Updates the details of an existing event. Validates the event's existence, its creator, and town hall. Throws
     * an exception if the event is null, has already expired, or if the creator or town hall does not exist.
     *
     * @param event The event to update.
     * @throws IllegalArgumentException if the event is null, expired, or if validation fails.
     */
    @Override
    public void updateEvent(EventEntity event)
    {
        if (event == null) {
            throw new IllegalArgumentException("| ERROR | Event is NULL");
        }

        if (LocalDateTime.now().isAfter(event.getEndDate())) {
            throw new IllegalArgumentException("| ERROR | Event is already Expired");
        }

        event.setCreator(userRepository.findById(event.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("| ERROR | Creator doesn't exist")));

        eventRepository.save(event);
    }

    /**
     * Updates the details of an existing point of interest. Validates the POI's existence, its creator, and town hall.
     * Throws an exception if the POI is null, or if the creator or town hall does not exist.
     *
     * @param pointOfInterest The point of interest to update.
     * @throws IllegalArgumentException if the POI is null or if validation fails.
     */
    @Override
    public void updatePoi(POIEntity pointOfInterest)
    {
        if (pointOfInterest == null) {
            throw new IllegalArgumentException("| ERROR | PointOfInterest is NULL");
        }

        pointOfInterest.setCreator(userRepository.findById(pointOfInterest.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("| ERROR | Creator doesn't exist")));

        pointOfInterestRepository.save(pointOfInterest);
    }

    /**
     * Updates the details of an existing itinerary. Validates the itinerary's existence, its creator, and updates its
     * content list. Throws an exception if the itinerary is null, does not exist, or if validation fails.
     *
     * @param itinerary The itinerary to update.
     * @param contents A list of content IDs to include in the itinerary.
     * @throws IllegalArgumentException if the itinerary is null, does not exist, or if validation fails.
     */
    @Override
    public void updateItinerary(ItineraryEntity itinerary, List<Long> contents)
    {
        if (itinerary == null) {
            throw new IllegalArgumentException("| ERROR | Itinerary is NULL");
        }

        itineraryRepository.findById(itinerary.getId())
                .orElseThrow(() -> new IllegalArgumentException("| ERROR | Itinerary doesn't exist"));

        UserEntity user = userRepository.findById(itinerary.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("| ERROR | User doesn't exist"));
        itinerary.setCreator(user);

        for (Long id : contents)
        {
            ContentEntity content = contentRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("| ERROR | Content doesn't exist"));
            itinerary.getContents().add(content);
        }

        itineraryRepository.save(itinerary);
    }

    /**
     * Approves an event by setting its status to ACCEPTED.
     *
     * @param id The ID of the event to approve.
     */
    @Override
    public void approveEvent(Long id) {
        eventRepository.findById(id).ifPresent(e -> {
            e.setStatus(StateContent.ACCEPTED);
            eventRepository.save(e);
        });
    }

    /*
     * Approves a point of interest by setting its status to ACCEPTED.
     *
     * @param id The ID of the point of interest to approve.
     */
    @Override
    public void approvePointOfInterest(Long id) {
        pointOfInterestRepository.findById(id).ifPresent(poi -> {
            poi.setStatus(StateContent.ACCEPTED);
            pointOfInterestRepository.save(poi);
        });
    }

    /**
     * Approves an itinerary by setting its status to ACCEPTED.
     *
     * @param id The ID of the itinerary to approve.
     */
    @Override
    public void approveItinerary(Long id) {
        itineraryRepository.findById(id).ifPresent(it -> {
            it.setStatus(StateContent.ACCEPTED);
            itineraryRepository.save(it);
        });
    }

    /*
     * Determines if a user has the authority to approve content based on their role in the related town hall.
     *
     * @param contentId The ID of the content to check.
     * @param userId The ID of the user whose authority is to be verified.
     * @return True if the user can approve the content, false otherwise.
     * @throws IllegalArgumentException if the content does not exist or the
     *         user does not have a role in the town hall.
     */
    public boolean canUserApproveContent(Long contentId, Long userId) {
        ContentEntity content = contentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("| ERROR | Content doesn't exist"));

        UserRole role = userRepository.findTownHallRolesByUserId(userId)
                .stream()
                .map(UserEntity::getEnumUser)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("| ERROR | User doesn't have a role in this town hall"));

        return role == UserRole.CURATOR;
    }
}