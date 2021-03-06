package org.efix.util.concurrent.queue;

import java.util.function.Consumer;


public interface Queue<E> {

    boolean offer(E e);

    E poll();

    int drain(Consumer<E> handler);

    int capacity();

    int size();

    boolean isEmpty();

}
