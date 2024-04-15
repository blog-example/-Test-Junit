package com.junit.utils.concurrency;


@FunctionalInterface
public interface Executable<T> {
  T execute();
}
