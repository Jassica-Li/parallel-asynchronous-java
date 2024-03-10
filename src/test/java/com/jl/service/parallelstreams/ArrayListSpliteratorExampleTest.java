package com.jl.service.parallelstreams;

import com.jl.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayListSpliteratorExampleTest {

    ArrayListSpliteratorExample arrayListSpliteratorExample = new ArrayListSpliteratorExample();

    @RepeatedTest(5)
    public void test_multiply_sequentially() {
        int size = 1000000;
        ArrayList<Integer> numList = DataSet.generateArrayList(size);

        List<Integer> resultList = arrayListSpliteratorExample.multiplyIntegersByValue(numList, 2, false);

        assertEquals(size, resultList.size());
    }

    @RepeatedTest(5)
    public void test_multiply_parallel() {
        int size = 1000000;
        ArrayList<Integer> numList = DataSet.generateArrayList(size);

        List<Integer> resultList = arrayListSpliteratorExample.multiplyIntegersByValue(numList, 2, true);

        assertEquals(size, resultList.size());
    }

}