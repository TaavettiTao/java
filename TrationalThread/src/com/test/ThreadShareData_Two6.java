package com.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 
 * @author taoweiwei
 * 线程范围内共享变量：example two
 *程序输出结果：
            Thread-0 has putted data:137777750
			Thread-1 has putted data:708316100
			A from Thread-0 get data:137777750
			A from Thread-1 get data:708316100
			B from Thread-0 get data:137777750
			B from Thread-1 get data:708316100
     结果分析：
	        1、 线程 Thread-0、Thread-1分别放入了137777750、708316100两个数值，
	        2、 处于Thread-0范围上的A、B实体，都获取的是该线程上的产生的数值；
            3、处于Thread-1范围上的A.B实体，都获取的是该线程上产生的数值；
                                   以上的情况，便是我们所要阐述的：同一个线程范围内共享变量。
 * 
 */
public class ThreadShareData_Two6 {
	
	   //定义静态变量ThreadLocalMap，key为当前线程，value为当前线程所拥有的变量
	   private static Map<Thread,Integer> threadLocalMap=new HashMap<Thread,Integer>();
	
		public static void main(String[] args) {
			
			for(int i=0;i<2;i++){//启动两个线程
				new Thread(new Runnable() {
					@Override
					public void run() {
						int data=new Random().nextInt();//放入数据
						
						System.out.println(Thread.currentThread().getName()+" has putted data:"+data);
						
						//保存当前线程所拥有的信息
						threadLocalMap.put(Thread.currentThread(), data);
						
						new A().get();
						new B().get();
					}
				}).start();
			}
			
		}
		
		static class A{
			
			public void get(){
				System.out.println("A from "+Thread.currentThread().getName()+" get data:"+threadLocalMap.get(Thread.currentThread()));
			}
			
			
		}
		
		static class B{
			public void get(){
				System.out.println("B from "+Thread.currentThread().getName()+" get data:"+threadLocalMap.get(Thread.currentThread()));
			}
		}
}
