package org.hzz.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class JavaTypeMappingMySqlType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private byte a; //
    private short b; //
    private boolean c; //
    private char d; //
    private int e; //
    private long f; //
    private float g; //
    private double h; //
    private String i; //
    private java.util.Date j; //
    private java.sql.Date k; //
    private LocalDateTime l; //
    private LocalDate m; //
    private java.math.BigDecimal n; //
    private java.math.BigInteger o; //
    private byte[] p; //
    private Integer q; //
    private Double r; //
    private Long s; //
    private Float t; //
}
/**
 CREATE TABLE `java_type_mapping_my_sql_type` (
 `id` int NOT NULL AUTO_INCREMENT,
 `a` tinyint NOT NULL,
 `b` smallint NOT NULL,
 `c` bit(1) NOT NULL,
 `d` char(1) NOT NULL,
 `e` int NOT NULL,
 `f` bigint NOT NULL,
 `g` float NOT NULL,
 `h` double NOT NULL,
 `i` varchar(255) DEFAULT NULL,
 `j` datetime(6) DEFAULT NULL,
 `k` date DEFAULT NULL,
 `l` datetime(6) DEFAULT NULL,
 `m` date DEFAULT NULL,
 `n` decimal(19,2) DEFAULT NULL,
 `o` decimal(19,2) DEFAULT NULL,
 `p` tinyblob,
 `q` int DEFAULT NULL,
 `r` double DEFAULT NULL,
 `s` bigint DEFAULT NULL,
 `t` float DEFAULT NULL,
 PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 */