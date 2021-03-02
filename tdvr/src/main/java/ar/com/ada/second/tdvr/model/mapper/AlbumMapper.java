package ar.com.ada.second.tdvr.model.mapper;

import ar.com.ada.second.tdvr.model.dto.AlbumDTO;
import ar.com.ada.second.tdvr.model.entity.Album;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AlbumMapper extends DataMapper<AlbumDTO, Album> {

    AlbumMapper MAPPER = Mappers.getMapper(AlbumMapper.class);

}