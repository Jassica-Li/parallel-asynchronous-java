package com.jl.service.thread.forkjoin;

import com.jl.util.DataSet;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static com.jl.util.CommonUtil.stopWatch;
import static com.jl.util.LoggerUtil.log;

public class StringTransformUsingForkJoinRecursion {

    public static void main(String[] args) {

        stopWatch.start();
        ForkJoinUsingRecursion forkJoinUsingRecursion = new ForkJoinUsingRecursion(DataSet.namesList());
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<String> resultList = forkJoinPool.invoke(forkJoinUsingRecursion);
        stopWatch.stop();
        log("Final Result : "+ resultList);
        log("Total Time Taken : "+ stopWatch.getTime());
    }

}

