package com.davidemarino;

import java.net.URI;
import java.util.List;
import java.util.concurrent.Callable;

public class DonwloaderExtractorCallable implements Callable<List<Link>> {
    private URI basePath;
    private String url;

    public DonwloaderExtractorCallable(URI basePath, String url) {
        this.basePath = basePath;
        this.url = url;
    }

    public List<Link> call() {
        String body = Downloader.downloadContent(url);
        return LinksExtractor.extractLinks(basePath, url, body);
    }
}
