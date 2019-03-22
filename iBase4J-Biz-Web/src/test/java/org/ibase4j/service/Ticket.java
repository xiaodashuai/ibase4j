package org.ibase4j.service;/**
 * Created by acer
 * on 2019/3/5 10:13
 */


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: DOC
 *
 * @description: 多线程卖票
 *
 * @author: xiaoshuiquan
 *
 * @create: 2019-03-05 10:13
 **/
public class Ticket implements Runnable {

    int ticket = 10;

    Lock l=new ReentrantLock();
    @Override
    public void run() {
        while (true) {
            l.lock();
                if (ticket > 0) {
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "正在卖票：" + ticket--);
                }
            l.unlock();
        }
    }
}