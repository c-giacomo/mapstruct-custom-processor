package it.generic.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.generic.mapper.common.GenericBeansMapper;
import it.generic.mapper.dtos.ADto;
import it.generic.mapper.entities.AEntity;

@SpringBootTest(classes = GenericMapperApplication.class)
class GenericMapperApplicationTests {
	
	@Autowired
	GenericBeansMapper mapper;

	@Test
	void mapNewClass() {
		AEntity entity = new AEntity();
		entity.setName("ATTILIO");
		
		ADto dto = mapper.map(entity, ADto.class);
		
		assertEquals("ATTILIO", dto.getName());
	}
	
	@Test
	void mapUpdate() {
		AEntity entity = new AEntity();
		entity.setName("ATTILIO");
		ADto dto = new ADto();
		dto.setName("FRANCESCO");
		
		assertEquals("FRANCESCO", dto.getName());
		
		dto = mapper.map(entity, dto);
		
		assertEquals("ATTILIO", dto.getName());
	}

}
