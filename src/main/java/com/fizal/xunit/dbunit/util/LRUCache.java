package com.fizal.xunit.dbunit.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Comment here about the class
 * User: Fizal
 * Date: 9/27/2016
 * Time: 10:52 PM
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int maximumCapacity;

    public LRUCache(int maximumCapacity) {
        super(1 << 4, 0.75f, true); //access order = true
        this.maximumCapacity = maximumCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maximumCapacity;
    }


}