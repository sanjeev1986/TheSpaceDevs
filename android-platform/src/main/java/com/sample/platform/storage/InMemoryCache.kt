package com.sample.platform.storage

import android.util.LruCache
import javax.inject.Singleton

@Singleton
class InMemoryCache(size: Int = 1 * 1024 * 1024/* Default 1 MB cache*/) {
    private val memoryCache = LruCache<String, Any>(size)
    fun <T> getDataFromCache(key: String): T? {
        return memoryCache[key] as? T
    }

    fun <T> saveToInMemoryCache(key: String, data: T): T {
        memoryCache.put(key, data)
        return data
    }

    fun <T> removeFromCache(key: String) = memoryCache.remove(key) as? T

    fun clear() = memoryCache.evictAll()
}
