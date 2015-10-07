package com.test;

import java.util.Random;

/**
 * 
 * @author taoweiwei
 * ThreadLocal：实现线程范围内单个变量共享，一个ThreadLocal对象代表一个变量，故其中只能放一个数据。
 * ThreadLocal并不是一个Thread，而是Thread的局部变量,其本质也是通过内部定义的Map实现线程范围内的共享变量的。
 */
public class ThreadLocal_07 {
	
	    //定义静态变量ThreadLocalMap，key为当前线程，value为当前线程所拥有的变量
	    private  static  ThreadLocal<Integer> threadLocal=new ThreadLocal<Integer>();
	
		public static void main(String[] args) {
			
			for(int i=0;i<2;i++){//启动两个线程
				new Thread(new Runnable() {
					@Override
					public void run() {
						int data=new Random().nextInt();//放入数据
						
						System.out.println(Thread.currentThread().getName()+" has putted data:"+data);
						
						//保存当前线程所拥有的信息
						threadLocal.set(data);
						
						new A().get();
						new B().get();
					}
				}).start();
			}
			
		}
		
		static class A{
			
			public void get(){
				System.out.println("A from "+Thread.currentThread().getName()+" get data:"+threadLocal.get());
			}
		}
		
		static class B{
			public void get(){
				System.out.println("B from "+Thread.currentThread().getName()+" get data:"+threadLocal.get());
			}
		}
}
