package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.domain.VisitTO;
import com.tecsup.petclinic.entities.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface VisitMapper {

    @Mapping(source = "visitDate", target = "visitDate")
    Visit toVisit(VisitTO visitTO);

    @Mapping(source = "visitDate", target = "visitDate")
    VisitTO toVisitTO(Visit visit);

    List<VisitTO> toVisitTOList(List<Visit> visitList);

    List<Visit> toVisitList(List<VisitTO> visitTOList);
}
