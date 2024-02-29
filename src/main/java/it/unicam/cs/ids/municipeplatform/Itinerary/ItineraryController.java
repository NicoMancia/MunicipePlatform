package it.unicam.cs.ids.municipeplatform.Itinerary;

import it.unicam.cs.ids.municipeplatform.DTOs.ItineraryCreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.Content.ContentService;
import it.unicam.cs.ids.municipeplatform.DataManagerController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/itinerary")
public class ItineraryController implements DataManagerController<ItineraryCreationRequestDTO, Long>
{
    private final ContentService contentService;

    public ItineraryController(ContentService contentService) {
        this.contentService = contentService;
    }
    @Override
    public ResponseEntity<ItineraryEntity> create(ItineraryCreationRequestDTO dto) {
        ItineraryEntity newItinerary = contentService.createNewItinerary(new ItineraryEntity(dto), dto.getContents());
        return ResponseEntity.ok(newItinerary);
    }

    @Override
    public ResponseEntity<ItineraryEntity> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(contentService.getItinerary(id));
    }

    @Override
    public ResponseEntity<List<ItineraryEntity>> getAll() {
        return ResponseEntity.ok(contentService.getAllItinerary());
    }

    @Override
    public ResponseEntity<?> update(@PathVariable("id") Long itineraryId,@RequestBody ItineraryCreationRequestDTO dto) {
        ItineraryEntity it = new ItineraryEntity(dto);
        it.setId(itineraryId);

        contentService.updateItinerary(it, dto.getContents());
        return ResponseEntity.ok("Itinerary successfully updated.");
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return null;
    }
}