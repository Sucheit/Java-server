package ru.collections;

import java.util.*;

public class CustomArrayList<E> {
    private Object[] array;
    private int size;

    public CustomArrayList() {
        array = new Object[10];
        size = 0;
    }

    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format("Index %s is out of bounds", index));
        }
        ensureCapacity(size + 1);
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    public void addAll(Collection<? extends E> c) {
        ensureCapacity(size + c.size());
        for (E element : c) {
            array[size++] = element;
        }
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index %s is out of bounds", index));
        }
        return (E) array[index];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index %s is out of bounds", index));
        }
        E removed = get(index);
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(array, index + 1, array, index, numMoved);
        }
        array[--size] = null;
        return removed;
    }

    public boolean remove(Object o) {
        for (int index = 0; index < size; index++) {
            if (Objects.equals(o, array[index])) {
                remove(index);
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        quickSort((E[]) array, c);
    }

    private void quickSort(E[] arr, Comparator<? super E> c) {
        quickSort(arr, 0, size - 1, c);
    }

    private void quickSort(E[] arr, int begin, int end, Comparator<? super E> c) {
        if (begin >= end) {
            return;
        }

        int pivotIndex = new Random().nextInt(end - begin) + begin;
        E pivot = arr[pivotIndex];

        swap(arr, pivotIndex, end);

        int leftPointer = partition(arr, begin, end, pivot, c);

        quickSort(arr, begin, leftPointer - 1, c);
        quickSort(arr, leftPointer + 1, end, c);
    }

    private int partition(E[] arr, int begin, int end, E pivot, Comparator<? super E> c) {
        int leftPointer = begin;
        int rightPointer = end;

        while (leftPointer < rightPointer) {

            while (c.compare(arr[leftPointer], pivot) <= 0 && leftPointer < rightPointer) {
                leftPointer++;
            }

            while (c.compare(arr[rightPointer], pivot) >= 0 && leftPointer < rightPointer) {
                rightPointer--;
            }

            swap(arr, leftPointer, rightPointer);
        }

        swap(arr, leftPointer, end);
        return leftPointer;
    }

    private void swap(E[] arr, int index1, int index2) {
        E temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    private void ensureCapacity(int minCapacity) {
        int oldCapacity = array.length;
        if (minCapacity > oldCapacity) {
            int newCapacity = oldCapacity + (oldCapacity / 2);
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            array = Arrays.copyOf(array, newCapacity);
        }
    }
}
