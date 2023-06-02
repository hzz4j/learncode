package org.hzz.spring.mapper;

import org.hzz.spring.entity.Car;
import org.hzz.spring.entity.CarDto;
import org.hzz.spring.service.CarService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class CarandCarDTOMapper {
    //  not to make the injected bean private
    // 如： private CarService carService; 因为mapstruct要继承这个类，所以不能private
    @Autowired
    protected CarService carService;

    @Mapping(target = "details", source = "description")
    @Mapping(target = "name", expression = "java(carService.enrichName(carDto.getCarName()))")
    public abstract Car carDtoToCar(CarDto carDto);
}
