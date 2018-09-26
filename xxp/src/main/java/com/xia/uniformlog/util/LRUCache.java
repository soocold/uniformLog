package com.xia.uniformlog.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 简单的LRUCache
 *
 * @author xia
 * @since 2017/12/10 20:53
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private static final long serialVersionUID = -3191244541659063948L;
    /**
     * 最大缓存大小
     */
    private static final int MAX_CACHE_SIZE = 1000;
    /**
     * map的负载因子
     */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    /**
     * map的初始化大小
     */
    private static final int INIT_SIZE = 16;

    public LRUCache() {
        super(INIT_SIZE, DEFAULT_LOAD_FACTOR, true);
    }

    @Override
    public boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > MAX_CACHE_SIZE;
    }
}
