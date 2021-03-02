package ar.com.ada.second.tdvr.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "Track")
public class Track implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "track_duration", nullable = false)
    private String trackDuration;

    @ManyToOne
    @JoinColumn(name = "Album_id", nullable = false, foreignKey = @ForeignKey(name = "fk_Album_Track"))
    private Album album;
}