package org.hzz.dp;

public class GameBagV3 {

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

    private void printRow(int[] calcArray){
        int arrayY = 0;
        for(int j:calcArray){
            System.out.print("["+arrayY+++"]="+j+" ,");
        }
        System.out.println("");
    }

    public void putBag(Element[] goods,int bagSize){
        int[] calcArray = new int[bagSize+1];
        calcArray[0] = 0;
        for (int goodsIndex = 0; goodsIndex < goods.length; goodsIndex++) {
            int goodsCost = goods[goodsIndex].cost;
            System.out.println(goods[goodsIndex].name+"：空间耗费："+goodsCost+"，价值："
                    +goods[goodsIndex].value);

            for (int currentSize = calcArray.length-1; currentSize >=0 ; currentSize--) {
                System.out.println("目前背包大小："+currentSize);
                if(currentSize>=goodsCost){
                    calcArray[currentSize] = Math.max(
                      calcArray[currentSize],
                      calcArray[currentSize-goodsCost] + goods[goodsIndex].value
                    );
                }
                printRow(calcArray);
            }
            System.out.println("------------------------------------");
        }

        System.out.println("最终结果： "+calcArray[bagSize]);
    }

    public static void main(String[] args) {
        Element[] tourElements = {new Element("天安门广场",1,9),
                new Element("故宫",4,9),
                new Element("颐和园",2,9),
                new Element("八达岭长城",1,7),
                new Element("天坛",1,6),
                new Element("圆明园",2,8),
                new Element("恭王府",1,5)};

        new GameBagV3().putBag(tourElements,6);
    }
}
