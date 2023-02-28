package org.hzz.chain_of_responsibility.simple;

public class Request {
    private boolean loggedOn;
    private boolean frequentOk;
    private boolean isPermits;
    private boolean containsSensitiveWords;
    private String  requestBody;
    private Request(boolean loggedOn, boolean frequentOk, boolean isPermits, boolean containsSensitiveWords) {
        this.loggedOn=loggedOn;
        this.frequentOk=frequentOk;
        this.isPermits=isPermits;
        this.containsSensitiveWords=containsSensitiveWords;
    }

    public boolean isLoggedOn() {
        return loggedOn;
    }

    public boolean isFrequentOk() {
        return frequentOk;
    }

    public boolean isPermits() {
        return isPermits;
    }

    public boolean isContainsSensitiveWords() {
        return containsSensitiveWords;
    }

    static class Builder{
        private boolean loggedOn;
        private boolean frequentOk;
        private boolean isPermits;
        private boolean containsSensitiveWords;

        Builder loggedOn(boolean loggedOn){
            this.loggedOn=loggedOn;
            return this;
        }

        Builder frequentOk(boolean frequentOk){
            this.frequentOk=frequentOk;
            return this;
        }
        Builder isPermits(boolean isPermits){
            this.isPermits=isPermits;
            return this;
        }
        Builder containsSensitiveWords(boolean containsSensitiveWords){
            this.containsSensitiveWords=containsSensitiveWords;
            return this;
        }

        public Request build(){
            Request request=new Request( loggedOn, frequentOk, isPermits, containsSensitiveWords);
            return request;
        }
    }
}
