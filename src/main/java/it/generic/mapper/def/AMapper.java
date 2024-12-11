package it.generic.mapper.def;

import org.mapstruct.Mapper;

import it.generic.mapper.common.BaseMapper;
import it.generic.mapper.dtos.ADto;
import it.generic.mapper.entities.AEntity;
import it.generic.mapper.model.Pojo;

@Pojo
@Mapper(componentModel = "spring")
public interface AMapper extends BaseMapper<AEntity, ADto> {

	@Override
	ADto map(AEntity s);


}
