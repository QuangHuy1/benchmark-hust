package vn.edu.benchmarkhust.transfromer;

import org.springframework.stereotype.Component;
import vn.edu.benchmarkhust.model.entity.Benchmark;
import vn.edu.benchmarkhust.model.request.BenchmarkRequest;
import vn.edu.benchmarkhust.model.response.BenchmarkResponse;
import vn.edu.benchmarkhust.utils.Utils;

@Component
public class BenchmarkTransformer {

    public BenchmarkResponse toResponse(Benchmark benchmark) {
        var response = new BenchmarkResponse();
        response.setId(benchmark.getId());
        response.setYear(benchmark.getYear());
        response.setScore(benchmark.getScore());
        response.setGroupType(benchmark.getGroupType());

        return response;
    }

    public Benchmark fromRequest(BenchmarkRequest request) {
        var benchmark = new Benchmark();
        benchmark.setYear(request.getYear());
        benchmark.setScore(request.getScore());
        benchmark.setGroupType(request.getGroupType());
        benchmark.setGroupType(request.getGroupType());

        return benchmark;
    }

    public void setBenchmark(Benchmark benchmark, BenchmarkRequest request) {
        if (request.getYear() != null) {
            benchmark.setYear(request.getYear());
        }

        if (request.getScore() != null) {
            benchmark.setScore(request.getScore());
        }

        if (request.getGroupType() != null) {
            benchmark.setGroupType(request.getGroupType());
        }
    }
}
