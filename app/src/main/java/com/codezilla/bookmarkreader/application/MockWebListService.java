package com.codezilla.bookmarkreader.application;

import com.codezilla.bookmarkreader.weblist.WebSiteInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by davut on 7/28/2017.
 */

class MockWebListService implements com.codezilla.bookmarkreader.weblist.IWebListService
{
    List<WebSiteInfo> result = new ArrayList<>();

    public MockWebListService() {
        for (int i = 0; i < 5; i++) {
            result.addAll(Arrays.asList(
                    WebSiteInfo.of("https://cleancoders.com","In this free episode, " +
                            "Clean Code: Episode 1, Robert \"Uncle Bob\" Martin explains, in his unique style, " +
                            "why businesses all too often see their development process slow to a halt, " +
                            "and what they can do to prevent it from happening. Watch it now for free!"),
                    WebSiteInfo.of("http://devnot.com" ,"Ülkemizde bilişim sektörü daha çok hizmet alanına yönelik çalışmakta" +
                            " ve yazılım üreten firmalardan oluşmaktadır." +
                            " Çoğumuz backend, frontend, mobil gibi alanlarda" +
                            " yazılım geliştiriyoruz, yani donanıma dokunmayan \"sanal\" işlerle uğraşıyoruz." +
                            " Türkiye’de donanım üretmek ucuz olmadığı için bu alana fazla" ),
                    WebSiteInfo.of("http://www.javaworld.com" ,"Serverless computing is no magic carpet," +
                            " but how does it really work? Get an overview of AWS Lambda's " +
                            "nanoservices architecture and execution model, then build your first" +
                            " Lambda function in Java" ),
                    WebSiteInfo.of("https://www.androidexperiments.com" , "KALEAX\n" +
                            "BY ALEXANDER SCHWANK\n" +
                            " 2\n" +
                            "A multi-level game where you throw paintballs at an invisible 3D object until you can guess what it is.")

            ));
        }
    }

    @Override
    public List<WebSiteInfo> getWebSitesInfos() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void add(String url) {
        result.add(WebSiteInfo.of(url , "No changes yet"));
    }


}
