package spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sun.print.resources.serviceui;

import data.HaodouEntity;
import data.HaodouEntity.Step;


public class HaodouSpider {
	
	/**
	 * 名字
	 */
	private static final String ID_TITLE = "stitle";
	/**
	 * 标签
	 */
	private static final String CLASS_TAGS = "pop_tags";
	/**
	 * 主料
	 */
	private static final String CLASS_MAIN = "ingtmgr";
	/**
	 * 辅料
	 */
	private static final String CLASS_FU = "ingtbur";
	
	private static final String CLASS_STEP = "step";
	
	private static final String ID_INTRO = "sintro";
	
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
				HaodouEntity entity = new HaodouEntity();
				entity.setTags(getTags(doc));
				entity.setTitle(getTitle(doc));
				entity.setFood_fu(getFoodFu(doc));
				entity.setFood_main(getFoodMain(doc));
				entity.setSteps(getSteps(doc));
				entity.setIntro(getIntro(doc));
				System.out.println(entity.toString());
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
	
	private static String getTitle(Document doc) {
		return doc.getElementById(ID_TITLE).text();
	}
	
	private static List<String> getTags(Document doc) {
		List<String> tags = new ArrayList<String>();
		Elements eles = doc.getElementsByClass(CLASS_TAGS);
		Elements eles_tags = eles.get(0).select("a");
		for(Element e : eles_tags) {
			tags.add(e.text());
		}
		return tags;
	}
	
	private static List<String> getFoodFu(Document doc) {
		List<String> tags = new ArrayList<String>();
		Elements eles_fu = doc.getElementsByClass(CLASS_FU);
		for(Element e : eles_fu) {
			tags.add(e.select("p").get(0).text());
		}
		return tags;
	}
	
	private static List<String> getFoodMain(Document doc) {
		List<String> tags = new ArrayList<String>();
		Elements eles_main = doc.getElementsByClass(CLASS_MAIN);
		for(Element e : eles_main) {
			tags.add(e.select("a").get(0).text());
		}
		return tags;
	}
	
	private static List<Step> getSteps(Document doc) {
		List<Step> steps = new ArrayList<HaodouEntity.Step>();
		Elements ele_steps = doc.getElementsByClass(CLASS_STEP).get(0).select("dd");
		for(int i = 0; i < ele_steps.size(); i++) {
			Step step = new Step();
			String img = ele_steps.get(i).select("img").get(0).attr("src");
			String text = ele_steps.get(i).select("p").text();
			step.setImgj(img);
			step.setText(text);
			step.setStep(i + 1);
			steps.add(step);
		}
		return steps;
	}
	
	private static String getIntro(Document doc) {
		return doc.getElementById(ID_INTRO).attr("data");
	}
	
	public interface LoadListener {
		public void onProgress(int index);
		public void onWebError();
		public void onError();
	}
	
}
