package org.hzz.dp;

import java.util.HashSet;
import java.util.Set;

public class GameBagV2 {

    /*放入背包的物品*/
    private static class Element{
        /** 名称 */
        private final String name;
        /** 物品的价值 */
        private final int value;
        /** 物品的花费 */
        private final int cost;
        public Element(String name,  int cost,int value) {
            this.name = name;
            this.value = value;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "Element{" +
                    "name='" + name + '\'' +
                    ", value=" + value +
                    ", cost=" + cost +
                    '}';
        }
    }

    public String findWhich(Element[] goods,int[][] calcArray,int x,int y){
        StringBuilder stringBuilder = new StringBuilder();
        if(x>0){
            if(calcArray[x][y] == calcArray[x-1][y]){
                stringBuilder.append(findWhich(goods,calcArray,x-1,y));
            }else{
                stringBuilder.append(goods[x-1].name+ " ");
                stringBuilder.append(findWhich(goods,calcArray,x-1,y-goods[x-1].cost));
            }
        }
        return stringBuilder.toString();
    }

    public void putBag(Element[] goods,int bagSize){
        int arrayX = goods.length+1;
        int arrayY = bagSize + 1;
        int[][] calcArray = new int[arrayX][arrayY];
        for (int i = 0; i < arrayX; i++) {
            for (int j = 0; j < arrayY; j++) {
                if (i == 0){ /** 对第一行数据做特殊处理*/
                    calcArray[i][j] = 0;
                }else{
                    int goodsIndex = i - 1;
                    /*计算本单元格是否能放下当前物品*/
                    int spareSpace = j-goods[goodsIndex].cost;
                    int preRow = i - 1;
                    /*上一行同列的值*/
                    int preRowValue = calcArray[preRow][j];
                    /*当前商品的价值*/
                    int currentGoodsValue = goods[goodsIndex].value;
                    if(spareSpace < 0){/*放不下，直接使用上一行同列的数据*/
                        calcArray[i][j] = calcArray[i-1][j];
                    }else{
                        /*是否有剩余空间，如果有，获得剩余空间最大价值*/
                        if(spareSpace>0) currentGoodsValue += calcArray[preRow][spareSpace];
                        calcArray[i][j] = Math.max(preRowValue,currentGoodsValue);
                    }
                }
            }
        }
        System.out.println("最终结果： "+calcArray[arrayX-1][arrayY-1]);
        System.out.println(findWhich(goods,calcArray,arrayX-1,arrayY-1));
    }

    public static void main(String[] args) {
        Element[] tourElements = {new Element("天安门广场",1,9),
                new Element("故宫",4,9),
                new Element("颐和园",2,9),
                new Element("八达岭长城",1,7),
                new Element("天坛",1,6),
                new Element("圆明园",2,8),
                new Element("恭王府",1,5)};

        new GameBagV2().putBag(tourElements,6);
    }
}
/**
 最终结果： 36
 恭王府 天坛 八达岭长城 颐和园 天安门广场
 */
