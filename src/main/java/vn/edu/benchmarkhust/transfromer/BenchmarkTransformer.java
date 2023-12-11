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
        Utils.copyPropertiesNotNull(benchmark, response);
        return response;
    }

    public Benchmark fromRequest(BenchmarkRequest request) {
        var benchmark = new Benchmark();
        Utils.copyPropertiesNotNull(request, benchmark);
        return benchmark;
    }

    public void setBenchmark(Benchmark benchmark, BenchmarkRequest request) {
        Utils.copyPropertiesNotNull(request, benchmark);
    }
}
