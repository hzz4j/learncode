package org.hzz.spring.mapper;

import org.hzz.spring.dto.AnotherCarDTO;
import org.hzz.spring.dto.FuelType;
import org.hzz.spring.entity.AnotherCar;
import org.hzz.spring.entity.BioDieselCar;
import org.hzz.spring.entity.ElectricCar;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class AnotherCarMapper {

    @BeforeMapping
    protected void enrichDTOWithFuelType(AnotherCar car,
                                      @MappingTarget AnotherCarDTO carDTO){
        if (car instanceof ElectricCar) {
            carDTO.setFuelType(FuelType.ELECTRIC);
        }

        if (car instanceof BioDieselCar){
            carDTO.setFuelType(FuelType.BIO_DIESEL);
        }
    }

    public abstract AnotherCarDTO carToCarDto(AnotherCar car);

    @AfterMapping
    protected void convertNameToUpperCase(@MappingTarget AnotherCarDTO carDTO){
        carDTO.setName(carDTO.getName().toUpperCase());
    }
}
