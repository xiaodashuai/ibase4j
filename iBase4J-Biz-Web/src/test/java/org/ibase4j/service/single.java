package org.ibase4j.service;/**
 * Created by acer
 * on 2019/3/6 14:28
 */

/**
 * @program: DOC
 *
 * @description: 单例模式
 *
 * @author: xiaoshuiquan
 *
 * @create: 2019-03-06 14:28
 **/
public class single {
    /*//恶汉式单例（立即加载方式）
    //私有构造
    private single(){}
    private static single s=new single();
    //静态工厂方法
    public static single getInstance(){
        return s;
    }*/

    /*//恶汉式单例（延迟加载方式）线程不安全
    //私有构造
    private single(){};
    private static  single s=null;
    public static single getInstance(){
        if (s==null){
            s=new single();
        }
        return s;
    }*/

    //恶汉式单例（延迟加载方式）加锁，保证线程安全和效率
    //私有构造
    private single(){};
    private static single s=null;
    public static single getInstance(){
        if (s==null){
           synchronized (single.class){
               if (s==null){
                   s=new single();
               }
           }
        }
        return s;
    }
}
