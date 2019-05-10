package com.exercise;

import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Represents a Redis-backed web search index.
 */
public class JedisIndex {

    private Jedis jedis;

    /**
     * Constructor.
     *
     * @param jedis
     */
    public JedisIndex(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * Returns the Redis key for a given search term.
     *
     * @return Redis key.
     */
    private String urlSetKey(String term) {
        return "URLSet:" + term;
    }

    /**
     * Returns the Redis key for a URL's TermCounter.
     *
     * @return Redis key.
     */
    private String termCounterKey(String url) {
        return "TermCounter:" + url;
    }

    /**
     * Checks whether we have a TermCounter for a given URL.
     *
     * @param url
     * @return
     */
    public boolean isIndexed(String url) {
        String redisKey = termCounterKey(url);
        return jedis.exists(redisKey);
    }

    /**
     * Adds a URL to the set associated with `term`.
     *
     * @param term
     * @param tc
     */
    public void add(String term, TermCounter tc) {
        jedis.sadd(urlSetKey(term), tc.getLabel());
    }

    /**
     * Looks up a search term and returns a set of URLs.
     *
     * @param term
     * @return Set of URLs.
     */
    public Set<String> getURLs(String term) {
        return jedis.smembers(urlSetKey(term));
    }

    /**
     * Looks up a term and returns a map from URL to count.
     *
     * @param term
     * @return Map from URL to count.
     */
    public Map<String, Integer> getCounts(String term) {
        Map<String, Integer> total = new HashMap<>();
        Integer count = 0;
        Set<String> urls = getURLs(term);
        for (String url : urls) {
            count = getCount(url, term);
            total.put(url, count);
        }
        return total;
    }

    public Map<String, Integer> getCountsFaster(String term) {
        List<String> urls = new ArrayList<>();
        urls.addAll(getURLs(term));

        Transaction t = jedis.multi();
        for (String url : urls) {
            jedis.hget(termCounterKey(url), term);
        }

        List<Object> objects = t.exec();
        Map<String, Integer> map = new HashMap<>();
        int i = 0;
        for (String url : urls) {
            map.put(url, Integer.parseInt((String) objects.get(i++)));
        }
        return map;
    }

    /**
     * Returns the number of times the given term appears at the given URL.
     *
     * @param url
     * @param term
     * @return
     */
    public Integer getCount(String url, String term) {
        /*my implementation*/
//        Integer count = 0;
//        if(isIndexed(url)){
//            String termCount = jedis.get(termCounterKey(url));
//            Gson gson = new Gson();
//            TermCounter tc = gson.fromJson(termCount,TermCounter.class);
//            count = tc.get(term);
//        }
//        return count;
        /*my implementation*/
        return Integer.parseInt(jedis.hget(termCounterKey(url), term));
    }

    /**
     * Adds a page to the index.
     *
     * @param url        URL of the page.
     * @param paragraphs Collection of elements that should be indexed.
     */
    public void indexPage(String url, Elements paragraphs) {
        /*my implementation*/
//        TermCounter tc = new TermCounter(url);
//        tc.processElements(paragraphs);
//        Map<String,String> maps = new HashMap<>();
//        for(String term : tc.keySet()){
//            maps.put(term,String.valueOf(tc.get(term)));
//        }
//        Gson gson = new Gson();
//        jedis.sadd(termCounterKey(url),gson.toJson(maps));
        /*my implementation*/
        TermCounter tc = new TermCounter(url);
        tc.processElements(paragraphs);
        pushTermCountToRedis(tc);
    }

    private void pushTermCountToRedis(TermCounter tc) {
        Transaction t = jedis.multi();

        String url = tc.getLabel();
        String hashName = termCounterKey(url);

        // if this page has already been indexed; delete the old hash
        jedis.del(hashName);

        for (String term : tc.keySet()) {
            jedis.hset(hashName, term, tc.get(term).toString());
            jedis.sadd(urlSetKey(term),url);
        }

        t.exec();
    }

    /**
     * Prints the contents of the index.
     * <p>
     * Should be used for development and testing, not production.
     */
    public void printIndex() {
        // loop through the search terms
        for (String term : termSet()) {
            System.out.println(term);

            // for each term, print the pages where it appears
            Set<String> urls = getURLs(term);
            for (String url : urls) {
                Integer count = getCount(url, term);
                System.out.println("    " + url + " " + count);
            }
        }
    }

    /**
     * Returns the set of terms that have been indexed.
     * <p>
     * Should be used for development and testing, not production.
     *
     * @return
     */
    public Set<String> termSet() {
        Set<String> keys = urlSetKeys();
        Set<String> terms = new HashSet<String>();
        for (String key : keys) {
            String[] array = key.split(":");
            if (array.length < 2) {
                terms.add("");
            } else {
                terms.add(array[1]);
            }
        }
        return terms;
    }

    /**
     * Returns URLSet keys for the terms that have been indexed.
     * <p>
     * Should be used for development and testing, not production.
     *
     * @return
     */
    public Set<String> urlSetKeys() {
        return jedis.keys("URLSet:*");
    }

    /**
     * Returns TermCounter keys for the URLS that have been indexed.
     * <p>
     * Should be used for development and testing, not production.
     *
     * @return
     */
    public Set<String> termCounterKeys() {
        return jedis.keys("TermCounter:*");
    }

    /**
     * Deletes all URLSet objects from the database.
     * <p>
     * Should be used for development and testing, not production.
     *
     * @return
     */
    public void deleteURLSets() {
        Set<String> keys = urlSetKeys();
        Transaction t = jedis.multi();
        for (String key : keys) {
            t.del(key);
        }
        t.exec();
    }

    /**
     * Deletes all URLSet objects from the database.
     * <p>
     * Should be used for development and testing, not production.
     *
     * @return
     */
    public void deleteTermCounters() {
        Set<String> keys = termCounterKeys();
        Transaction t = jedis.multi();
        for (String key : keys) {
            t.del(key);
        }
        t.exec();
    }

    /**
     * Deletes all keys from the database.
     * <p>
     * Should be used for development and testing, not production.
     *
     * @return
     */
    public void deleteAllKeys() {
        Set<String> keys = jedis.keys("*");
        Transaction t = jedis.multi();
        for (String key : keys) {
            t.del(key);
        }
        t.exec();
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Jedis jedis = JedisMaker.make();
        JedisIndex index = new JedisIndex(jedis);

        //index.deleteTermCounters();
        //index.deleteURLSets();
        //index.deleteAllKeys();
        loadIndex(index);

        Map<String, Integer> map = index.getCounts("the");
        for (Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }

    /**
     * Stores two pages in the index for testing purposes.
     *
     * @return
     * @throws IOException
     */
    private static void loadIndex(JedisIndex index) throws IOException {
        WikiFetcher wf = new WikiFetcher();

        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        Elements paragraphs = wf.readWikipedia(url);
        index.indexPage(url, paragraphs);

        url = "https://en.wikipedia.org/wiki/Programming_language";
        paragraphs = wf.readWikipedia(url);
        index.indexPage(url, paragraphs);
    }
}
