package test;

import helper.AprioriHelper;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import data.HaodouEntity;
import db.HaodouDB;

public class DBTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
//			HaodouEntity entity = new HaodouDB().getDetailFood("8785");
			AprioriHelper.getRecommend("4600");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
