package it.unicam.cs.ids.municipeplatform.Event;

import it.unicam.cs.ids.municipeplatform.DataManagerController;
import it.unicam.cs.ids.municipeplatform.DTOs.EventCreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.Content.ContentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/event")
public class EventController implements DataManagerController<EventCreationRequestDTO, Long>
{
    private final ContentService contentService;

    public EventController(ContentService contentService) {
        this.contentService = contentService;
    }
    @Override
    public ResponseEntity<EventEntity> create(EventCreationRequestDTO dto) {
        EventEntity newEvent = contentService.createNewEvent(new EventEntity(dto));
        return ResponseEntity.ok(newEvent);
    }

    @GetMapping("/search")
    public List<EventEntity> searchEvents(@RequestParam String name,
                                          @RequestParam String description,
                                          @RequestParam EventCategory category,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate) {
        return contentService.searchEvents(name, description, category,startDate, endDate);
    }

    @Override
    public ResponseEntity<EventEntity> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(contentService.getEvent(id));
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        contentService.delete(id);
        return ResponseEntity.ok().body("Event successfully deleted.");
    }
    @Override
    public ResponseEntity<List<EventEntity>> getAll() {
        return ResponseEntity.ok(contentService.getAllEvent());
    }

    @Override
    public ResponseEntity<?> update(@PathVariable("id") Long eventId, @RequestBody EventCreationRequestDTO dto){

        //Save up event id
        EventEntity elem = new EventEntity(dto);
        elem.setId(eventId);

        contentService.updateEvent(elem);
        return ResponseEntity.ok("Event successfully updated.");
    }
}
