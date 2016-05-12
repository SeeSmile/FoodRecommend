package db;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import data.HaodouEntity;
import data.HaodouEntity.Step;

public class HaodouDB extends BaseDB{
	
	public static final String TABLE_FOOD = "food";
	public static final String KEY_ID = "id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_INTRO = "intro";
	public static final String KEY_TAGS = "tags";
	public static final String KEY_FOOD_MAIN = "food_main";
	public static final String KEY_FOOD_FU = "food_fu";
	public static final String KEY_STEPS = "steps";
	public static final String KEY_URL = "url";
	public static final String KEY_IMAGE = "image";

	public HaodouDB() {
		super("115.28.39.64", "food", "fupei", "password");
	}

	public void insertInfo(HaodouEntity entity) throws SQLException {
		ArrayList<DbParam> list = new ArrayList<DbParam>();
		list.add(new DbParam(KEY_IMAGE, entity.getImage()));
		list.add(new DbParam(KEY_TITLE, entity.getTitle()));
		list.add(new DbParam(KEY_INTRO, entity.getIntro()));
		list.add(new DbParam(KEY_TAGS, entity.getTags().toString()));
		list.add(new DbParam(KEY_FOOD_MAIN, entity.getFood_main().toString()));
		list.add(new DbParam(KEY_FOOD_FU, entity.getFood_fu().toString()));
		list.add(new DbParam(KEY_STEPS, entity.getSteps().toString()));
		list.add(new DbParam(KEY_URL, entity.getUrl()));
		String sql = createInsertSql(TABLE_FOOD, list);
		PreparedStatement pst = getPrepared(sql);
		initPst(pst , list);
		pst.execute();
	}
	
	public HaodouEntity getRecommendFood(String id) throws SQLException {
		 
		 return null;
	}
	
	/**
	 * 获取食谱详情
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws JSONException
	 */
	public HaodouEntity getDetailFood(String id) throws SQLException, JSONException {
		String sql = "select * from food where id=?";
		 PreparedStatement pst = getPrepared(sql);
		 pst.setString(1, id);
		 ResultSet result = pst.executeQuery();
		 if(result.next()) {
			 HaodouEntity entity = new HaodouEntity();
			 String title = result.getString(KEY_TITLE);
			 String url = result.getString(KEY_URL);
			 String str_id = id;
			 String intro = result.getString(KEY_INTRO);
			 String image = result.getString(KEY_IMAGE);
			 String str_tags = result.getString(KEY_TAGS);
			 String str_main = result.getString(KEY_FOOD_MAIN);
			 String str_fu = result.getString(KEY_FOOD_FU);
			 String str_step = result.getString(KEY_STEPS);
			 entity.setId(str_id);
			 entity.setTitle(title);
			 entity.setIntro(intro);
			 entity.setImage(image);
			 entity.setFood_fu(stringToList(str_fu));
			 entity.setFood_main(stringToList(str_main));
			 entity.setTags(stringToList(str_tags));
			 entity.setUrl(url);
			 entity.setSteps(stringToStep(str_step));
			 return entity;
		 }
		 return null;
	}
	
	/**
	 * 获取列表
	 * @param keyword
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public JSONArray getFoodByKey(String keyword, int page) throws SQLException {
		JSONArray array = new JSONArray();
		String sql = "select * from food where title like ? limit ?,?";
		PreparedStatement pst = getPrepared(sql);
		pst.setString(1, "%" + keyword + "%");
		pst.setInt(2, (page - 1) * 15);
		pst.setInt(3, 15);
		ResultSet result = pst.executeQuery();
		while(result.next()) {
			JSONObject json = new JSONObject();
			try {
				json.put(KEY_ID, result.getString(KEY_ID));
				json.put(KEY_IMAGE, result.getString(KEY_IMAGE));
				json.put(KEY_TAGS, result.getString(KEY_TAGS));
				json.put(KEY_TITLE, result.getString(KEY_TITLE));
				array.put(json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return array;
	}
	
	/**
	 * 将字符串转为List
	 * @param text
	 * @return
	 * @throws JSONException
	 */
	public static List<String> stringToList(String text) throws JSONException {
		JSONArray array = new JSONArray(text);
		List<String> list = new ArrayList<>();
		for(int i = 0; i < array.length(); i++) {
			list.add(array.getString(i));
		}
		return list;
	}
	
	/**
	 * 将字符串转为List<Step>
	 * @param text
	 * @return
	 * @throws JSONException
	 */
	public static List<Step> stringToStep(String text) throws JSONException {
		List<Step> list = new ArrayList<>();
		JSONArray array = new JSONArray(text);
		Gson gson = new Gson();
		for(int i = 0; i < array.length(); i++) {
			list.add(gson.fromJson(array.getJSONObject(i).toString(), Step.class));
		}
		return list;
	}
	
	/**
	 * 获取拥有此标签的食谱列表
	 * @param tag
	 * @return
	 * @throws SQLException
	 */
	public List<String> getIdsByTag(String tag) throws SQLException {
		String sql = "select id from food where tags like ?";
		PreparedStatement pst = getPrepared(sql);
		pst.setString(1, "%" + tag + "%");
		ResultSet result = pst.executeQuery();
		List<String> list = new ArrayList<>();
		while(result.next()) {
			list.add(result.getString(KEY_ID));
		}
		return list;
	}
	
	public List<String> getIdsByTags(List<String> tags) throws SQLException {
		List<String> list_id = new ArrayList<>();
		for(String str : tags) {
			List<String> list = getIdsByTag(str);
			list_id.add(list.toString());
		}
		return list_id;
	}
	
	public List<String> getTagsById(String id) throws SQLException, JSONException {
		String sql = "select " + KEY_TAGS + " from food where id=?";
		PreparedStatement pst = getPrepared(sql);
		pst.setString(1, id);
		ResultSet result = pst.executeQuery();
		if(result.next()) {
			return stringToList(result.getString(KEY_TAGS));
		}
		return null;
	}
}
