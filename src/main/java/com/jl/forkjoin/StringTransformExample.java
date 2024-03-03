package com.jl.forkjoin;

import com.jl.util.DataSet;

import java.util.ArrayList;
import java.util.List;

import static com.jl.util.CommonUtil.delay;
import static com.jl.util.CommonUtil.stopWatch;
import static com.jl.util.ComputeUtil.addNameLengthTransform;
import static com.jl.util.LoggerUtil.log;

public class StringTransformExample {

    public static void main(String[] args) {

        stopWatch.start();
        List<String> resultList = new ArrayList<>();
        List<String> names = DataSet.namesList();
        log("names : "+ names);

        names.forEach((name)->{
            String newValue = addNameLengthTransform(name);
            resultList.add(newValue);
        });
        stopWatch.stop();
        log("Final Result : "+ resultList);
        log("Total Time Taken : "+ stopWatch.getTime());
    }


}
