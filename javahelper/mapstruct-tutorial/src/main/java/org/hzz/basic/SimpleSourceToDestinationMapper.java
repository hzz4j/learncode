package org.hzz.basic;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface SimpleSourceToDestinationMapper {

    @Mappings(
            {
                    @Mapping(source = "name", target = "anotherName"),
                    @Mapping(source = "description", target = "anotherDescription")
            }
    )
    SimpleDestination sourceToDestination(SimpleSource source);
}
