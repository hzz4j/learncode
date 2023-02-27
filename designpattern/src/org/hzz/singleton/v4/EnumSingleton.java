package org.hzz.singleton.v4;

public enum EnumSingleton {
    INSTANCE;
     public void print(){
         System.out.println(this.hashCode());
     }
}
