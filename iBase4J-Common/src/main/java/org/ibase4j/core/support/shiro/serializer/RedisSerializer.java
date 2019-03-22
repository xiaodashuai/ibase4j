package org.ibase4j.core.support.shiro.serializer;


import org.ibase4j.core.support.shiro.exception.SerializationException;

public interface RedisSerializer<T> {

    byte[] serialize(T t) throws SerializationException;

    T deserialize(byte[] bytes) throws SerializationException;
}
