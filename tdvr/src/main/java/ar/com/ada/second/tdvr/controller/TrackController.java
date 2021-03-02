package ar.com.ada.second.tdvr.controller;

import ar.com.ada.second.tdvr.model.dto.TrackDTO;
import ar.com.ada.second.tdvr.service.TrackServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class TrackController {
    @Autowired
    private TrackServices trackServices;

    @PostMapping({"/albums/{albumsId}/tracks", "/albums/{albumsId}/tracks/"})
    public ResponseEntity postAlbumMethod(
            @Valid @RequestBody TrackDTO dto,
            @PathVariable Long albumId) throws URISyntaxException {

        /**
         * Este metodo tiene una definicion especial ya que la entidad Track tiene relacion
         * con Album, y al momento de crear un Track, es necesario indicarle a que album estara
         * asociado, es por ello que en la URL y en el parametro del metodo se coloca una variable (albumId)
         * para indicar cual es el id del album a buscar en la base de datos para asociarlo a los datos
         * que llegan en el body (dto)
         *
         * esa logica esta en el servicio.
         */

        TrackDTO trackSaved = trackServices.createNew(dto, albumId);
        URI uri = new URI("/album/" + trackSaved.getId());

        return ResponseEntity
                .created(uri)
                .body(trackSaved);
    }

    @GetMapping({"/tracks", "tracks"})
    public ResponseEntity getTracksMethod() {
        // se llama al servicio y se le pide el listado de tracks
        List<TrackDTO> tracks = trackServices.getAll();

        // se crea el response request
        return ResponseEntity
                .ok()
                .body(tracks);
    }

    @GetMapping({"/tracks/{id}", "/tracks/{id}/"})
    public ResponseEntity getTrackByIdMethod(@PathVariable Long id) {

        TrackDTO byId = trackServices.getById(id);

        return ResponseEntity
                .ok()
                .body(byId);
    }

    @DeleteMapping({"/tracks/{id}", "/tracks/{id}/"})
    public ResponseEntity deleteTrackByIdMethod(@PathVariable Long id) {

        trackServices.remove(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping({"/albums/{albumId}/tracks/{trackId}", "/albums/{albumId}/tracks/{trackId}/"})
    public ResponseEntity patchAlbumByIdMethod(
            @RequestBody TrackDTO dto,
            @PathVariable Long albumId,
            @PathVariable Long trackId) {

        TrackDTO trackUpdated = trackServices.update(dto, albumId, trackId);

        return ResponseEntity
                .ok()
                .body(trackUpdated);
    }

}

