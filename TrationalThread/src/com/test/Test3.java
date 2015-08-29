package com.test;


/**
 * 
 * @author taoweiwei
 * 1、内部类不能访问外部非final对象
 * 2、内部类可以直接访问外部类成员变量，即此时向内部类传入外部类实例
 *
 */
public class Test3 {
	public static void main(String[] args) {
		
	  new Test3().init();
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
	 * OutPut.class:类的字节码在内存中也是一份变量，
	 * 所以以下两个方法互斥，共享同一对象
	 */
	static class OutPut{
		
		public void output(String name){
			synchronized (OutPut.class) {
			int len =name.length();
				for(int i=0;i<len;i++){
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}
		
		/**
		 * 静态方法同步
		 * @param name
		 */
		public static  synchronized void output3(String name){
			int len =name.length();
			for(int i=0;i<len;i++){
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
	}
	

}
