package ar.com.ada.second.tdvr.service;

import ar.com.ada.second.tdvr.component.BusinessLogicExceptionComponent;
import ar.com.ada.second.tdvr.model.dto.TrackDTO;
import ar.com.ada.second.tdvr.model.entity.Album;
import ar.com.ada.second.tdvr.model.entity.Track;
import ar.com.ada.second.tdvr.model.mapper.AvoidingMappingContext;
import ar.com.ada.second.tdvr.model.mapper.TrackMapper;
import ar.com.ada.second.tdvr.model.repository.AlbumRepository;
import ar.com.ada.second.tdvr.model.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackServices implements Services<TrackDTO, Track> {
    private TrackMapper trackMapper = TrackMapper.MAPPER;

    @Autowired
    private AvoidingMappingContext context;

    @Autowired
    private BusinessLogicExceptionComponent logicExceptionComponent;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public TrackDTO createNew(TrackDTO dto) {
        return null;
    }

    public TrackDTO createNew(TrackDTO dto, Long id) {

        /**
         * Este metodo es especial por el tema de las entidades relacionadas, ademas de
         * recibir el dto (datos a guardar en la DB) se recibe el id de la entodad a buscar en la
         * DB para hacer la relacion.
         */

        Album album = albumRepository
                .findById(id)
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Album", id));

        Track trackToSave = trackMapper.toEntity(dto, context);

        trackToSave.setAlbum(album);

        trackRepository.save(trackToSave);

        TrackDTO trackSaved = trackMapper.toDTO(trackToSave, context);

        return trackSaved;
    }

    @Override
    public List<TrackDTO> getAll() {
        // llamar al repositorio y pedirle que haga la consulta a la BD de todos los registro de de esa entidad
        List<Track> trackList = trackRepository.findAll();// => select * from

        // convertir esa lista de DAO a una lista de DTO
        List<TrackDTO> trackDTO = trackMapper.toDTO(trackList, context);

        // retorno la lista resultante de la conversion
        return trackDTO;
    }

    @Override
    public TrackDTO getById(Long id) {
        Optional<Track> trackOptional = trackRepository.findById(id);

        Track track = trackOptional
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Track", id));

        TrackDTO trackDTO = trackMapper.toDTO(track, context);

        return trackDTO;

    }

    @Override
    public TrackDTO update(TrackDTO dto, Long id) {
        // verifico si el id existe en la base de datos
        Optional<Track> trackOptional = trackRepository.findById(id);

        Track trackById = trackOptional
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Track", id));

        mergeData(trackById, dto);

        trackRepository.save(trackById);

        TrackDTO trackUpdated = trackMapper.toDTO(trackById, context);

        return trackUpdated;

    }

    @Override
    public void remove(Long id) {
        Optional<Track> trackByIdToDelete = trackRepository.findById(id);

        Track track = trackByIdToDelete
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Track", id));

        trackRepository.delete(track);
    }


    @Override
    public void mergeData(Track entity, TrackDTO dto) {
        if (dto.hasNullOrEmptyAttributes())
            throw logicExceptionComponent.getExceptionEntityEmptyValues("Track");

        if (!entity.getTitle().equals(dto.getTitle()))
            entity.setTitle(dto.getTitle());

    }

    public TrackDTO update(TrackDTO dto, Long albumId, Long trackId) {

        /**
         * Busco el Album en la base de datos, en caso que no exista, lanzara una  ExceptionEntityNotFound
         */
        Album albumByIdFromDB = albumRepository
                .findById(albumId)
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Album", albumId));

        /**
         * Busco el Track en la base de datos, en caso que no exista, lanzara una  ExceptionEntityNotFound
         */
        Track trackByIdFromDB = trackRepository
                .findById(trackId)
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Track", trackId));

        /**
         * Se le setea el album de la base de datos al track de la base de datos
         */
        trackByIdFromDB.setAlbum(albumByIdFromDB);

        /**
         * se realiza el merge de los datos del dto a los que se trajo en la base de datos.
         */
        mergeData(trackByIdFromDB, dto);

        /**
         * se procede a guardar los cambios en la base de datos.
         */
        trackRepository.save(trackByIdFromDB);

        /**
         * se convierte el entity a dto
         */
        TrackDTO trackUpdated = trackMapper.toDTO(trackByIdFromDB, context);

        /**
         * se entrega al controlador el dto con los cambios efectuados
         */
        return trackUpdated;

    }
}
