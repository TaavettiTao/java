package com.test;

import java.util.Random;

/**
 * 
 * @author taoweiwei
 * ThreadLocal：实现线程范围内对象变量共享
 */
public class ThreadLocal_08 {
	  
		public static void main(String[] args) {
			
			for(int i=0;i<2;i++){//启动两个线程
				new Thread(new Runnable() {
					@Override
					public void run() {
						int data=new Random().nextInt();
						ThreadObject.getInstance().setAge(data);
						ThreadObject.getInstance().setName("张三");
						new A().get();
						new B().get();
					}
				}).start();
			}
			
		}
		
		static class A{
			
			public void get(){
				//取得当前线程的对象实例
				ThreadObject threadObject=ThreadObject.getInstance();
				System.out.println("A from "+Thread.currentThread().getName()+" get data:"
				                  +"name-->"+threadObject.getName()+",age-->"+threadObject.getAge());
			}
			
			
		}
		
		static class B{
			public void get(){
				//取得当前线程的对象实例
				ThreadObject threadObject=ThreadObject.getInstance();
				System.out.println("B from "+Thread.currentThread().getName()+" get data:"
				                  +"name-->"+threadObject.getName()+",age-->"+threadObject.getAge());
			}
		}
			
}

/**
 * 
 * @author taoweiwei
 * 单例模式:饥汉模式
 */
class ThreadObject{
	
	private String name;
	
	private Integer age;
	
	//将ThreadLocal封装在对象内部,每次获取该线程下对应的对象变量
	private static ThreadLocal<ThreadObject> map=new ThreadLocal<ThreadObject>();
	
	private ThreadObject(){}//单例模式
	
	/**
	 * 多线程环境下，使用synchronized保证线程同步
	 * @return
	 */
	public static synchronized ThreadObject getInstance(){
		ThreadObject threadObject=map.get();
		if(threadObject==null){
			threadObject=new ThreadObject();
			map.set(threadObject);
		}
		return threadObject;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
