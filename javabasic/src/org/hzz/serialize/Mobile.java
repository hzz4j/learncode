package org.hzz.serialize;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Mobile implements Serializable {
    private static final long serialVersionUID = -5527474321867536114L;
    private String name;
}
