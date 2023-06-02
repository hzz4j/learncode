package org.hzz.mapper;


import lombok.extern.slf4j.Slf4j;
import org.hzz.spring.entity.Car;
import org.hzz.spring.entity.CarDto;
import org.hzz.spring.mapper.CarandCarDTOMapper;
import org.hzz.spring.service.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CarAndCarDTOMapperTest {

    @Autowired
    private CarandCarDTOMapper carandCarDTOMapper;

    @Test
    public void test() {
        CarDto carDto = new CarDto();
        carDto.setCarName("大众奔驰");
        carDto.setDescription("不怕奔驰和路虎，就怕大众带字母");

        Car car = carandCarDTOMapper.carDtoToCar(carDto);
        log.info(car.toString());
        assertEquals(car.getName(), "-:: 大众奔驰 ::-");
        assertEquals(car.getDetails(), carDto.getDescription());
    }
}
