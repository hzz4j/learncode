package org.hzz.basic;

import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.assertEquals;


public class MainTest {
    @Test
    public  void test() {
        SimpleSourceToDestinationMapper mapper = Mappers.getMapper(SimpleSourceToDestinationMapper.class);
        SimpleSource simpleSource = new SimpleSource();
        simpleSource.setName("hzz");
        simpleSource.setDescription("Q10Viking learning mapstruct tutorial");

        SimpleDestination simpleDestination = mapper.sourceToDestination(simpleSource);
        assertEquals(simpleSource.getName(), simpleDestination.getAnotherName());
        assertEquals(simpleSource.getDescription(), simpleDestination.getAnotherDescription());
    }
}
