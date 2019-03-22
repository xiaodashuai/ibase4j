package org.ibase4j.service;/**
 * Created by acer
 * on 2019/2/27 10:23
 */

import java.awt.*;
import java.util.Arrays;

/**
 * @program: DOC
 *
 * @description: jvav相关的一些算法
 * https://www.cnblogs.com/10158wsj/p/6782124.html?utm_source=tuicool&utm_medium=referral
 *
 * @author: xiaoshuiquan
 *
 * @create: 2019-02-27 10:23
 **/
public class SuanFa {
    public static void main(String[] args) {
        int []love={12,45,78,11,1,99,32,18};
        int[]ha={1,10,21,32,43,54};
        String[]aini={"q","d","z","v","y","j","m","l"};
        //maoPaoPaiXu(love);
        //xuanZePaiXu(love);
        /*int inde=erFenFaPaiXu(ha,32);
        System.out.println(inde);*/
       /* int inde= erFenFaStringPaiXu(aini,"y");
        System.out.println(inde);*/
        chaLuPaiXu(love);
    }

    //冒泡排序：每次前一个和后一个比较,比后面的大的话，就移位
    // 外层控制总共要进行的趟数，内层控制第几趟的数据内部的排序次数。
    public static  void  maoPaoPaiXu(int []args){
        for(int i=0;i<args.length;i++){
            for(int j=0;j<args.length-i-1;j++){
                if(args[j]>args[j+1]){
                    int tem =args[j];
                    args[j]=args[j+1];
                    args[j+1]=tem;
                }
            }
        }
        for (int res:args){
            System.out.println("冒泡排序后："+res+",");
        }
    }

    //选择排序算法：每次从现有数据挑出一个最小的直接放在最前面，第二次再将剩余的数据进行排序，拿出最小的放在最前面，以此类推
    public static  void  xuanZePaiXu(int []args) {
        for (int i=0;i<args.length-1;i++){
            for (int j=i+1;j<args.length;j++){
            if(args[i]>args[j]){
                int tem =args[i];
                args[i]=args[j];
                args[j]=tem;
            }
            }
        }
        for (int res:args){
            System.out.println("选择排序后"+res);
        }
    }

    //插入排序：不是通过交换位置而是通过比较找到合适的位置插入元素来达到排序的目的的
    //让每个位子的元素都和前一个元素做对比
    public static  void  chaLuPaiXu(int []args) {
        int len=args.length;
        int insertNum;//要插入的数
        for (int i=1;i<len;i++){//因为第一次不用，所以从1开始
            insertNum=args[i];
            int j=i-1;//序列元素个数
            while (j>=0&&args[j]>insertNum){//从后往前循环，将大于insertNum的数向后移动
                args[j+1]=args[j];
                j--;
            }
            args[j+1]=insertNum;//找到位置，插入当前元素
        }
        for (int res:args){
            System.out.print("选择排序后"+res+"  ");
        }
    }

    //希尔排序，是插入法的一种升级，
    public static  void  xiErPaiXu(int []args) {
        int len=args.length;
        while (len!=0){
            len=len/2;
            for (int i=0;i<len;i++){
                for (int j=i+len;j<args.length;j+=len){//元素从第二个开始
                    int k=j-len;//k为有序列最后一位
                    int temp=args[j];//要插入的元素
                    while (k>0&&temp<args[j]){//从后往前遍历
                        args[k+len]=k;
                         k-=len;
                    }
                    args[k+len]=temp;

                }
            }
        }
    }


    //二分查找有序数组:(主要是查找指定的一个数)当数据量很大适宜采用该方法。采用二分法查找时，数据需是有序不重复的
    public static  int  erFenFaPaiXu(int []args,int key) {
        int start=0;
        int end=args.length-1;
        int half=0;
        while (start<=end){
            half=(start+end)/2;
            if (key<args[half]){
                end=half-1;
            }
            if (key>args[half]){
                start=half+1;
            }
            if (key==args[half]) {
                break;
            }
        }
        return half;
    }

    //二分法查找字符串的元素位置
    public static  int  erFenFaStringPaiXu(String []args,String key) {
        Arrays.sort(args);//先排序，升序
        System.out.println("排序后的数组："+args);
        int index=Arrays.binarySearch(args, key);
        return index;
    }
}
