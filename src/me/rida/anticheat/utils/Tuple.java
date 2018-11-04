package me.rida.anticheat.utils;

import java.util.Map;

public class Tuple<K, V> implements Map.Entry<K, V> {

    private K key;
    private V value;

    public Tuple(K paramK, V paramV)
    {
        this.key = paramK;
        this.value = paramV;
    }

    @Override
    public K getKey() {
        return null;
    }

    @Override
    public V getValue() {
        return null;
    }

    @Override
    public V setValue(V value) {
        return null;
    }
}
