package com.jl.service.parallelstreams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.jl.util.CommonUtil.resetAndStart;
import static com.jl.util.CommonUtil.timeTaken;

public class ArrayListSpliteratorExample {

    public List<Integer> multiplyIntegersByValue(ArrayList<Integer> integerList, int multiplyValue, Boolean isParallel) {

        resetAndStart();
        Stream<Integer> integerStream = integerList.stream();

        List<Integer> resultList;
        if (isParallel) {
            resultList = integerStream.parallel().map(it -> it * multiplyValue).toList();
        } else {
            resultList = integerStream.map(it -> it * multiplyValue).toList();
        }
        timeTaken();
        return resultList;
    }
}
