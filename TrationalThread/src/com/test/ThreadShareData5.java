package com.test;

import java.util.Random;

/**
 * 
 * @author taoweiwei
 * 线程范围内共享变量：example one
 * 在该类中由于定义了静态变量data,所以多个线程共享静态变量data的数值,所以多个线程拿到的值是一样的
 */
public class ThreadShareData5 {
	
	//定义静态变量，多个线程共享data变量
	private static int data=0;
	
	public static void main(String[] args) {
		
		for(int i=0;i<2;i++){//启动两个线程
			new Thread(new Runnable() {
				@Override
				public void run() {
					data=new Random().nextInt();//放入数据
					System.out.println(Thread.currentThread().getName()+" has putted data:"+data);
					new A().get();
					new B().get();
				}
			}).start();
		}
		
	}
	
	static class A{
		
		public void get(){
			System.out.println("A from "+Thread.currentThread().getName()+" get data:"+data);
		}
		
		
	}
	
	static class B{
		public void get(){
			System.out.println("B from "+Thread.currentThread().getName()+" get data:"+data);
		}
	}
	

}
