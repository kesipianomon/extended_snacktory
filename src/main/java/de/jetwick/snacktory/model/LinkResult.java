package de.jetwick.snacktory.model;

import org.jsoup.nodes.Element;

public class LinkResult {

    public String src;
    public Integer weight;
    public String title;
    public int height;
    public int width;
    public String alt;
    public boolean noFollow;
    public Element element;

    public LinkResult(String src, Integer weight, String title, int height, int width, String alt, boolean noFollow) {
        this.src = src;
        this.weight = weight;
        this.title = title;
        this.height = height;
        this.width = width;
        this.alt = alt;
        this.noFollow = noFollow;
    }
    
    public String toString() {
    	return src;
    }
}
