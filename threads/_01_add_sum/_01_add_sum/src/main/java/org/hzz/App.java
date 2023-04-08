package org.hzz;

public class App {
    public static void main(String[] args) {
        // generate number array 1-100
        int[] numbers = generateNumber(100);

        int split = numbers.length / 2;
        ResolveResult firstHandler = new ThreadResolveResult(new StandardResolveResult(numbers,0,split));
        ResolveResult secondHandler = new StandardResolveResult(numbers,split,numbers.length);
        int secondResult = secondHandler.resolve();
        int firstResult = firstHandler.resolve();
        int total = firstResult + secondResult;
        System.out.println("Total: "+ total);
        if(total == 5050){
            System.out.println("结果正确");
        }
    }


    public static int[] generateNumber(int max){
        int[] numbers = new int[max];
        for (int i=0;i<max;i++){
            numbers[i] = i+1;
        }
        return numbers;
    }
}
/**
 * Total: 5050
 * 结果正确
 */