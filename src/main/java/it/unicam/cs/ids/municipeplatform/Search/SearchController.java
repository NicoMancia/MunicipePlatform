package it.unicam.cs.ids.municipeplatform.Search;


import it.unicam.cs.ids.municipeplatform.Content.ContentEntity;
import it.unicam.cs.ids.municipeplatform.Contest.ContestEntity;
import it.unicam.cs.ids.municipeplatform.Event.EventEntity;
import it.unicam.cs.ids.municipeplatform.Itinerary.ItineraryEntity;
import it.unicam.cs.ids.municipeplatform.Location;
import it.unicam.cs.ids.municipeplatform.POI.POIEntity;
import it.unicam.cs.ids.municipeplatform.POI.PoiCategory;
import it.unicam.cs.ids.municipeplatform.User.UserEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/contests")
    public List<ContestEntity> searchContests(@RequestParam String name,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                              @RequestParam String type) {
        return searchService.searchContests(name, startDate, endDate, type);
    }

    @GetMapping("/content")
    public List<ContentEntity> searchContent(@RequestParam String name,
                                             @RequestParam String description,
                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date creationDate,
                                             @RequestParam(required = false) String contentType) {
        return searchService.searchContent(name, description, creationDate, contentType);
    }

    @GetMapping("/itineraries")
    public List<ItineraryEntity> searchItineraries(@RequestParam String name,
                                                   @RequestParam String description,
                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date creationDate,
                                                   @RequestParam int difficultyLevel) {
        return searchService.searchItineraries(name, description, creationDate);
    }

    @GetMapping("/pois")
    public List<POIEntity> searchPointsOfInterest(@RequestParam String name,
                                                  @RequestParam String description,
                                                  @RequestParam PoiCategory category,
                                                  @RequestParam Location location) {
        return searchService.searchPointsOfInterest(name, description, category, location);
    }

    @GetMapping("/events")
    public List<EventEntity> searchEvents(@RequestParam String name,
                                          @RequestParam String description,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return searchService.searchEvents(name, description, startDate, endDate);
    }
    @GetMapping("/usersByMail")
    public List<UserEntity> searchUser(@RequestParam String email){
        return searchService.searchUsersByEmail(email);
    }
}
