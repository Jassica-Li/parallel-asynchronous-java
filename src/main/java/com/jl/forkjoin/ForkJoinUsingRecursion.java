package com.jl.forkjoin;

import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.jl.util.ComputeUtil.addNameLengthTransform;


public class ForkJoinUsingRecursion extends RecursiveTask<List<String>> {

    private List<String> inputList;

    public ForkJoinUsingRecursion(List<String> inputList) {
        this.inputList = inputList;
    }

    @Override
    protected List<String> compute() {
        if (inputList.size() <=1) {
            List<String> resultList = new ArrayList<>();
            inputList.forEach(name -> {
                val result = addNameLengthTransform(name);
                resultList.add(result);

            });
            return resultList;
        }
        int midPoint = inputList.size() / 2;
        ForkJoinTask<List<String>> leftInputList = new ForkJoinUsingRecursion(inputList.subList(0, midPoint)).fork();
        List<String> leftResult = leftInputList.join();
        inputList = inputList.subList(midPoint, inputList.size());
        List<String> rightResult = compute();
        leftResult.addAll(rightResult);
        return leftResult;
    }
}
