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

        this.creator = new UserEntity();
        this.creator.setIdUser(dto.getCreatorId());
    }

    public void subscribe(ContentEntity content) {
        for (ContentEntity c : contents) {
            if (c.getId().equals(content.getId())) {
                throw new IllegalStateException("Content already subscribed to contest.");
            }
        }

        this.contents.add(content);
    }
    public void unsubscribe(ContentEntity content) {
        for (ContentEntity c : contents) {
            if (c.getId().equals(content.getId())) {
                this.contents.remove(content);
                return;
            }
        }
        throw new IllegalStateException("Content already unsubscribed to contest.");

    }
}
