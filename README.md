# ID自增生成器
> 高并发、分布式环境下，生成全局主键(通常数字类型，字符串不在讨论范围内)，需要满足一些要求（个人总结）
1. 不能有单点故障，高可用
2. 以时间为序，ID有序
3. 方便分片
4. 不要太长，一般最多64位，对应long类型
5. 性能要足够好，不能成为系统瓶颈

#### Flickr
> Flickr 的实现策略：通过至少两台MySQL构成一个高可用集群，分别设置其自增步长、和自增偏移量，用于分散ID的生成，通常跟集群机器数有关。两台MySQL放同一张表，只有ID(自增)和值两列，通过`REPLACE`方式更新记录，使之自增，并通过`LAST_INSERT_ID()`函数来获取自增ID。


#### Twitter
> Twitter 的实现策略
1. 第一位为0，不用
2. timestamp—41bits,精确到ms，那就意味着其可以表示长达(2^41-1)/(1000360024*365)=139.5年
3. machine id—10bits,该部分其实由datacenterId和workerId两部分组成
    1. datacenterId，方便搭建多个生成uid的service，并保证uid不重复
    2. workerId是实际server机器的代号，最大到32，同一个datacenter下的workerId是不能重复的
4. sequence id —12bits,该id可以表示4096个数字

参考：
- <http://hacloud.club/2017/09/09/snowflake/>
- <https://www.jianshu.com/p/54a87a7c3622>
- <https://gitee.com/keets/snowflake-id-generator>

#### 其它
> 一些小的尝试