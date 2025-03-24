package com.davidemarino;

import com.davidemarino.Crawler;
import com.davidemarino.Link;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Main class
 */
public class Main {

    /**
     * The main method. It takes 2 parameters
     * @param args must be of size 2. THe first parameter is the starting page, the second is the number of threads
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            showUsage();
        }
        try {
            Crawler crawler = new Crawler(args[0], Integer.parseInt(args[1]));
            List<Link> links = crawler.downloadLinks();

            System.out.println(
                    links.stream()
                            .sorted(Comparator.comparing(Link::label))
                            .map(link -> "Page:  " + link.page() + "\nLabel: " + link.label() + "\nLink:  " + link.url() + "\n")
                            .collect(Collectors.joining("=========================================\n")));

            System.out.println("Total number of links: " + links.size());
        } catch (NumberFormatException e) {
            showUsage();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void showUsage() {
        System.out.println("Usage: java -jar crawler.jar <url> <number of threads>");
        System.exit(1);
    }
}