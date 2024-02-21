package it.unicam.cs.ids.municipeplatform.Platform;

import it.unicam.cs.ids.municipeplatform.Content.ContentEntity;
import it.unicam.cs.ids.municipeplatform.Contest.ContestEntity;
import it.unicam.cs.ids.municipeplatform.User.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class PlatformEntity {
    private Long idPiattaforma;
    private List<UserEntity> listaUtenti= new ArrayList<>();
    private List<ContentEntity> listaContenuti= new ArrayList<>();
    private List<ContestEntity> listaContest= new ArrayList<>();

}
