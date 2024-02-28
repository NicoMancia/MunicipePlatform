package it.unicam.cs.ids.municipeplatform.Contest;

import it.unicam.cs.ids.municipeplatform.DTOs.ContestCreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.Content.ContentEntity;
import it.unicam.cs.ids.municipeplatform.TownHall.TownHallEntity;
import it.unicam.cs.ids.municipeplatform.User.UserEntity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import lombok.Getter;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="Contest")
public class ContestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contest_id_seq")
    private Long id;
    private String name;
    private String description;
    private Date initialDate;
    private Date endDate;
    private String rules;
    @Getter
    private String type;
    @OneToMany
    private List<ContentEntity> contents;
    @ManyToOne
    private ContentEntity winningContent;
    private boolean contestOpen;
    @ManyToOne
    private UserEntity creator;

    public ContestEntity(ContestCreationRequestDTO dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.initialDate = dto.getInitialDate();
        this.endDate = dto.getEndDate();
        this.rules = dto.getRules();
        this.type = dto.getType();
        this.contents = new ArrayList<>();
        this.contestOpen = true;

        // set it to fill it later
        this.creator = new UserEntity();
        this.creator.setIdUser(dto.getCreatorId());
    }

    public void subscribe(ContentEntity content) {
        // make sure it's not here already
        for (ContentEntity c : contents) {
            if (c.getId().equals(content.getId())) {
                throw new IllegalStateException("Content already subscribed to contest.");
            }
        }

        this.contents.add(content);
    }

    public Set<Long> closeContest(Long winnerContentId) {
        if (!this.contestOpen) {
            throw new IllegalStateException("Contest is already closed.");
        }

        this.contestOpen = false;
        this.winningContent = this.contents.stream()
                .filter(c -> c.getId().equals(winnerContentId))
                .findFirst().orElse(null);

        if (this.winningContent == null) {
            this.contestOpen = true;
            throw new IllegalStateException("Contest is already closed.");
        }

        // Get a list from all contents in the objects
        // of losing user ids that are not equal to winnerContentId from the content creators
        // and return it.
        Set<Long> losers = new HashSet<>();
        for (ContentEntity content : this.contents) {
            if (!content.getCreator().getIdUser().equals(winnerContentId)) {
                losers.add(content.getCreator().getIdUser());
            }
        }

        return losers;
    }
}
