package org.ibase4j.service;/**
 * Created by acer
 * on 2019/3/5 9:33
 */

import net.bytebuddy.implementation.bind.annotation.Super;

/**
 * @program: DOC
 *
 * @description: 自定义线程
 *
 * @author: xiaoshuiquan
 *
 * @create: 2019-03-05 09:33
 **/
public class Mythread extends Thread {
    //调用父类的构造方法，指定线程名称
    public Mythread(String name){
        super(name);
    }

    @Override
    public void run() {
       for (int i=0;i<5;i++){
           System.out.println(getName()+i);
       }
    }
}
