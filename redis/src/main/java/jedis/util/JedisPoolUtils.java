package jedis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * jedis连接池工具类
 */
public class JedisPoolUtils {
    private static JedisPool jedisPool;

    static{
        //读取配置文件
        InputStream in = JedisPoolUtils.class.getClassLoader().getResourceAsStream("jedis.properties");
        //创建properties对象
        Properties pro = new Properties();
        try {
            pro.load(in);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取数据，设置的JedisPoolConfig中
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(pro.getProperty("maxTotal")));
        config.setMaxIdle(Integer.parseInt(pro.getProperty("maxIdle")));

        //初始化JedisPool
        jedisPool = new JedisPool(config,pro.getProperty("host"),Integer.parseInt(pro.getProperty("port")));
    }

    /**
     * 获取链接方法
     * @return 返回jedis连接池
     */
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

    /**
     * 关闭链接的方法
     */
    public static void close(){
        jedisPool.getResource().close();
    }
}
