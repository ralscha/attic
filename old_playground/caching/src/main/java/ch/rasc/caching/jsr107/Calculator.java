package ch.rasc.caching.jsr107;

import java.math.BigInteger;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheRemoveAll;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;

import org.springframework.stereotype.Service;

@Service
@CacheDefaults(cacheName = "results")
@SuppressWarnings("unused")
public class Calculator {

	@CacheResult
	public BigInteger factorial(long n) {
		System.out.println("calling factorial method with parameter: " + n);

		BigInteger ret = BigInteger.ONE;
		for (int i = 1; i <= n; ++i) {
			ret = ret.multiply(BigInteger.valueOf(i));
		}

		return ret;
	}

	// Always call method and put result into the cache
	@CacheResult(skipGet = true)
	public BigInteger factorialAlways(@CacheKey long n, boolean notPartOfTheKey) {
		return factorial(n);
	}

	@CachePut
	public void putSomethingIntoTheCache(long n, @CacheValue BigInteger result) {
		// nothing here
	}

	@CacheRemove
	public void removeSomethingFromTheCache(long n) {
		// nothing here
	}

	@CacheRemoveAll
	public void removeEverythingFromTheCache() {
		// nothing here
	}
}
