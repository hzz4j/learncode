package org.hzz.prototype;

import java.io.*;

public class Product implements Cloneable, Serializable {
    private String part1;
    private String part2;
    private String part3;
    private String part4;
    // 自定义数据类型
    private BaseInfo baseInfo;

    public Product(String part1, String part2, String part3, String part4, BaseInfo baseInfo) {
        this.part1=part1;
        this.part2=part2;
        this.part3=part3;
        this.part4=part4;
        this.baseInfo=baseInfo;
    }

    public String getPart1() {
        return part1;
    }

    public void setPart1(String part1) {
        this.part1=part1;
    }

    public String getPart2() {
        return part2;
    }

    public void setPart2(String part2) {
        this.part2=part2;
    }

    public String getPart3() {
        return part3;
    }

    public void setPart3(String part3) {
        this.part3=part3;
    }

    public String getPart4() {
        return part4;
    }

    public void setPart4(String part4) {
        this.part4=part4;
    }


    public BaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfo baseInfo) {
        this.baseInfo=baseInfo;
    }

    @Override
    public String toString() {
        return "Product{" +
                "part1='" + part1 + '\'' +
                ", part2='" + part2 + '\'' +
                ", part3='" + part3 + '\'' +
                ", part4='" + part4 + '\'' +
                ", baseInfo=" + baseInfo +
                '}';
    }

    @Override
    protected Product clone() throws CloneNotSupportedException {
//        return useSerializClone();
        return useJVMClone();
    }

    /**
     * 利用jvm克隆机制完成的深拷贝
     */
    private Product useJVMClone() throws CloneNotSupportedException {
        Product product = (Product) super.clone();
        BaseInfo baseInfo = (BaseInfo)this.baseInfo.clone();
        product.setBaseInfo(baseInfo);
        return product;
    }

    private Product useSerializClone(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try(ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

        try(ObjectInputStream ois = new ObjectInputStream(bis)){
            Product product = (Product) ois.readObject();
            return product;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
