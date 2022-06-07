package org.hzz.lambd;

public class Client {
    private String ip;
    private int port;
    private DataSource dataSource;

    public Client(String ip, int port,DataSource dataSource) {
        this.ip = ip;
        this.port = port;
        this.dataSource = dataSource;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public String toString() {
        return ip+"@@"+port;
    }
}
