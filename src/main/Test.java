package main;

import spider.HaodouSpider;
import spider.HaodouSpider.LoadListener;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			
			public void run() {
				HaodouSpider.start(1019074, new LoadListener() {
					
					public void onWebError() {
						System.out.println("onWebError");
					}
					
					public void onProgress(int index) {
						System.out.println(index);
					}
					
					public void onError() {
						System.out.println("onError");
					}
				});
				
			}
		}).start();
	}

}
