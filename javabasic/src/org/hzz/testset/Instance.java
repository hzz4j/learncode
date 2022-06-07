package org.hzz.testset;

public class Instance {
    private String ip;
    private boolean ephemeral;

    public Instance(String ip, boolean ephemeral) {
        this.ip = ip;
        this.ephemeral = ephemeral;
    }

    public String getIp() {
        return ip;
    }

    public boolean isEphemeral() {
        return ephemeral;
    }

    @Override
    public int hashCode() {
        return this.ip.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj || obj.getClass() != getClass()) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        Instance other = (Instance) obj;

        return getIp().equals(other.getIp()) && this.isEphemeral() == other.isEphemeral();
    }

    @Override
    public String toString() {
        return ip + "##" + ephemeral;
    }
}
