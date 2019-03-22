package org.ibase4j.service;/**
 * Created by acer
 * on 2019/3/8 14:24
 */

/**
 * @program: DOC
 *
 * @description: 打印一个int数组
 *
 * @author: xiaoshuiquan
 *
 * @create: 2019-03-08 14:24
 **/
public class testInt {

    public static void main(String[] args) {
        printAllSubset(3);
        /*int []b={1,2,3,4};
        printAllArray(b);*/
    }

    public static void printAllSubset(int N){
        if(N<=0){
        throw new IndexOutOfBoundsException("参数应该是一个正整数");
        }
        int allMasks=1<<N;
        for(int i=0;i<allMasks;i++){
            for (int j=0;j<N;j++){
                if((i&(1<<j))>0)
                    System.out.print((j+1)+"");

                    System.out.println();

            }
        }
    }

   /* 打印数组全部的子集*/
   public static void printAllArray(int [] a){
        if(null==a&&a.length==0){
            System.out.println("数组不能为空");
        }
        int leng=a.length;
        int allMasks=1<<leng;
        for(int i=0;i<allMasks;i++){
            for(int j=0;j<leng;j++){
                if((i&(1<<j))>0){
                    System.out.println(a[j]+"");
                }
            }
        }
   }

}
