import java.util.*;

/*
Scott campbell

cse383 - f14
Homework 2- simple AWS Dynamo DB server

Single instance of AWS record
*/

public class URLItem {
	static String[] Names = new String[]{"Name","key","url"};
	String[] values;


	public URLItem() {
		values = new String[3];
	}

	public void set(String k, String v) {
		for (int i=0;i<Names.length;i++) {
			if (k.equals(Names[i])) {
				values[i] = v;
			}
		}
	}

	public String getName() {
		return values[0];
	}

	public String getKey() {
		return values[1];
	}

	public String getUrl() {
		return values[2];
	}


	public String toString() {
		return "Name=" + values[0] + " key="+values[1] + " url=" + values[2];
	}
}
