package jedis;

import jedis.util.JedisPoolUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisTest {
    @Test
    public void jedis(){
        //注册驱动
        Jedis jedis = new Jedis("localhost",6379);
        //操作
        jedis.set("username","zhangsan");
        //关闭连接
        jedis.close();
    }

    /**
     * String数据结构
     */
    @Test
    public void string(){
        //注册驱动
        Jedis jedis = new Jedis("localhost",6379);//不写默认就是本机端口号和本机IP地址
        //操作
        jedis.set("username","zhangsan");
        String username = jedis.get("username");
        System.out.println(username);
        //关闭连接
        //可以使用setex()方法储存可以指定过期时间的 key value
        jedis.setex("activeCode",20,"1234"); //20秒后自动删除
        jedis.close();
    }

    /**
     * hash数据结构
     */
    @Test
    public void hash(){
        //注册驱动
        Jedis jedis = new Jedis("localhost",6379);//不写默认就是本机端口号和本机IP地址
        //操作
        jedis.hset("user","name","zhangsan");
        jedis.hset("user","age","20");
        jedis.hset("user","gender","man");
        String name = jedis.hget("user", "name");
        System.out.println(name);
        Map<String, String> user = jedis.hgetAll("user");
        Set<Map.Entry<String, String>> entrySet = user.entrySet();
        Set<String> keySet = user.keySet();
        for (String key : keySet) {
            //获取value
            String value = user.get(key);
            System.out.println(key+":"+value);
        }
        //关闭连接
        jedis.close();
    }

    /**
     * list数据结构
     */
    @Test
    public void list(){
        //注册驱动
        Jedis jedis = new Jedis();//不写默认就是本机端口号和本机IP地址
        //操作
        jedis.lpush("list","a","b","c");//左边加入 顺序cba
        jedis.rpush("list","a","b","c");//右边加入 顺序abc
        List<String> list = jedis.lrange("list", 0, -1);
        System.out.println(list);// cba abc

        //弹出元素，即删除
        String element1 = jedis.lpop("list");
        System.out.println(element1);//弹出后获取到弹出的返回值 c
        //弹出元素，即删除
        String element2 = jedis.rpop("list");
        System.out.println(element2);//弹出后获取到弹出的返回值 c

        //弹出之后在此获取所有元素
        List<String> list1 = jedis.lrange("list", 0, -1);
        System.out.println(list1);// ba ab
        //关闭连接
        jedis.close();
    }

    /**
     * set数据结构
     */
    @Test
    public void set(){
        //注册驱动
        Jedis jedis = new Jedis();//不写默认就是本机端口号和本机IP地址
        //操作
        jedis.sadd("set","java","php","c++");
        Set<String> set = jedis.smembers("set");
        System.out.println(set);
        //关闭连接
        jedis.close();
    }

    /**
     * sortedset数据结构
     */
    @Test
    public void sortedSet(){
        //注册驱动
        Jedis jedis = new Jedis();//不写默认就是本机端口号和本机IP地址
        //操作
        jedis.zadd("sortedSet",1,"诸葛亮");
        jedis.zadd("sortedSet",3,"司马懿");
        jedis.zadd("sortedSet",2,"嫦娥");
        Set<String> sortedSet = jedis.zrange("sortedSet", 0, -1);
        System.out.println(sortedSet);
        //关闭连接
        jedis.close();
    }

    /**
     * JedisPool连接池的使用
     */
    @Test
    public void jedisPool(){
        //创建配置对象，也可以不创建使用默认的
        JedisPoolConfig config = new JedisPoolConfig();
        //最大链接数
        config.setMaxTotal(50);
        //空闲连接数
        config.setMaxIdle(10);
        //创建连接池对象
        JedisPool jedisPool = new JedisPool(config,"localhost",6379);
        //获取链接
        Jedis jedis = jedisPool.getResource();
        //操作
        jedis.set("heheh","hahah");

        //关闭，归还到连接池中
        jedis.close();

    }

    /**
     * Jedis工具类的使用
     */
    @Test
    public void jedisUtils(){
      //通过连接池工具类获取
        Jedis jedis = JedisPoolUtils.getJedis();
        //使用
        jedis.set("hello","gun");
        System.out.println(jedis.get("hello"));
        //关闭链接
        jedis.close();
    }
}



