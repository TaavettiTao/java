package com.test;


/**
 * 
 * @author taoweiwei
 * 1、内部类不能访问外部非final对象
 * 2、内部类可以直接访问外部类成员变量，即此时向内部类传入外部类实例
 * 3、该类在Test1基础上更改，对多线程访问的资源进行同步操作，
 *   并且互斥加在output方法中name变量上
 * 4、只有对多个线程访问的统一资源进行互斥synchronized，才能避免线程之间抢夺资源
 */
public class Test2 {
	public static void main(String[] args) {
		
	  new Test2().init();
	}
	
	public void init(){
	       final	OutPut output=new OutPut();
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						output.output("haizi");
					}
				}
			}).start();
			
		   new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						output.output("baba");
					}
				}
			}).start();
		
	}
	
	
	/**
	 * 
	 * @author taoweiwei
	 * 内部类，输出功能
	 * 1、synchronized：作用在变量上
	 * 2、作用在方法上
	 */
	class OutPut{
		/**
		 * 作用在变量上,此时同步的是name
		 * @param name
		 */
		public void output(String name){
			int len =name.length();
			//在name变量上加上同步操作，防止线程抢夺资源，当某一线程获得该资源后，
			//只有该线程使用完资源之后，其他线程才能使用。
			synchronized (name) {
				for(int i=0;i<len;i++){
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}
		
		/**
		 * 作用在方法上，此时同步的是当前对象this
		 * @param name
		 */
		public synchronized void output2(String name){
			int len =name.length();
				for(int i=0;i<len;i++){
					System.out.print(name.charAt(i));
				}
				System.out.println();
		}
		
		/**
		 * 作用在方法上，此时同步的是当前对象this
		 * @param name
		 */
		public void output3(String name){
			synchronized (this) {
			int len =name.length();
				for(int i=0;i<len;i++){
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}
	}
	

}
