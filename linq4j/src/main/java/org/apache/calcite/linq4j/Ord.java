/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.linq4j;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

/**
 * Pair of an element and an ordinal.
 *
 * @param <E> Element type
 */
public class Ord<E> implements Map.Entry<Integer, E> {
  public final int i;
  public final E e;

  /**
   * Creates an Ord.
   */
  public Ord(int i, E e) {
    this.i = i;
    this.e = e;
  }

  /**
   * Creates an Ord.
   */
  public static <E> Ord<E> of(int n, E e) {
    return new Ord<E>(n, e);
  }

  /**
   * Creates an iterable of {@code Ord}s over an iterable.
   */
  public static <E> Iterable<Ord<E>> zip(final Iterable<? extends E> iterable) {
    return new Iterable<Ord<E>>() {
      public Iterator<Ord<E>> iterator() {
        return zip(iterable.iterator());
      }
    };
  }

  /**
   * Creates an iterator of {@code Ord}s over an iterator.
   */
  public static <E> Iterator<Ord<E>> zip(final Iterator<? extends E> iterator) {
    return new Iterator<Ord<E>>() {
      int n = 0;

      public boolean hasNext() {
        return iterator.hasNext();
      }

      public Ord<E> next() {
        return Ord.of(n++, iterator.next());
      }

      public void remove() {
        iterator.remove();
      }
    };
  }

  /**
   * Returns a numbered list based on an array.
   */
  public static <E> List<Ord<E>> zip(final E[] elements) {
    return new OrdArrayList<E>(elements);
  }

  /**
   * Returns a numbered list.
   */
  public static <E> List<Ord<E>> zip(final List<? extends E> elements) {
    return elements instanceof RandomAccess
        ? new OrdRandomAccessList<E>(elements)
        : new OrdList<E>(elements);
  }

  public Integer getKey() {
    return i;
  }

  public E getValue() {
    return e;
  }

  public E setValue(E value) {
    throw new UnsupportedOperationException();
  }

  /** List of {@link Ord} backed by a list of elements. */
  private static class OrdList<E> extends AbstractList<Ord<E>> {
    private final List<? extends E> elements;

    public OrdList(List<? extends E> elements) {
      this.elements = elements;
    }

    public Ord<E> get(int index) {
      return of(index, elements.get(index));
    }

    public int size() {
      return elements.size();
    }
  }

  /** List of {@link Ord} backed by a random-access list of elements. */
  private static class OrdRandomAccessList<E> extends OrdList<E>
      implements RandomAccess {
    public OrdRandomAccessList(List<? extends E> elements) {
      super(elements);
    }
  }

  /** List of {@link Ord} backed by an array of elements. */
  private static class OrdArrayList<E> extends AbstractList<Ord<E>>
      implements RandomAccess {
    private final E[] elements;

    public OrdArrayList(E[] elements) {
      this.elements = elements;
    }

    @Override public Ord<E> get(int index) {
      return Ord.of(index, elements[index]);
    }

    @Override public int size() {
      return elements.length;
    }
  }
}

// End Ord.java
