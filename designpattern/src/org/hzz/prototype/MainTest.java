package org.hzz.prototype;

public class MainTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        BaseInfo baseInfo = new BaseInfo("Q10Viking");
        Product product=new Product( "part1", "part2", "part3", "part4", baseInfo );
        Product clone= product.clone();

        System.out.println( "original: " + product );
        System.out.println( "clone:  " + clone );
        product.getBaseInfo().setCompanyName( "HZZ Come ON" );
        product.setPart1("rename part1");
        System.out.println( "original: " + product );
        System.out.println( "clone:  " + clone );
    }
}
/**
 * original: Product{part1='part1', part2='part2', part3='part3', part4='part4', baseInfo=BaseInfo{companyName='Q10Viking'}}
 * clone:  Product{part1='part1', part2='part2', part3='part3', part4='part4', baseInfo=BaseInfo{companyName='Q10Viking'}}
 * original: Product{part1='rename part1', part2='part2', part3='part3', part4='part4', baseInfo=BaseInfo{companyName='HZZ Come ON'}}
 * clone:  Product{part1='part1', part2='part2', part3='part3', part4='part4', baseInfo=BaseInfo{companyName='Q10Viking'}}
 */