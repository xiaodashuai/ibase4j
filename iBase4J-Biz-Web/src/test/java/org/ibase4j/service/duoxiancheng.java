package org.ibase4j.service;/**
 * Created by acer
 * on 2019/3/5 9:29
 */

/**
 * @program: DOC
 *
 * @description: 多线程学习
 *
 * @author: xiaoshuiquan
 *
 * @create: 2019-03-05 09:29
 **/
public class duoxiancheng {
    public static void main(String[] args) {
        //多线程并行
       /* Mythread mythread=new Mythread("新线程运行！");
        mythread.start();
        for (int i=0;i<5;i++){
            System.out.println("main方法:"+i);
        }*/
       Ticket t=new Ticket();

       Thread t1=new Thread(t,"窗口1");
       Thread t2=new Thread(t,"窗口2");
       Thread t3=new Thread(t,"窗口3");

       t1.start();
       t2.start();
       t3.start();

    }
}



