package it.unicam.cs.ids.municipeplatform.Contest;

import it.unicam.cs.ids.municipeplatform.DTOs.ContestCreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contest")
public class ContestController implements BaseCrudController<ContestCreationRequestDTO, Long> {
    private final ContestService contestService;

    public ContestController(ContestService contestService) {
        this.contestService = contestService;
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
    public ResponseEntity<?> update(ContestCreationRequestDTO dto, Long id) {
        ContestEntity elem = new ContestEntity(dto);
        elem.setId(id);

        this.contestService.updateContest(elem, dto.getContents());

        return ResponseEntity.ok("{}");
    }

    @PutMapping("/subscribe/{contestId}")
    public ResponseEntity<?> subscribeContent(@RequestBody  Long contentId,
                                              @PathVariable Long contestId) {
        contestService.subscribeContent(contentId, contestId);

        return ResponseEntity.ok("{}");
    }

    @PutMapping("/terminate/{contestId}")
    public ResponseEntity<?> terminateContest(@PathVariable Long contestId, @RequestBody Long winningContentId) {
        contestService.terminateContest(contestId, winningContentId);

        return ResponseEntity.ok("{}");
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return null;
    }
}
