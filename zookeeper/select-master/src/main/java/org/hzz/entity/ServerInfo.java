package org.hzz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerInfo {
    private String ip;
    private int port;
    private int index;
}
