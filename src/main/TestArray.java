package main;

import java.util.ArrayList;
import java.util.List;

public class TestArray {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("dfs");
		list.add("fefscv");
		System.out.println(list.toString());
		List<TTT> list2 = new ArrayList<>();
		list2.add(new TTT("fupei", 12));
		list2.add(new TTT("ljoij", 90));
		System.out.println(list2.toString());

	}
	
	public static class TTT{
		private String name;
		private int age;
		public TTT(String name, int age) {
			this.name = name;
			this.age = age;
		}
		
		@Override
		public String toString() {
			return "name:" + name + ", age:" + age;
		}
		
		
	}

}
