package de.jetwick.snacktory.render;

/**
 * Created by aditya on 27/01/17.
 */
public interface IScraper {

    public void init();

    public String scrapePage(String url);

    public void shutdown();
}
