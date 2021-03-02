package ar.com.ada.second.tdvr.model.repository;

import ar.com.ada.second.tdvr.model.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
}
