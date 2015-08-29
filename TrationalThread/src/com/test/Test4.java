package com.test;
/**
 * 
 * @author taoweiwei
 * 问题：子线程循环10次，接着主线程循环100，接着又回到子线程10次，
 *     接在再回到主线程又循环100，如此循环50次，请写出程序。
 * 经验：1、要用到共同数据（包括同步锁）的若干个方法应该归属在同一个类身上，
 *       这种设计正好体现了高类聚和程序的健壮性。
 *     2、主线程优先级最高，即main方法先执行（其也是一个线程），线程使用start方法只是标志线程启动，
 *       之后便被放进队列中，并不一定在start之后立马执行。
 */ 
public class Test4 {
	public static void main(String[] args) {
		final Common com=new Common();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 50; i++) {
					com.sub();
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 50; i++) {
					com.main();
				}
			}
		}).start();
	}
}

/**
 * 
 * @author taoweiwei
 * 分装同步方法的类:此时synchronized同步的对象是this，即当前对象
 * 1、每当子线程方法调用完之后，让子线程等待，主线程开始执行；
 *   主线程执行完一次，主线程等待，唤醒子线程执行，如此循环50次。
 * 2、在线程执行过程中，可能需要子线程先执行，则将线程间通信变量isSubRun=true，即表示子线程先运行；
 *   isSubRun=false,即表示主线程执行。
 */
class Common{
	private boolean isSubRun=true;
	
	/**
	 * 子线程方法
	 */
	  public synchronized void sub(){
		  if(!isSubRun){
			  try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		  }
		  
		  for(int i = 0; i < 10; i++){
			  System.out.println("sub thread ->"+i);
		  }
		  
		 isSubRun=false;
		 this.notify();
	  }
	  
	  /**
	   * 主线程方法
	   */
	  public synchronized void main(){
		  if(isSubRun){
			  try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		  }
		  
		  for(int i = 0; i < 100; i++){
			  System.out.println("main thread ->"+i);
		  }
		  
		  isSubRun=true;
		  this.notify();
	  }
	  
}

