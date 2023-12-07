package vn.edu.benchmarkhust.exception;

public interface ErrorCode<S> {
    String message();

    String code();

    S status();
}
