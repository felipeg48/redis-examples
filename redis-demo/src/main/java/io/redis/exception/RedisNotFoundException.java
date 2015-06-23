package io.redis.exception;

public class RedisNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6198856697401782129L;
	
	public RedisNotFoundException() { super(); }
	public RedisNotFoundException(String s) { super(s); }
}