package ar.com.ada.second.tdvr.model.repository;

import ar.com.ada.second.tdvr.model.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}