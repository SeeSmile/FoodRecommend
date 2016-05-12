package data;

import java.util.List;
import com.google.gson.Gson;

public class HaodouEntity {
	
	private String id;
	
	private String image;
	
	private String url;
	
	private String title;
	
	private String intro;
	
	private List<String> tags;
	
	private List<String> food_main;
	
	private List<String> food_fu;
	
	private List<Step> steps;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public List<String> getFood_main() {
		return food_main;
	}

	public void setFood_main(List<String> food_main) {
		this.food_main = food_main;
	}

	public List<String> getFood_fu() {
		return food_fu;
	}

	public void setFood_fu(List<String> food_fu) {
		this.food_fu = food_fu;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
	public static class Step {
		private int step;
		private String imgj;
		private String text;
		public int getStep() {
			return step;
		}
		public void setStep(int step) {
			this.step = step;
		}
		public String getImgj() {
			return imgj;
		}
		public void setImgj(String imgj) {
			this.imgj = imgj;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		
		@Override
		public String toString() {
			return new Gson().toJson(this);
		}
	}
}
