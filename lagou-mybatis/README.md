# lagou-mybatis
mybatis框架的深入学习与自定义的部分功能的实现
1.Mybatis动态sql是做什么的？都有哪些动态sql？简述一下动态sql的执行原理？

答：动态 SQL 是 MyBatis 的强大特性之一。如果你使用过 JDBC 或其它类似的框架，你应该能理解根据不同条件拼接 SQL 语句有多痛苦，例如拼接时要确保不能忘记添加必要的空格，还要注意去掉列表最后一个列名的逗号。利用动态 SQL，可以彻底摆脱这种痛苦。

- if

- choose (when, otherwise)

- trim (where, set)

- foreach

  Mybatis框架将xml里的sql语句构成一个一个节点，每个节点由一种SqlNode对象描述。每一个动态sql标签都对应一个SqlNode类型的实现类:每一个动态sql由多个sqlNode节点构成，其根节点是MixedSqlNode，这个节点先执行apply方法，在这个apply里面，依次执行子节点的apply方法，那么在每个子节点里面，也会依次执行apply方法，这样递归的执行下去，而上下文，就是用来保存参数，构建动态sql的prepareStatement的参数，和保存最终生成sql的StringBuilder对象的。

  每当调用mapper的方法时，都会执行根节点的apply方法，拼接成包含内部包含?的sql语句，然后调用preparedStatement，然后setPrepare方法设置参数，来执行动态sql。

  

2.Mybatis是否支持懒加载？如果支持，它的实现原理是什么？

答：支持。延迟加载主要是通过动态代理的形式实现。通过代理拦截到指定方法，执行数据加载。

JavaassistProxyFactory会使用CGLib创建一个User代理对象，所有调用User对象方法，都会经过EnhancedResultObjectProxyImpl.invoke()方法的拦截。于是当调用User.getOrder()方法时，才真正去执行查询Order的动作并把结果赋值给User的order属性。如果lazyLoadingEnabled=false，压根不会创建User代理对象，直接就是user对象，并立即执行order查询，然后赋值给User的order属性。



3.Mybatis都有哪些Executor执行器？他们之间的区别是什么？

答：SimpleExecutor：每执行一次update或select，就开启一个Statement对象，用完立刻关闭Statement对象。

ReuseExecutor：执行update或select，以sql作为key查找Statement对象，存在就使用，不存在就创建，用完后，不关闭Statement对象，而是放置于Map内，供下一次使用。简言之，就是重复使用Statement对象。

BatchExecutor：执行update（没有select，JDBC批处理不支持select），将所有sql都添加到批处理中（addBatch()），等待统一执行（executeBatch()），它缓存了多个Statement对象，每个Statement对象都是addBatch()完毕后，等待逐一执行executeBatch()批处理。与JDBC批处理相同。

作用范围：Executor的这些特点，都严格限制在SqlSession生命周期范围内。



4.简述一下Mybatis的一级、二级缓存（分别从存储结构、范围、失效场景三个方面作答）？

答：MyBatis的一级缓存是基于SqlSession级别的，也就是说某个SqlSession进行某个查询操作后会将该结果暂时缓存起来，而后在所有的SqlSession没有对该表进行插入、修改、删除操作的情况下，当这个SqlSession再次发起此查询时SqlSession不会去数据库执行查询操作，而是直接从缓存拿出上次查询的结果。不同的SqlSession之间缓存的数据互不影响；MyBatis的二级缓存是基于Mapper级别的，也就是说多个SqlSession去使用某个Mapper的查询语句时，得到的缓存数据是可共用的.

5.简述Mybatis的插件运行原理，以及如何编写一个Mybatis插件？

答：

1. 根据解析到的类信息创建Interceptor对象；
2. 调用setProperties方法设置属性变量；
3. 添加到Configuration的interceptorChain拦截器链中；
