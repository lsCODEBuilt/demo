package com.test.plugins.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Redis Service
 *
 * @author misselvexu
 */

@Configuration
public class RedisService {

    private static String redisCode = "utf-8";

    @Autowired
    private RedisTemplate redisTemplate;

    public RedisService() {
    }

    /**
     * del keys
     *
     * @param keys keys
     * @return rows
     */
    public long del(final String... keys) {
        return (long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long result = 0;
                for (String key : keys) {
                    result = connection.del(key.getBytes());
                }
                return result;
            }
        });
    }

    /**
     * set value
     *
     * @param key key
     * @param value value
     * @param liveTime ttl
     */
    public void set(final byte[] key, final byte[] value, final long liveTime) {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }

    /**
     * set value
     *
     * @param key key
     * @param value value
     * @param liveTime ttl (秒)
     */
    public void set(String key, String value, long liveTime) {
        this.set(key.getBytes(), value.getBytes(), liveTime);
    }

    /**
     * set value
     *
     * @param key key
     * @param value value
     */
    public void set(String key, String value) {
        this.set(key, value, 0L);
    }

    /**
     * set value
     *
     * @param key key
     * @param value value
     */
    public void set(byte[] key, byte[] value) {
        this.set(key, value, 0L);
    }

    /**
     * set Object
     *
     * @param key key
     * @param value object value
     */
    public void setObject(String key, Object value) {
        this.setObject(key, value, 0L);
    }



    /**
     * set object
     *
     * @param key key
     * @param obj object
     * @param liveTime ttl
     */
    public void setObject(String key, Object obj, long liveTime) {
        try {
            this.set(key.getBytes(), serializeStorageObj(obj), liveTime);
        } catch (IOException e) {
        }
    }

    /**
     * get value by key
     *
     * @param key key
     * @return return value
     */
    public String get(final String key) {
        return (String) redisTemplate.execute(new RedisCallback() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return new String(connection.get(key.getBytes()), redisCode);
                } catch (Exception e) {
                }
                return "";
            }
        });
    }

    /**
     * Get Object
     *
     * @param key key
     * @return object value
     */
    public Object getObject(final String key) {
        return redisTemplate.execute((RedisConnection connection) -> {
            try {
                return deSerializeStorageObj(connection.get(key.getBytes()));
            } catch (Exception e) {
            }
            return "";
        });
    }

    private byte[] serializeStorageObj(Object obj) throws IOException {
        byte[] bytes = null;
        if (obj != null) {
            ByteArrayOutputStream baos = null;
            ObjectOutputStream oos = null;
            try {
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                bytes = baos.toByteArray();
            } catch (IOException e) {
                throw e;
            } finally {
                if (oos != null) {
                    oos.close();
                }
                if (baos != null) {
                    baos.close();
                }
            }
        }
        return bytes;
    }

    private Object deSerializeStorageObj(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object obj = ois.readObject();
        ois.close();
        bais.close();
        return obj;
    }

    /**
     * check exist
     *
     * @param key key
     * @return return true exist otherwise not exist
     */
    public boolean exists(final String key) {
        return (boolean) redisTemplate.execute(new RedisCallback() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }

    /**
     * flush db
     *
     * @return return 'ok'
     */
    public String flushDB() {
        return (String) redisTemplate.execute(new RedisCallback() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    /**
     * Get db size
     *
     * @return reutrn db size
     */
    public long dbSize() {
        return (long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    /**
     * Ping test
     *
     * @return Server response message - usually {@literal PONG}.
     */
    public String ping() {
        return (String) redisTemplate.execute(new RedisCallback() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {

                return connection.ping();
            }
        });
    }

    /**
     * hset value
     *
     * @param key key
     * @param field field
     * @param value value
     */
    public void hSet(String key, String field, String value) {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hSet(key.getBytes(), field.getBytes(), value.getBytes());
            }
        });
    }

    /**
     * Hset value
     *
     * @param key key
     * @param field field
     * @param value value
     * @param liveTime ttl
     */
    public void hSet(String key, String field, String value, Long liveTime) {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                Boolean isSuccess = connection.hSet(key.getBytes(), field.getBytes(), value.getBytes());
                if (liveTime > 0) {
                    connection.expire(key.getBytes(), liveTime);
                }
                return isSuccess;
            }
        });
    }

    /**
     * Hget value by field and key
     *
     * @param key key
     * @param field field
     * @return return value
     */
    public String hGet(String key, String field) {
        return (String) redisTemplate.execute(new RedisCallback() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                if (exists(key)) {
                    return new String(connection.hGet(key.getBytes(), field.getBytes()));
                } else {
                    return null;
                }

            }
        });
    }

    /**
     * Hget All values
     *
     * @param key key
     * @return return values
     */
    public Map<String, String> hGetAll(String key) {
        return (Map<String, String>) redisTemplate.execute(new RedisCallback() {
            @Override
            public Map<String, String> doInRedis(RedisConnection connection) throws DataAccessException {
                if (exists(key)) {
                    Map<byte[], byte[]> map = connection.hGetAll(key.getBytes());
                    Map<String, String> resMap = new HashMap<>();
                    map.forEach((mapKey, mapValue) -> resMap.put(new String(mapKey), new String(mapValue)));
                    return resMap;
                } else {
                    return new HashMap<>();
                }

            }
        });
    }

    /**
     * 查询过期时间
     *
     * @param key key
     * @return 剩余过期时间second
     */
    public Long ttl(String key) {
        Object object = redisTemplate.execute(
                (RedisConnection connection) -> connection.ttl(key.getBytes()), true);
        if (object == null) {
            return null;
        } else {
            return (Long) object;
        }
    }
    /**
     * incr将 key 中储存的数字值增一，如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     *
     * @return reutrn incr Long
     */
    public Long incr(String key) {
        return (Long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incr(key.getBytes());
            }
        });
    }

    /**
     * incrBy将 key 中储存的数字加上指定的增量值，如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     *
     * @return reutrn incrBy Long
     */
    public long incrBy(String key,Long value) {
        return (long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incrBy(key.getBytes(),value);
            }
        });
    }

    /**
     * incrBy为 key 中所储存的值加上浮点数增量 increment ，如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     *
     * @return reutrn incrBy Double
     */
    public Double incrBy(String key,Double value) {
        return (Double) redisTemplate.execute(new RedisCallback() {
            @Override
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incrBy(key.getBytes(),value);
            }
        });
    }

    /**
     * incr将 key 中储存的数字值减一，如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     *
     * @return reutrn incr Long
     */
    public Long decr(String key) {
        return (Long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.decr(key.getBytes());
            }
        });
    }

    /**
     * incrBy将 key 所储存的值减去减量 decrement ，如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     *
     * @return reutrn incrBy Long
     */
    public Long decrBy(String key,Long value) {
        return (Long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.decrBy(key.getBytes(),value);
            }
        });
    }

    /**
     * publish 将信息 message 发送到指定的频道 channel 。
     *
     * @return reutrn publish Long
     */
    public Long publish(String channel, String message){
        return  (Long) redisTemplate.execute((RedisConnection connection) ->
        {
            return connection.publish(channel.getBytes(),message.getBytes());
        },true);
    }
    /**
     * subscribe 订阅给定的一个或多个频道的信息。
     *
     * @return reutrn
     */
    public void subscribe(MessageListener listener, String channel){
        redisTemplate.execute((RedisConnection connection) ->
        {
            connection.subscribe(listener,channel.getBytes());
            return  true;
        },true);
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表头
     *
     * @return reutrn lPush Long
     */
    public Long lPush(String channel, String message){
        return  (Long) redisTemplate.execute((RedisConnection connection) ->
        {
            return connection.lPush(channel.getBytes(),message.getBytes());
        },true);
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表头
     *
     * @return reutrn lPush Long
     */
    public Long lPushObject(String channel, Object message){
        return  (Long) redisTemplate.execute((RedisConnection connection) ->
        {
            try {
                return connection.lPush(channel.getBytes(),serializeStorageObj(message));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return connection;
        },true);
    }

    /**
     * 移除并返回列表 key 的尾元素。
     *
     * @return reutrn rPop Long
     */
    public String rPop(String channel){
        return  (String) redisTemplate.execute((RedisConnection connection) ->
        {
            byte[] obj = connection.rPop(channel.getBytes());
            if ( obj != null)
                return new String(obj);
            else
                return  null;
        },true);
    }

    /**
     *  RPOP 的阻塞版本
     *
     * @return reutrn rPop Long
     */
    public String bRPop(String channel){
        return  (String) redisTemplate.execute((RedisConnection connection) ->
        {
            List<byte[]> obj = connection.bRPop( 0,channel.getBytes());
            if ( obj != null)
                return new String(obj.get(1));
            else
                return  null;
        },true);
    }
}