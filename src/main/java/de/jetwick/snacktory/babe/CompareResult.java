package de.jetwick.snacktory.babe;

import de.jetwick.snacktory.JResult;

public class CompareResult {
	public String id = "";
	public String source_id = "";

	public String babe_url;
	public String lib_url;

	public String babe_title;
	public String lib_title;

	public int title_distance;
	public int babe_title_len;
	public int lib_title_len;

	public int content_distance;
	public int babe_content_len;
	public int lib_content_len;



	public CompareResult() {
		babe_url = "";
		lib_url = "";

		babe_title = "";
		lib_title = "";

	}

	public CompareResult(String id, String source_id, String babe_url,
			String lib_url, String babe_title, String lib_title,
			int title_distance, int babe_title_len, int lib_title_len,
			int content_distance, int babe_content_len, int lib_content_len) {
		super();
		this.id = id;
		this.source_id = source_id;
		this.babe_url = babe_url;
		this.lib_url = lib_url;
		this.babe_title = babe_title;
		this.lib_title = lib_title;
		this.title_distance = title_distance;
		this.babe_title_len = babe_title_len;
		this.lib_title_len = lib_title_len;
		this.content_distance = content_distance;
		this.babe_content_len = babe_content_len;
		this.lib_content_len = lib_content_len;
	}

	public CompareResult(JResult origin) {
		id = origin.getId();

		babe_url = origin.getCanonicalUrl();
		lib_url = "";

		babe_title = origin.getTitle();
		lib_title = "";

		babe_title_len = origin.getTitle().length();
		babe_content_len = origin.getText().length();

	}
	
	
	public String toString() {
		String result = "";
		
		result += "\n" + id
				+ "\n" + babe_title
				+ "\n" + lib_title
				+ "\n" + babe_url;
				
		
		return result;
	}

}
