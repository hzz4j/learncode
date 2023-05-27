package org.hzz;

import io.vavr.control.Either;

import java.util.HashMap;
import java.util.Map;

public class EitherTest {
    public static void main(String[] args) {
        // a = 100
        Integer a = compute(100).right().getOrElseThrow(x -> new RuntimeException(x));
        // b = Marks not acceptable
        String b = compute(60).left().getOrNull();
        // c = null
        Integer c = compute(10).right()
                .map(x -> x + 1)
                .getOrNull();

        Either<Object, Integer> right = Either.right(5);
        // 统一结果
        // result = right:5
        String result = right.fold(
                l -> "left: " + l,
                r -> "right:" + r
        );
        System.out.println(result);
    }

    static Either<String,Integer> compute(int marks){
        if(marks < 85){
            return Either.left("Marks not acceptable");
        }else{
            return Either.right(marks);
        }
    }

    // 以下是不使用Either,纯粹靠java自带的功能实现
    public static Object[] computeWithoutEitherUsingArray(int marks) {
        Object[] results = new Object[2];
        if (marks < 85) {
            results[0] = "Marks not acceptable";
        } else {
            results[1] = marks;
        }
        return results;
    }

    public static Map<String, Object> computeWithoutEitherUsingMap(int marks) {
        Map<String, Object> results = new HashMap<>();
        if (marks < 85) {
            results.put("FAILURE", "Marks not acceptable");
        } else {
            results.put("SUCCESS", marks);
        }
        return results;
    }
}
