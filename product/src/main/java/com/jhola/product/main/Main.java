package com.jhola.product.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class Main {

    public static void main(String[] args) {
        List<Integer> number = Arrays.asList(2, 3, 4, 5);

        List<String> names = Arrays.asList("Reflection", "Collection", "Stream");

        List<Integer> collect = number.stream().map(no -> no + 1).collect(Collectors.toList());
        System.out.println(collect);
    }

}