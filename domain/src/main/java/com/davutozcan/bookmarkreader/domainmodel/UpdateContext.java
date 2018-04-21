package com.davutozcan.bookmarkreader.domainmodel;

public class UpdateContext {
    private final IHttpClient httpClient;
    private final IWebUnitRepository realmFacade;
    private final IHtmlComparer comparer;
    private final ILogRepository logRepository;
    private final IFaviconExtractor faviconExtractor;
    private final IUpdateListener listener;

    public UpdateContext(IHttpClient httpClient, IWebUnitRepository realmFacade, IHtmlComparer comparer, ILogRepository logRepository, IFaviconExtractor faviconExtractor, IUpdateListener listener) {
        this.httpClient = httpClient;
        this.realmFacade = realmFacade;
        this.comparer = comparer;
        this.logRepository = logRepository;
        this.faviconExtractor = faviconExtractor;
        this.listener = listener;
    }

    public IHttpClient getHttpClient() {
        return httpClient;
    }

    public IWebUnitRepository getRealmFacade() {
        return realmFacade;
    }

    public IHtmlComparer getComparer() {
        return comparer;
    }

    public ILogRepository getLogRepository() {
        return logRepository;
    }

    public IFaviconExtractor getFaviconExtractor() {
        return faviconExtractor;
    }

    public IUpdateListener getListener() {
        return listener;
    }
}
