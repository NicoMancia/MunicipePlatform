package it.unicam.cs.ids.municipeplatform.Event;

import it.unicam.cs.ids.municipeplatform.DataManagerController;
import it.unicam.cs.ids.municipeplatform.DTOs.EventCreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.Content.ContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Override
    public ResponseEntity<EventEntity> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(contentService.getEvent(id));
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

    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return null;
    }
}
