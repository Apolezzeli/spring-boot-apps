package ar.com.ada.second.tdvr.model.mapper;

import ar.com.ada.second.tdvr.model.dto.ArtistDTO;
import ar.com.ada.second.tdvr.model.entity.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ArtistMapper extends DataMapper<ArtistDTO, Artist> {

    ArtistMapper MAPPER = Mappers.getMapper(ArtistMapper.class);

}