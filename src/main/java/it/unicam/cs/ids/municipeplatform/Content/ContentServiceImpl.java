package it.unicam.cs.ids.municipeplatform.Content;
import it.unicam.cs.ids.municipeplatform.Event.EventEntity;
import it.unicam.cs.ids.municipeplatform.Event.EventRepository;
import it.unicam.cs.ids.municipeplatform.Itinerary.ItineraryEntity;
import it.unicam.cs.ids.municipeplatform.Itinerary.ItineraryRepository;
import it.unicam.cs.ids.municipeplatform.POI.POIEntity;
import it.unicam.cs.ids.municipeplatform.POI.POIRepository;
import it.unicam.cs.ids.municipeplatform.User.UserEntity;
import it.unicam.cs.ids.municipeplatform.User.UserRepository;

import it.unicam.cs.ids.municipeplatform.User.*;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ContentServiceImpl implements ContentService {
    private final ItineraryRepository itineraryRepository;
    private final POIRepository POIRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    public ContentServiceImpl(ItineraryRepository itineraryRepository,
                              POIRepository POIRepository,
                              EventRepository eventRepository,
                              UserRepository userRepository, ContentRepository contentRepository) {

        this.itineraryRepository = itineraryRepository;
        this.POIRepository = POIRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
    }

    private StateContent genericApprovalDecision(UserRole role) {
        return switch (role) {
            case PLATFORM_MANAGER, ANIMATOR, CURATOR, AUTHORIZED_CONTRIBUTOR -> StateContent.ACCEPTED;
            default -> StateContent.PENDING;
        };
    }

    private Optional<StateContent> getDefaultApprovalStatusFromUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User doesn't exist."));

        List<UserEntity> roles = userRepository.findTownHallRolesByUserId(id);

        for (UserEntity role : roles) {
            return Optional.of(genericApprovalDecision(role.getEnumUser()));
        }

        return Optional.empty();
    }

    public ItineraryEntity createNewItinerary(ItineraryEntity itinerary,List<Long> contents) {
        if (itinerary == null) {
            throw new IllegalArgumentException("Itinerary is NULL.");
        }

        itinerary.setCreator(userRepository.findById(itinerary.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("Creator doesn't exist.")));

        for (Long id : contents)
        {
            ContentEntity content = contentRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Content doesn't exist."));
            itinerary.getContents().add(content);
        }

        itinerary.setStatus(getDefaultApprovalStatusFromUser(itinerary.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("User doesn't have an approval status.")));

        return itineraryRepository.save(itinerary);
    }

    public POIEntity createNewPointOfInterest(POIEntity pointOfInterest) {
        if (pointOfInterest == null) {
            throw new IllegalArgumentException("PointOfInterest is NULL.");
        }

        pointOfInterest.setCreator(userRepository.findById(pointOfInterest.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("Creator doesn't exist.")));

        pointOfInterest.setStatus(getDefaultApprovalStatusFromUser(pointOfInterest.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("User doesn't have an approval status.")));

        return POIRepository.save(pointOfInterest);
    }

    public EventEntity createNewEvent(EventEntity event) {
        if (event == null) {
            throw new IllegalArgumentException("Event is NULL.");
        }

        event.setCreator(userRepository.findById(event.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("Creator doesn't exist.")));

        event.setStatus(getDefaultApprovalStatusFromUser(event.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("User doesn't have an approval status.")));

        return eventRepository.save(event);
    }

    @Override
    public ItineraryEntity getItinerary(Long id)
    {
        return itineraryRepository.findById(id).orElseThrow();
    }

    @Override
    public POIEntity getPoi(Long id)
    {
        return POIRepository.findById(id).orElseThrow();
    }

    @Override
    public EventEntity getEvent(Long id)
    {
        return eventRepository.findById(id).orElseThrow();
    }

    @Override
    public ContentEntity getContent(Long id) {
        return contentRepository.findById(id).orElseThrow();
    }

    @Override
    public List<ContentEntity> getAllContent(){
        return StreamSupport.stream(contentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContentEntity> getAllPending(){
        return new ArrayList<>(contentRepository.findAllPending());
    }

    @Override
    public List<ContentEntity> getAllContentByUserId(Long userId){
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("Creator doesn't exist.");
        }
        return contentRepository.findAllContentByUserId(userId);
    }

    public List<EventEntity> getAllEvent() {
        return StreamSupport.stream(eventRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<ItineraryEntity> getAllItinerary() {
        return StreamSupport.stream(itineraryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<POIEntity> getAllPoi() {
        return StreamSupport.stream(POIRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEvent(EventEntity event)
    {
        if (event == null) {
            throw new IllegalArgumentException("Event is NULL.");
        }

        if (LocalDateTime.now().isAfter(event.getEndDate())) {
            throw new IllegalArgumentException("Event is already Expired.");
        }

        event.setCreator(userRepository.findById(event.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("Creator doesn't exist.")));

        eventRepository.save(event);
    }

    @Override
    public void updatePoi(POIEntity pointOfInterest)
    {
        if (pointOfInterest == null) {
            throw new IllegalArgumentException("PointOfInterest is NULL.");
        }

        pointOfInterest.setCreator(userRepository.findById(pointOfInterest.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("Creator doesn't exist.")));

        POIRepository.save(pointOfInterest);
    }

    @Override
    public void updateItinerary(ItineraryEntity itinerary, List<Long> contents)
    {
        if (itinerary == null) {
            throw new IllegalArgumentException("Itinerary is NULL.");
        }

        itineraryRepository.findById(itinerary.getId())
                .orElseThrow(() -> new IllegalArgumentException("Itinerary doesn't exist."));

        UserEntity user = userRepository.findById(itinerary.getCreator().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("User doesn't exist."));

        itinerary.setCreator(user);

        for (Long id : contents)
        {
            ContentEntity content = contentRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Content doesn't exist."));
            itinerary.getContents().add(content);
        }

        itineraryRepository.save(itinerary);
    }

    @Override
    public void approveEvent(Long id) {
        eventRepository.findById(id).ifPresent(e -> {
            e.setStatus(StateContent.ACCEPTED);
            eventRepository.save(e);
        });
    }

    @Override
    public void approvePOI(Long id) {
        POIRepository.findById(id).ifPresent(poi -> {
            poi.setStatus(StateContent.ACCEPTED);
            POIRepository.save(poi);
        });
    }

    @Override
    public void approveItinerary(Long id) {
        itineraryRepository.findById(id).ifPresent(it -> {
            it.setStatus(StateContent.ACCEPTED);
            itineraryRepository.save(it);
        });
    }

    public boolean canUserApproveContent(Long contentId, Long userId) {
        ContentEntity content = contentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("Content doesn't exist."));

        userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User doesn't exist."));
        UserRole role = userRepository.findUserRole(userId).getEnumUser();

        return role == UserRole.CURATOR;
    }
}