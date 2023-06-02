package org.hzz.mapper;


import lombok.extern.slf4j.Slf4j;
import org.hzz.spring.dto.AnotherCarDTO;
import org.hzz.spring.entity.AnotherCar;
import org.hzz.spring.entity.BioDieselCar;
import org.hzz.spring.mapper.AnotherCarMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AnotherCarMapperTest {

    @Autowired
    private AnotherCarMapper anotherCarMapper;

    @Test
    public void test(){
        AnotherCar car = new BioDieselCar(1001, "q10_car");
        AnotherCarDTO anotherCarDTO = anotherCarMapper.carToCarDto(car);
        System.out.println(anotherCarDTO);
        // AnotherCarDTO(id=1001, name=Q10_CAR, fuelType=BIO_DIESEL)
    }
}
