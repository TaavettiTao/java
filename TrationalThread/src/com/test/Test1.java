package com.test;
/**
 * 
 * @author taoweiwei
 * 1、内部类不能访问外部非final对象
 * 2、内部类可以直接访问外部类成员变量，即此时向内部类传入外部类实例
 * 3、本类中的两个线程访问同一个output变量，此时没有做任何互斥措施，
 *   可能出现该对象输出的结果出现两个线程抢夺资源的情况，
 *   即可能会输出：babahaizi、hbaaiba等情况
 *
 */
public class Test1 {
	public static void main(String[] args) {
		
	  new Test1().init();
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
	 */
	class OutPut{
		public void output(String name){
			int len =name.length();
			for(int i=0;i<len;i++){
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
	}
	

}
