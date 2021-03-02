package ar.com.ada.second.tdvr.service;

import ar.com.ada.second.tdvr.component.BusinessLogicExceptionComponent;
import ar.com.ada.second.tdvr.model.dto.ArtistDTO;
import ar.com.ada.second.tdvr.model.entity.Artist;
import ar.com.ada.second.tdvr.model.mapper.ArtistMapper;
import ar.com.ada.second.tdvr.model.mapper.AvoidingMappingContext;
import ar.com.ada.second.tdvr.model.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistServices implements Services<ArtistDTO, Artist> {

    private ArtistMapper artistMapper = ArtistMapper.MAPPER;

    @Autowired
    private BusinessLogicExceptionComponent logicExceptionComponent;

    @Autowired
    private AvoidingMappingContext context;

    @Autowired
    private ArtistRepository artistRepository;


    @Override
    public ArtistDTO createNew(ArtistDTO dto) {
        // debo hacer la conversion de dto a entity
        Artist artist = artistMapper.toEntity(dto, context);

        // se le pide al repository que cuarde la entidad
        artistRepository.save(artist);

        // convierte a dto las instancia artist con el id que le asigno MySQL
        ArtistDTO artistSaved = artistMapper.toDTO(artist, context);

        // le entrego al controlador el dto con el id
        return artistSaved;
    }

    @Override
    public List<ArtistDTO> getAll() {

        // llamar al repositorio y pedirle que haga la consulta a la BD de todos los registro de de esa entidad
        List<Artist> artistList = artistRepository.findAll();// => select * from

        // convertir esa lista de DAO a una lista de DTO
        List<ArtistDTO> artistDTO = artistMapper.toDTO(artistList, context);

        /*
        ArrayList<ArtistDTO> artistDTO = new ArrayList<>();
        artistList
                .stream()
                .peek(artist -> {
                    ArtistDTO artistDTO = new ArtistDTO(artist.getId(), artist.getName());
                    artistDtoList.add(artistDTO);
                });
         */

        // retorno la lista resultante de la conversion
        return artistDTO;
    }

    @Override
    public ArtistDTO getById(Long id) {


        Optional<Artist> artistOptional = artistRepository.findById(id);

        Artist artist = artistOptional
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Artist", id));

        ArtistDTO artistDTO = artistMapper.toDTO(artist, context);

        return artistDTO;

        /*
        Optional<Artist> artistOptional = artistRepository.findById(id);
        if (artistOptional.isPresent()) {
            Artist artist = artistOptional.get();
            ArtistDTO artistById = artistMapper.toDTO(artist, context);
            return artistById;
        } else {
            throw logicExceptionComponent.getExceptionEntityNotFound("Artist", id);
        }
        */
    }

    @Override
    public ArtistDTO update(ArtistDTO dto, Long id) {
        // verifico si el id existe en la base de datos
        Optional<Artist> artistOptional = artistRepository.findById(id);

        Artist artistById = artistOptional
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Artist", id));

        mergeData(artistById, dto);

        artistRepository.save(artistById);

        ArtistDTO artistUpdated = artistMapper.toDTO(artistById, context);

        return artistUpdated;
    }

    @Override
    public void remove(Long id) {
        Optional<Artist> artistByIdToDelete = artistRepository.findById(id);

        Artist artist = artistByIdToDelete
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Artist", id));

        artistRepository.deleteById(id);
    }

    @Override
    public void mergeData(Artist entity, ArtistDTO dto) {
        if (dto.hasNullOrEmptyAttributes())
            throw logicExceptionComponent.getExceptionEntityEmptyValues("Artist");

        if (!entity.getName().equals(dto.getName()))
            entity.setName(dto.getName());

        //if (!entity.getSurname().equals(dto.getSurname()))
        //entity.setSurname(dto.getSurname());
    }

    public ArtistDTO removeById(Long id) {
        Optional<Artist> artistByIdToDelete = artistRepository.findById(id);

        Artist artist = artistByIdToDelete
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Artist", id));

        artistRepository.delete(artist);

        ArtistDTO artistDeleted = artistMapper.toDTO(artist, context);

        return artistDeleted;
    }
}