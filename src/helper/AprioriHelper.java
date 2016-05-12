package helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import main.AprioriUtil;

import org.json.JSONException;

import db.HaodouDB;

public class AprioriHelper {
	
	public static List<Set<String>> getRecommend(String id) throws SQLException, JSONException {
		HaodouDB db = new HaodouDB();
		List<String> list_tag = db.getTagsById(id);
		System.out.println("tags:\n" + list_tag);
		List<String> list_ids = db.getIdsByTags(list_tag);
		List<Set<String>> list = new AprioriUtil().start(list_ids);
		return list;
	}
	
}
