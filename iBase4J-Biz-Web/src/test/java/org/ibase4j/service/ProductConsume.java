package org.ibase4j.service;/**
 * Created by acer
 * on 2019/3/6 9:29
 */

/**
 * @program: DOC
 *
 * @description: 多线程中的生产者和消费者
 *
 * @author: xiaoshuiquan
 *
 * @create: 2019-03-06 09:29
 **/
public class ProductConsume {

    /*public synchronized void product(){
        if (this.product>=100){
            try {
                wait();
                System.out.println("生产者满了，消费者快消费！");
            }catch (InterruptedException e){
                e.printStackTrace();
            }
           return;
        }
        this.product++;
        System.out.println("生产者生产了："+this.product+"个产品");
        notifyAll();
    }

    public synchronized void consume(){
        if(this.product<=10){
            try {
                wait();
                System.out.println("消费品消耗完了");
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return;
        }
        this.product--;
        System.out.println("消费第"+this.product+"商品");
        notifyAll();
    }*/
}
