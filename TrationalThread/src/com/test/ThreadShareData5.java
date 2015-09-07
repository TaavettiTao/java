package com.test;

import java.util.Random;

/**
 * 
 * @author taoweiwei
 * 线程范围内共享变量：example one
 * 在该类中由于定义了静态变量data,所以多个线程共享静态变量data的数值,所以多个线程拿到的值是一样的
 * 线程并发执行是不可控的，无法知道那个线程先执行，那个线程后执行，都是通过CPU调度的，
 * 如果一段代码会被多个线程操作，则那么出现的情况是无法预料的；
 * 即使该程序代码是顺序的，但是可能线程1执行到A区域代码时，线程2已经执行到了A区域之后的B区域代码。
 * 所以下面程序出现以下情况是很正常的：
 * Thread-0 has putted data:-1649628587
   Thread-1 has putted data:-1146026723
   A from Thread-1 get data:-1146026723
   A from Thread-0 get data:-1146026723
   B from Thread-1 get data:-1146026723
   B from Thread-0 get data:-1146026723
   
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
