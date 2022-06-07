package org.hzz.chain_of_responsibility.link;

public interface Handler {
    void process(Request request);

    boolean isSuccess();
    void failInfo();
}
