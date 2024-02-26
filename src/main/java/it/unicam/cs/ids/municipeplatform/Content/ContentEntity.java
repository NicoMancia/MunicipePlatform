package it.unicam.cs.ids.municipeplatform.Content;
import it.unicam.cs.ids.municipeplatform.TownHall.TownHallEntity;
import it.unicam.cs.ids.municipeplatform.User.UserEntity;

import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jdk.jshell.spi.ExecutionControl;
import lombok.Getter;
import lombok.Setter;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="content")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_id_seq")
    private Long Id;
    String name = null;
    String description = null;
    //b
    Date creationDate = null;
    @ManyToOne
    UserEntity creator = null;
    StateContent status = null;

    public String getContentType() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented yet.");
    }

    public ContentEntity(String name, String description, Date creationDate, Long creator) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.creator = new UserEntity();
        this.creator.setIdUtente(creator);
    }

    public ContentEntity(Long id) {
        this.setId(id);
    }
}
