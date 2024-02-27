package it.unicam.cs.ids.municipeplatform.Content;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok().body("{}");
    }
    @PutMapping("/approve/poi/{id}")
    public ResponseEntity<?> approvePoi(@PathVariable Long id, @RequestBody Long userId)
    {
        if (contentService.canUserApproveContent(id, userId))
            return ResponseEntity.badRequest().body("You cannot approve the content.");

        contentService.approvePointOfInterest(id);
        return ResponseEntity.ok().body("{}");
    }
    @PutMapping("/approve/itinerary/{id}")
    public ResponseEntity<?> approveItinerary(@PathVariable Long id, @RequestBody Long userId)
    {
        if (contentService.canUserApproveContent(id, userId))
            return ResponseEntity.badRequest().body("You cannot approve the content.");

        contentService.approveItinerary(id);
        return ResponseEntity.ok().body("{}");
}
}