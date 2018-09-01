package com.davutozcan.bookmarkreader.domainmodel;

import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Supplier;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;

public class SingleItemUpdater
{
    IWebUnitRepository realmFacade;
    Supplier<IHttpClient> httpClientFactory;
    IHtmlComparer comparer;
    IFaviconExtractor faviconExtractor;
    ILogRepository logRepository;

    public SingleItemUpdater(IWebUnitRepository realmFacade, Supplier<IHttpClient> httpClientFactory, IHtmlComparer comparer, IFaviconExtractor faviconExtractor, ILogRepository logRepository) {
        this.realmFacade = realmFacade;
        this.httpClientFactory = httpClientFactory;
        this.comparer = comparer;
        this.faviconExtractor = faviconExtractor;
        this.logRepository = logRepository;
    }

    public void update(WebUnit w , Consumer<WebUnit> onStart , Consumer<WebUnit> onComplete , Consumer<WebUnit> onFail , Consumer<WebUnit> onNewContent)
    {
        try
        {
            w.setLastDownloadCheckDate(new Date());
            onStart.accept(w);
            updateSafe(w , onNewContent);
            w.setDownloadStatus(WebUnit.DownloadStatus.OK);
            onComplete.accept(w);
        } catch (Exception e)
        {
            w.setDownloadStatus(WebUnit.DownloadStatus.ERROR);
            onFail.accept(w);
            logRepository.error(String.format("Cannot update[%s] cause:%s" , w.getUrl() , e.getMessage()));
        }
        updateEntity(w);
    }

    private void updateSafe(WebUnit w, Consumer<WebUnit> onNewContent) {
        IHttpClient httpClient = this.httpClientFactory.get();
        String htmlContent = httpClient.getHtmlContent(w.getUrl());
        boolean changed = comparer.isChanged(currentContent(w) , htmlContent);
        if(changed)
        {
            swapLatestContent(w);
            WebUnitContent wuc = new WebUnitContent();
            wuc.setContent(htmlContent);
            w.setLatestContent(wuc);
            wuc.setUrl(httpClient.url());
            w.setChange( toChange(comparer.newLines()));
            w.setStatus(WebUnit.Status.HAS_NEW_CONTENT);
            logRepository.info(String.format("Updated:[%s] " , w.getUrl()));
            onNewContent.accept(w);
        }
        String favicon = faviconExtractor.faviconUrl(httpClient.url(), htmlContent);
        if(!isNullOrEmpty(favicon))
        {
            w.setFaviconUrl(favicon);
        }
    }

    private void swapLatestContent(WebUnit w) {
        WebUnitContent wuc = w.getLatestContent();
        w.setPreviousContent(wuc);
        w.setLatestContent(null);
    }


    private void updateEntity(WebUnit w) {
        try{
            realmFacade.update(w);
        }catch (Exception e){
            android.util.Log.e(getClass().getSimpleName(), "updateEntity: ",e );
        }
    }

    private Change toChange(List<TextBlock> textBlocks) {
        Change change = new Change();
        RealmList<TextBlock> blocks = new RealmList<>();
        blocks.addAll(textBlocks );
        change.setNewBlocks(blocks);
        return change;
    }

    private String currentContent(WebUnit w) {
        if(w.getLatestContent() == null)
            return "";
        if(isNullOrEmpty(w.getLatestContent().getContent()))
            return "";
        return w.getLatestContent().getContent();
    }
    private boolean isNullOrEmpty(String content) {
        if(content == null)
            return true;
        if(content.length() == 0)
            return true;
        return false;
    }
}
