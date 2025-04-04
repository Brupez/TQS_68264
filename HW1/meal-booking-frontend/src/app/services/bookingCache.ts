interface CacheEntry<T> {
    data: T;
    timestamp: number;
    ttl: number;
}

class SimpleCache {
    private cache = new Map<string, CacheEntry<any>>();

    set<T>(key: string, data: T, ttl: number = 300000) { // Default 5 minutes
        this.cache.set(key, {
            data,
            timestamp: Date.now(),
            ttl
        });
    }

    get<T>(key: string): T | null {
        const entry = this.cache.get(key);
        if (!entry) return null;

        // Check if cache entry is expired
        if (Date.now() > entry.timestamp + entry.ttl) {
            this.cache.delete(key);
            return null;
        }

        return entry.data as T;
    }

    delete(key: string) {
        this.cache.delete(key);
    }

    clear() {
        this.cache.clear();
    }
}

export const bookingCache = new SimpleCache();