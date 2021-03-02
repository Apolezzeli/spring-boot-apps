package ar.com.ada.second.tdvr.service;

import ar.com.ada.second.tdvr.component.BusinessLogicExceptionComponent;
import ar.com.ada.second.tdvr.model.dto.AlbumDTO;
import ar.com.ada.second.tdvr.model.entity.Album;
import ar.com.ada.second.tdvr.model.entity.Artist;
import ar.com.ada.second.tdvr.model.mapper.AlbumMapper;
import ar.com.ada.second.tdvr.model.mapper.AvoidingMappingContext;
import ar.com.ada.second.tdvr.model.repository.AlbumRepository;
import ar.com.ada.second.tdvr.model.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumServices implements Services<AlbumDTO, Album> {

    private AlbumMapper albumMapper = AlbumMapper.MAPPER;

    @Autowired
    private AvoidingMappingContext context;

    @Autowired
    private BusinessLogicExceptionComponent logicExceptionComponent;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public AlbumDTO createNew(AlbumDTO dto) {
        return null;
    }

    public AlbumDTO createNew(AlbumDTO dto, Long id) {

        /**
         * Este metodo es especial por el tema de las entidades relacionadas, ademas de
         * recibir el dto (datos a guardar en la DB) se recibe el id de la entodad a buscar en la
         * DB para hacer la relacion.
         */

        Artist artist = artistRepository
                .findById(id)
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Artist", id));

        Album albumToSave = albumMapper.toEntity(dto, context);

        albumToSave.setArtist(artist);

        albumRepository.save(albumToSave);

        AlbumDTO albumSaved = albumMapper.toDTO(albumToSave, context);

        return albumSaved;
    }

    @Override
    public List<AlbumDTO> getAll() {
        // llamar al repositorio y pedirle que haga la consulta a la BD de todos los registro de de esa entidad
        List<Album> albumList = albumRepository.findAll();// => select * from

        // convertir esa lista de DAO a una lista de DTO
        List<AlbumDTO> albumDTO = albumMapper.toDTO(albumList, context);

        // retorno la lista resultante de la conversion
        return albumDTO;
    }

    @Override
    public AlbumDTO getById(Long id) {
        Optional<Album> albumOptional = albumRepository.findById(id);

        Album album = albumOptional
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Album", id));

        AlbumDTO albumDTO = albumMapper.toDTO(album, context);

        return albumDTO;

    }

    @Override
    public AlbumDTO update(AlbumDTO dto, Long id) {
        // verifico si el id existe en la base de datos
        Optional<Album> albumOptional = albumRepository.findById(id);

        Album albumtById = albumOptional
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Album", id));

        mergeData(albumtById, dto);

        albumRepository.save(albumtById);

        AlbumDTO albumUpdated = albumMapper.toDTO(albumtById, context);

        return albumUpdated;

    }

    @Override
    public void remove(Long id) {
        Optional<Album> albumByIdToDelete = albumRepository.findById(id);

        Album album = albumByIdToDelete
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Album", id));

        albumRepository.delete(album);
    }


    @Override
    public void mergeData(Album entity, AlbumDTO dto) {
        if (dto.hasNullOrEmptyAttributes())
            throw logicExceptionComponent.getExceptionEntityEmptyValues("Album");

        if (!entity.getTitle().equals(dto.getTitle()))
            entity.setTitle(dto.getTitle());

        if (!entity.getReleased().equals(dto.getReleased()))
            entity.setReleased(dto.getReleased());
    }

    public AlbumDTO update(AlbumDTO dto, Long artistId, Long albumId) {

        /**
         * Busco el Artist en la base de datos, en caso que no exista, lanzara una  ExceptionEntityNotFound
         */
        Artist artistByIdFromDB = artistRepository
                .findById(artistId)
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Artist", artistId));

        /**
         * Busco el Album en la base de datos, en caso que no exista, lanzara una  ExceptionEntityNotFound
         */
        Album albumByIdFromDB = albumRepository
                .findById(albumId)
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Album", albumId));

        /**
         * Se le setea el artista de la base de datos al albun de la base de datos
         */
        albumByIdFromDB.setArtist(artistByIdFromDB);

        /**
         * se realiza el merge de los datos del dto a los que se trajo en la base de datos.
         */
        mergeData(albumByIdFromDB, dto);

        /**
         * se procede a guardar los cambios en la base de datos.
         */
        albumRepository.save(albumByIdFromDB);

        /**
         * se convierte el entity a dto
         */
        AlbumDTO albumUpdated = albumMapper.toDTO(albumByIdFromDB, context);

        /**
         * se entrega al controlador el dto con los cambios efectuados
         */
        return albumUpdated;
    }
}