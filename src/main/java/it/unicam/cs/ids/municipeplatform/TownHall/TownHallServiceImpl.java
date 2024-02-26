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

    /**
     * Creates and saves a new town hall. Validates that the town hall object is not null.
     *
     * @param townHall The town hall to be created.
     * @return The saved town hall entity.
     * @throws IllegalArgumentException if the town hall object is null.
     */
    @Override
    public TownHallEntity createTownHall(TownHallEntity townHall) {
        if (townHall == null) {
            throw new IllegalArgumentException("| ERROR | TownHall is NULL");
        }

        return townHallRepository.save(townHall);
    }

    /**
     * Updates a town hall with new information.
     * Validates the existence of the town hall by its ID before updating.
     *
     * @param townHall The new town hall information to update.
     * @param aLong The ID of the town hall to update.
     * @return The updated town hall entity.
     * @throws IllegalArgumentException if the town hall is not found.
     */
    @Override
    public TownHallEntity update(TownHallEntity townHall, Long aLong) {
        townHallRepository.findById(aLong)
                .orElseThrow(() -> new IllegalArgumentException("| ERROR | TownHall not found"));
        townHall.setId(aLong);
        townHallRepository.save(townHall);

        return townHall;
    }
}
