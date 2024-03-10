package com.jl.service.parallelstreams;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static com.jl.util.CommonUtil.*;

public class LinkedListSpliteratorExample {

    public List<Integer> multiplyIntegersByValueSequential(LinkedList<Integer> integerList, int multiplyValue) {

        resetAndStart();
        List<Integer> resultList = integerList.stream().map(it -> it * multiplyValue).toList();
        timeTaken();
        return resultList;
    }

    public List<Integer> multiplyIntegersByValueParallel(LinkedList<Integer> integerList, int multiplyValue) {

        resetAndStart();
        List<Integer> resultList = integerList.stream().parallel().map(it -> it * multiplyValue).toList();
        timeTaken();
        return resultList;
    }
}
