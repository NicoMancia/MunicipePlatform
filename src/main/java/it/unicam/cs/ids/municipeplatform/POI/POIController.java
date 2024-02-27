package it.unicam.cs.ids.municipeplatform.POI;

import it.unicam.cs.ids.municipeplatform.DTOs.POICreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.Content.ContentService;
import it.unicam.cs.ids.municipeplatform.BaseCrudController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/poi")
public class POIController implements BaseCrudController<POICreationRequestDTO, Long>
{
    private final ContentService contentService;

    public POIController(ContentService contentService) {
        this.contentService = contentService;
    }
    @Override
    public ResponseEntity<POIEntity> create(POICreationRequestDTO dto) {
        POIEntity newPointOfInterest = contentService.createNewPointOfInterest(new POIEntity(dto));
        return ResponseEntity.ok(newPointOfInterest);
    }

    @Override
    public ResponseEntity<POIEntity> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(contentService.getPoi(id));
    }

    @Override
    public ResponseEntity<List<POIEntity>> getAll() {
        return ResponseEntity.ok(contentService.getAllPoi());
    }

    @Override
    public ResponseEntity<?> update(POICreationRequestDTO dto, Long id) {
        POIEntity elem = new POIEntity(dto);
        elem.setId(id);

        contentService.updatePoi(elem);
        return ResponseEntity.ok("{}");
    }
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return null;
}
}