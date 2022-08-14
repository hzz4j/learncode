package org.hzz.string;

import java.util.Deque;
import java.util.LinkedList;

public class ValidParentheses_20 {
    public boolean isValid(String s) {
        Deque<Character> stack = new LinkedList<>();
        for (char c :
                s.toCharArray()) {
            if(c == '(') stack.push(')');
            else if(c == '[') stack.push(']');
            else if(c == '{') stack.push('}');
            else if(stack.isEmpty() || stack.pop() != c){
                return false;
            }
        }
        return stack.isEmpty();
    }
}
