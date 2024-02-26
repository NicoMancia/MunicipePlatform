package it.unicam.cs.ids.municipeplatform.TownHall;

public interface TownHallService {
    TownHallEntity createTownHall(TownHallEntity townHall);

    TownHallEntity update(TownHallEntity townHall, Long aLong);
}
