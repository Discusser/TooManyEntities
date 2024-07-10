package io.github.discusser.toomanyentities.config;

public class Wrapper<T> {
    T value;

    public Wrapper(T obj) {
        this.value = obj;
    }

    public static <T> Wrapper<T> of(T obj) {
        return new Wrapper<T>(obj);
    }
}
