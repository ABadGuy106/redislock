package net.abadguy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


@Service
public class RedisLock implements Lock {


    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    private static final String LOCK="LOCK";

    public void lock() {
        if(!tryLock()){
            try {
                Thread.sleep(new Random().nextInt(10)+1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock();
        }
    }


    public void unlock() {

    }


    public boolean tryLock() {
        Jedis jedis= (Jedis) jedisConnectionFactory.getConnection().getNativeConnection();
        String value= UUID.randomUUID().toString();
        String ret=jedis.set(LOCK,value,"NX","PX",3000);
        if(ret!=null&& ret.equals("OK")){
            return true;
        }
        return false;
    }


    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    public void lockInterruptibly() throws InterruptedException {

    }



    public Condition newCondition() {
        return null;
    }
}
