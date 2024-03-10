package com.jl.service.parallelstreams;

import com.jl.util.ComputeUtil;
import com.jl.util.DataSet;

import java.util.List;
import java.util.stream.Stream;

import static com.jl.util.CommonUtil.startTimer;
import static com.jl.util.CommonUtil.timeTaken;
import static com.jl.util.LoggerUtil.log;

public class ParallelismStreamsExamples {

    public static void main(String[] args) {
        List<String> names = DataSet.namesList();
        ParallelismStreamsExamples examples = new ParallelismStreamsExamples();
        startTimer();
        List<String> mappedNameList = examples.transformNameList(names, true);
        timeTaken();
        log("the result is:  " + mappedNameList);

    }

    private List<String> transformNameList(List<String> nameList, boolean isParallel){
        Stream<String> nameListStream = nameList.stream();
        if (isParallel)
             nameListStream = nameListStream.parallel();


        return nameListStream.map(ComputeUtil::addNameLengthTransform).toList();
    }

    public List<String> string_toLowerCase(List<String> nameList){
        return nameList.stream().map(String::toLowerCase).toList();
    }
}
