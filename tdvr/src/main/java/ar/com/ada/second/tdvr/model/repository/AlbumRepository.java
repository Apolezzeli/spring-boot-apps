package ar.com.ada.second.tdvr.model.repository;

import ar.com.ada.second.tdvr.model.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
}