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
	
	public CompareResult(JResult origin) {
		id = origin.getId();
		
		babe_url = origin.getCanonicalUrl();
		lib_url = "";
		
		babe_title = origin.getTitle();
		lib_title = "";
		
		babe_title_len = origin.getTitle().length();
		babe_content_len = origin.getText().length();
				
	}
	
	

}
