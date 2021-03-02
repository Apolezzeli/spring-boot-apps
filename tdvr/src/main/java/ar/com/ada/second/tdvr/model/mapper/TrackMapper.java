package ar.com.ada.second.tdvr.model.mapper;

import ar.com.ada.second.tdvr.model.dto.TrackDTO;
import ar.com.ada.second.tdvr.model.entity.Track;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrackMapper extends DataMapper<TrackDTO, Track> {

    TrackMapper MAPPER = Mappers.getMapper(TrackMapper.class);

}