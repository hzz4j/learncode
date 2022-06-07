package org.hzz.enm.value;

public class Main {
    public static void main(String[] args) {
        HttpMethod post1 = HttpMethod.valueOf("POST");
        HttpMethod post2 = HttpMethod.valueOf("POST");
        System.out.println(post1 == post2); // true
        HttpMethod[] values = HttpMethod.values();
    }
}
