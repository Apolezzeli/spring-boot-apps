package ar.com.ada.second.tdvr.controller;

import ar.com.ada.second.tdvr.model.dto.AlbumDTO;
import ar.com.ada.second.tdvr.service.AlbumServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class AlbumController {

    @Autowired
    private AlbumServices albumServices;

    @PostMapping({"/artists/{artistId}/albums", "/artists/{artistId}/albums/"})
    public ResponseEntity postArtistMethod(
            @Valid @RequestBody AlbumDTO dto,
            @PathVariable Long artistId) throws URISyntaxException {

        /**
         * Este metodo tiene una definicion especial ya que la entidad Album tiene relacion
         * con Artist, y al momento de crear un Album, es necesario indicarle a que artista estara
         * asociado, es por ello que en la URL y en el parametro del metodo se coloca una varia (artistId)
         * para indicar cual es el id del artista a buscar en la base de datos para asociarlo a los datos
         * que llegan en el body (dto)
         *
         * esa logica esta en el servicio.
         */

        AlbumDTO albumSaved = albumServices.createNew(dto, artistId);
        URI uri = new URI("/artist/" + albumSaved.getId());

        return ResponseEntity
                .created(uri)
                .body(albumSaved);
    }

    @GetMapping({"/albums", "albums"})
    public ResponseEntity getAlbumsMethod() {
        // se llama al servicio y se le pide el listado de albums
        List<AlbumDTO> albums = albumServices.getAll();

        // se crea el response request
        return ResponseEntity
                .ok()
                .body(albums);
    }

    @GetMapping({"/albums/{id}", "/albums/{id}/"})
    public ResponseEntity getAlbumByIdMethod(@PathVariable Long id) {

        AlbumDTO byId = albumServices.getById(id);

        return ResponseEntity
                .ok()
                .body(byId);
    }

    @DeleteMapping({"/albums/{id}", "/albums/{id}/"})
    public ResponseEntity deleteAlbumByIdMethod(@PathVariable Long id) {

        albumServices.remove(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping({"/artists/{artistId}/albums/{albumId}", "/artists/{artistId}/albums/{albumId}/"})
    public ResponseEntity patchArtistByIdMethod(
            @RequestBody AlbumDTO dto,
            @PathVariable Long artistId,
            @PathVariable Long albumId) {

        AlbumDTO albumUpdated = albumServices.update(dto, artistId, albumId);

        return ResponseEntity
                .ok()
                .body(albumUpdated);
    }

}