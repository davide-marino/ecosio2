package com.davidemarino;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 *
 */
public class Crawler {
    private ExecutorService executorService;
    private List<Link> links;
    private URI basePath;
    private Set<String> visitedUrls;
    private Set<String> toBeVisitedUrls;

    /**
     * Constructor
     *
     * @param startingUrl the starting url
     * @param threadSize the number of thread to use
     */
    public Crawler(String startingUrl, int threadSize) {
        basePath = URI.create(startingUrl).resolve("/");
        links = new ArrayList<>();
        executorService = Executors.newFixedThreadPool(threadSize);
        visitedUrls = new LinkedHashSet<>();
        toBeVisitedUrls = new LinkedHashSet<>();
        toBeVisitedUrls.add(startingUrl);
    }

    /**
     * The method to get the list of Link
     *
     * @return the list of requested links
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public List<Link> downloadLinks() throws InterruptedException, ExecutionException {
        while (!toBeVisitedUrls.isEmpty()) {
            System.out.println("Tobe visited: " + toBeVisitedUrls.size() + " visited: " + visitedUrls.size());
            visitedUrls.addAll(toBeVisitedUrls);
            List<Future<List<Link>>> newVisitedLinksList = executorService.invokeAll(toBeVisitedUrls.stream()
                    .map(url -> new DonwloaderExtractorCallable(basePath, url))
                    .toList());
            toBeVisitedUrls.clear();
            for (Future<List<Link>> future : newVisitedLinksList) {
                List<Link> newLinks = future.get();
                links.addAll(newLinks);
                for (Link link : newLinks) {
                    if (!visitedUrls.contains(link.url())) {
                        toBeVisitedUrls.add(link.url());
                    }
                }
            }
        }
        return links;
    }
}