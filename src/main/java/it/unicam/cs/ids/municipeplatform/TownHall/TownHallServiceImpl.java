package it.unicam.cs.ids.municipeplatform.TownHall;

import it.unicam.cs.ids.municipeplatform.Content.ContentService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TownHallServiceImpl implements TownHallService{
    private final TownHallRepository townHallRepository;
    private final ContentService contentService;

    public TownHallServiceImpl(TownHallRepository townHallRepository, ContentService contentService) {
        if (townHallRepository == null) {
            throw new IllegalArgumentException("| ERROR | TownHallRepository is NULL");
        }

        this.townHallRepository = townHallRepository;
        this.contentService = contentService;
    }

    @Override
    public TownHallEntity createTownHall(TownHallEntity townHall) {
        if (townHall == null) {
            throw new IllegalArgumentException("| ERROR | TownHall is NULL");
        }

        return townHallRepository.save(townHall);
    }

    @Override
    public TownHallEntity update(TownHallEntity townHall, Long aLong) {
        townHallRepository.findById(aLong)
                .orElseThrow(() -> new IllegalArgumentException("| ERROR | TownHall not found"));
        townHall.setId(aLong);
        townHallRepository.save(townHall);

        return townHall;
    }
}
