package com.exercise;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WikiPhilosophy {

    final static List<String> visited = new ArrayList<String>();
    final static WikiFetcher wf = new WikiFetcher();

    /**
     * Tests a conjecture about Wikipedia and Philosophy.
     *
     * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
     *
     * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        String destination = "https://en.wikipedia.org/wiki/Philosophy";
        String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";

        testConjecture(destination, source, 10);
    }

    /**
     * Starts from given URL and follows first link until it finds the destination or exceeds the limit.
     *
     * @param destination
     * @param source
     * @throws IOException
     */
    public static void testConjecture(String destination, String source, int limit) throws IOException {
        String url = source;
        for(int i = 0;i < limit; i++) {
            if(visited.contains(url)){
                System.err.println("We're in a loop, exiting.");
            }else{
                visited.add(url);
            }

            Element elt = getFirstValidLink(url);

            if(elt == null){
                System.err.println("Got to a page with no valid links.");
                return;
            }

            System.out.println("**" + elt.text() + "**");
            url = elt.attr("abs:href");

            if(url.equals(destination)){
                System.out.println("Eureka!");
                break;
            }
        }
    }

    private static Element getFirstValidLink(String url) throws IOException{
        Elements elements = wf.fetchWikipedia(url);
        WikiParser parser = new WikiParser(elements);
        return parser.findFirstLink();
    }
}
