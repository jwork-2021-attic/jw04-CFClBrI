package com.anish.calabashbros;

public interface Sorter<T extends Comparable<T>> {
    public void load(T[] elements);

    public void sort();

    public String getPlan();
}
