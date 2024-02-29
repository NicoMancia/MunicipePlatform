package it.unicam.cs.ids.municipeplatform.POI;

import it.unicam.cs.ids.municipeplatform.DTOs.POICreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.Content.ContentService;
import it.unicam.cs.ids.municipeplatform.DataManagerController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/poi")
public class POIController implements DataManagerController<POICreationRequestDTO, Long>
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
    @GetMapping(path ="/search")
    public List<POIEntity> searchPOI(@RequestParam String name,
                                     @RequestParam String description,
                                     @RequestParam PoiCategory category) {
        return contentService.searchPOI(name, description, category);
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
    public ResponseEntity<?> update(@PathVariable("id") Long POIid, @RequestBody POICreationRequestDTO dto) {
        POIEntity elem = new POIEntity(dto);
        elem.setId(POIid);

        contentService.updatePoi(elem);
        return ResponseEntity.ok("POI successfully updated.");
    }
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        contentService.delete(id);
        return ResponseEntity.ok().body("POI successfully deleted.");
    }
}