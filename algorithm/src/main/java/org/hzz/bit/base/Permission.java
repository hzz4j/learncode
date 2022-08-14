package org.hzz.bit.base;

/**
 * 位运算的运用-权限控制,add,query,modify,del
 */
public class Permission {

    private static final int ALLOW_SELECT = 1<<0;
    private static final int ALLOW_INSERT = 1<<1;
    private static final int ALLOW_UPDATE = 1<<2;
    private static final int ALLOW_DELETE = 1<<3;

    //当前的权限状态
    private int flag;

    public void setPermission(int permission){
        flag = permission;
    }


    /*增加权限,可以一项或者多项*/
    public void addPermission(int permission){
        flag = flag|permission;
    }

    /*删除权限,可以一项或者多项*/
    public void disablePermission(int permission){
        flag = flag&~permission;
    }

    /*是否拥有某些权限*/
    public boolean isAllow(int permission){
        return (flag&permission)==permission;
    }

    /*是否不拥有某些权限*/
    public boolean isNotAllow(int permission){
        return (flag&permission)==0;
    }


    public static void main(String[] args) {
        int flag = 15;
        System.out.println(Integer.toBinaryString(flag));
        Permission permission = new Permission();
        permission.setPermission(flag);
        permission.disablePermission(ALLOW_DELETE|ALLOW_INSERT);
        System.out.println("ALLOW_SELECT="+permission.isAllow(ALLOW_SELECT));
        System.out.println("ALLOW_INSERT="+permission.isAllow(ALLOW_INSERT));
        System.out.println("ALLOW_UPDATE="+permission.isAllow(ALLOW_UPDATE));
        System.out.println("ALLOW_DELETE="+permission.isAllow(ALLOW_DELETE));
    }
}
/**
 * 1111
 * ALLOW_SELECT=true
 * ALLOW_INSERT=false
 * ALLOW_UPDATE=true
 * ALLOW_DELETE=false
 */
