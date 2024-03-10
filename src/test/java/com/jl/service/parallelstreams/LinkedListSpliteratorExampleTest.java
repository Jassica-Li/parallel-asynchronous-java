package com.jl.service.parallelstreams;

import com.jl.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListSpliteratorExampleTest {

    LinkedListSpliteratorExample linkedListSpliteratorExample = new LinkedListSpliteratorExample();

    @RepeatedTest(5)
    public void test_multiply_sequentially() {
        int size = 1000000;
        LinkedList<Integer> numList = DataSet.generateIntegerLinkedList(size);

        List<Integer> resultList = linkedListSpliteratorExample.multiplyIntegersByValueSequential(numList, 2);

        assertEquals(size, resultList.size());
    }

    @RepeatedTest(5)
    public void test_multiply_parallel() {
        int size = 1000000;
        LinkedList<Integer> numList = DataSet.generateIntegerLinkedList(size);

        List<Integer> resultList = linkedListSpliteratorExample.multiplyIntegersByValueParallel(numList, 2);

        assertEquals(size, resultList.size());
    }
}