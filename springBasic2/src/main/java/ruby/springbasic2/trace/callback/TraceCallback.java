package ruby.springbasic2.trace.callback;

@FunctionalInterface
public interface TraceCallback<T> {
    T call();
}
