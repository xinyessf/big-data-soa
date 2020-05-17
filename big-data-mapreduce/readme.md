### 测试数

```
data目录
```

### 统计上行下行流量

>需求：统计一下文件中，每一个用户所耗费的总上行流量，总下行流量，总流量

```
1363157982040 	13502468823	5C-0A-5B-6A-0B-D4:CMCC-EASY	120.196.100.99	y0.ifengimg.com	综合门户	57	102	7335	110349	200
1363157986072 	18320173382	84-25-DB-4F-10-1A:CMCC-EASY	120.196.100.99	input.shouji.sogou.com	搜索引擎	21	18	9531	2412	200
1363157990043 	13925057413	00-1F-64-E1-E6-9A:CMCC	120.196.100.55	t3.baidu.com	搜索引擎	69	63	11058	48243	200

```

>map阶段：将每一行按tab切分成各字段，提取其中的手机号作为输出key，流量信息封装到FlowBean对象中，作为输出的value

```
要点：自定义类型如何实现hadoop的序列化接口
FlowBean这种自定义数据类型必须实现hadoop的序列化接口：Writable，实现其中的两个方法： 
readFields(in)   反序列化方法
write(out)   序列化方法

```

>reduce阶段：遍历一组数据的所有value（flowbean），进行累加，然后以手机号作为key输出，以总流量信息bean作为value输出

### 求网站topN

>1、读取附件中的文件request.dat，
>
>**需求1****：**求出每一个url被访问的总次数，并将结果输出到一个结果文件中
>
>思路：就是一个wordcount
>
>**需求2****：**求出每个网站被访问次数最多的top3个url《分组TOPN》
>
>思路： 
>
>map阶段——切字段，抽取域名作为key，url作为value，返回即可
>
>reduce阶段——用迭代器，将一个域名的一组url迭代出来，挨个放入一个hashmap中进行计数，最后从这个hashmap中挑出次数最多的3个url作为结果返回
>
>**需求3****：**求访问次数最多的topn个网站（只能有1个reduce worker）《全局TOPN》
>
>思路：
>
>map阶段：解析数据，将域名作为key，1作为value
>
>reduce阶段：
>
>reduce方法中——对一个域名的一组1累加，然后将 <域名,总次数>放入一个成员变量Treemap中
>
>cleanup方法中——从treemap中挑出次数最高的n个域名作为结果输出

```java
要点1：每一个reduce worker程序，会在处理完自己的所有数据后，调用一次cleanup方法

要点2：如何向map和reduce传自定义参数
从JobSubmitter的main方法中，可以向map worker和reduce worker传递自定义参数（通过configuration对象来写入自定义参数）；然后，我们的map方法和reduce方法中，可以通过context.getConfiguration()来取自定义参数 

Configuration conf = new Configuration() //
这一句代码，会加载mr工程jar包中的hadoop依赖jar中的各默认配置文件*-default.xml
然后，会加载mr工程中自己的放置的*-site.xml
然后，还可以在代码中conf.set("参数名","参数值")



另外，mr工程打成jar包后，在hadoop集群的机器上，用hadoop jar mr.jar xx.yy.MainClass
运行时，hadoop jar命令会将这台机器上的hadoop安装目录中的所有jar包和配置文件通通加入运行时的classpath，

配置参数的优先级：
1、依赖jar中的默认配置
2、环境中的*-site.xml
3、工程中的*-site.xml
4、代码中set的参数
优先级一次增大，高优先级的参数值会覆盖低优先级的参数值

```

