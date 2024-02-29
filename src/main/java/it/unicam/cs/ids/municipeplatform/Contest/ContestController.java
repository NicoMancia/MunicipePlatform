package it.unicam.cs.ids.municipeplatform.Contest;

import it.unicam.cs.ids.municipeplatform.DTOs.ContestCreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.DataManagerController;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/contest")
public class ContestController implements DataManagerController<ContestCreationRequestDTO, Long> {
    private final ContestService contestService;

    public ContestController(ContestService contestService) {
        this.contestService = contestService;
    }
    @GetMapping(path ="/search")
    public List<ContestEntity> searchContests(@RequestParam String name,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate,
                                              @RequestParam String type) {
        return contestService.searchContests(name, startDate, endDate, type);
    }
    @Override
    public ResponseEntity<ContestEntity> create(ContestCreationRequestDTO dto) {
        ContestEntity elem = contestService.createContest(new ContestEntity(dto), dto.getContents());

        return ResponseEntity.ok(elem);
    }

    @Override
    public ResponseEntity<ContestEntity> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(contestService.getContest(id));
    }

    @Override
    public ResponseEntity<List<ContestEntity>> getAll() {
        return ResponseEntity.ok(contestService.getAllContest());
    }

    @Override
    public ResponseEntity<?> update(Long contestId, ContestCreationRequestDTO dto) {
        ContestEntity elem = new ContestEntity(dto);
        elem.setId(contestId);

        this.contestService.updateContest(elem, dto.getContents());

        return ResponseEntity.ok("Content successfully updated!");
    }

    @PutMapping("/subscribe/{contestId}")
    public ResponseEntity<?> subscribeContent(@RequestBody  Long contentId,
                                              @PathVariable Long contestId) {
        if(!contestService.subscribeContent(contentId, contestId))
            return ResponseEntity.ok("Unable to enroll new content, contest ended!");

        return ResponseEntity.ok("Content successfully subscribed!");
    }

    @PutMapping("/unsubscribe/{contestId}")
    public ResponseEntity<?> unsubscribeContent(@RequestBody  Long contentId,
                                              @PathVariable Long contestId) {
        if(!contestService.subscribeContent(contentId, contestId))
            return ResponseEntity.ok("Unable to unsubscribe content, contest ended!");

        return ResponseEntity.ok("Content successfully unsubscribed.");
    }

    @PutMapping("/terminate/{contestId}")
    public ResponseEntity<?> terminateContest(@PathVariable Long contestId) {
        contestService.terminateContest(contestId);

        return ResponseEntity.ok("Contest ended!");
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return null;
    }
}
