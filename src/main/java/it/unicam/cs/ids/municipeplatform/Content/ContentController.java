package it.unicam.cs.ids.municipeplatform.Content;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {
    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @PutMapping("/approve/event/{id}")
    public ResponseEntity<?> approveEvent(@PathVariable Long id, @RequestBody Long userId)
    {
        if (contentService.canUserApproveContent(id, userId))
            return ResponseEntity.badRequest().body("You cannot approve the content.");

        contentService.approveEvent(id);
        return ResponseEntity.ok().body("The content status has been successfully changed.");
    }
    @PutMapping("/approve/poi/{id}")
    public ResponseEntity<?> approvePoi(@PathVariable Long id, @RequestBody Long userId)
    {
        if (!contentService.canUserApproveContent(id, userId))
            return ResponseEntity.badRequest().body("You cannot approve the content.");

        contentService.approvePOI(id);
        return ResponseEntity.ok().body("The content status has been successfully changed.");
    }
    @PutMapping("/approve/itinerary/{id}")
    public ResponseEntity<?> approveItinerary(@PathVariable Long id, @RequestBody Long userId)
    {
        if (contentService.canUserApproveContent(id, userId))
            return ResponseEntity.badRequest().body("You cannot approve the content.");

        contentService.approveItinerary(id);
        return ResponseEntity.ok().body("The content status has been successfully changed.");
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ContentEntity>> getAllContent() {
        return ResponseEntity.ok(contentService.getAllContent());
    }

    @GetMapping(path ="/getAllByUserId/{id}")
    public ResponseEntity<List<ContentEntity>> geAllContentByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(contentService.getAllContentByUserId(id));
    }


    @GetMapping("/getAllPending")
    public ResponseEntity<List<ContentEntity>> getAllPending() {
        return ResponseEntity.ok(contentService.getAllPending());
    }
}