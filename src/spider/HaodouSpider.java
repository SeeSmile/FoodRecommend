package spider;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class HaodouSpider {
	
	public static final String URL_MAIN = "http://www.haodou.com/recipe/";
	
	public static boolean canRun = false;
	
	public static void start(int startid, LoadListener listener) {
		
		int index = startid;
		canRun = true;
		while(canRun) {
			index++;
			Document doc;
			try {
				doc = Jsoup.connect(URL_MAIN + index + "/")
						.get();
				listener.onProgress(index);
				
				
			} catch (IOException e) {
				listener.onWebError();
			} finally {
				try {
					Thread.sleep(2 * 1000);
				} catch (InterruptedException e) {
					listener.onError();
				}
			}
			
		}
		
	}
	
	public interface LoadListener {
		public void onProgress(int index);
		public void onWebError();
		public void onError();
	}
	
}
