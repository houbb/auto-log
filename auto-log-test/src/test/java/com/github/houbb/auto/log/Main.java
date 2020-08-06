package com.github.houbb.auto.log;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class Main {

    public static void main(String[] args) {
        int result = div(10, 0);
        System.out.println(result);
    }

    public static int div(int a, int b) {
        return (int) (a / (float) b);
    }

}
