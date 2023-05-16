package org.hzz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MultiResult <T,R>{
    final T data1;
    final R data2;
}
