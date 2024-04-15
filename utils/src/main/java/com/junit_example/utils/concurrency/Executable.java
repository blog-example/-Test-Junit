package com.junit_example.utils.concurrency;


@FunctionalInterface
public interface Executable<T> {
  T execute();
}
