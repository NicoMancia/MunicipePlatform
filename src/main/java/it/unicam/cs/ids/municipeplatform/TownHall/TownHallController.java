package it.unicam.cs.ids.municipeplatform.TownHall;

import it.unicam.cs.ids.municipeplatform.DTOs.TownHallCreationRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/townHall")
public class TownHallController {
    private final TownHallService townHallService;

    public TownHallController(TownHallService townHallService) {
        this.townHallService = townHallService;
    }
    public ResponseEntity<TownHallEntity> create(@RequestBody TownHallCreationRequestDTO dto) {
        TownHallEntity newTownHall = townHallService.createTownHall(new TownHallEntity(dto));
        return ResponseEntity.ok(newTownHall);
    }

    public ResponseEntity<?> update(TownHallCreationRequestDTO entity, Long aLong) {
        return ResponseEntity.ok(townHallService.update(new TownHallEntity(entity), aLong));
    }
}
