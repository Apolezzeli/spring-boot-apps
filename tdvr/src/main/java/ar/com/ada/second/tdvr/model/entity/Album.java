package ar.com.ada.second.tdvr.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "Album")
public class Album implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Date released;

    @ManyToOne
    @JoinColumn(name = "Artist_id", nullable = false, foreignKey = @ForeignKey(name = "fk_Album_Artist"))
    private Artist artist;

    @OneToMany(mappedBy = "album")
    private List<Track> tracks;
}