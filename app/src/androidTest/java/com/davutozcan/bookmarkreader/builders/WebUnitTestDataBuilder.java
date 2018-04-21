package com.davutozcan.bookmarkreader.builders;

import com.davutozcan.bookmarkreader.domainmodel.Change;
import com.davutozcan.bookmarkreader.domainmodel.WebUnit;
import com.davutozcan.bookmarkreader.domainmodel.WebUnitContent;

import java.util.Date;

import static com.davutozcan.bookmarkreader.builders.WebUnitTestDataBuilder.WebUnitContentBuilder.contentBuilder;

/**
 * Created by davut on 17.02.2018.
 */

public class WebUnitTestDataBuilder
{
    private final WebUnit webUnit;

    private WebUnitTestDataBuilder()
    {
        this.webUnit = new WebUnit();
    }

    public static WebUnitTestDataBuilder webUnitBuilder()
    {
        return new WebUnitTestDataBuilder();
    }



    public WebUnitTestDataBuilder withUrl(String url)
    {
        this.webUnit.setUrl(url);
        return this;
    }

    public WebUnitTestDataBuilder withContent(WebUnitContent content)
    {
        this.webUnit.setLatestContent(content);
        return this;
    }

    public static WebUnitContent content(String url , String content)
    {
        return contentBuilder()
                .withUrl(url)
                .withContent(content)
                .withDate(System.currentTimeMillis())
                .build();
    }

    public WebUnitTestDataBuilder withStatus(int status) {
        this.webUnit.setStatus(status);
        return this;
    }

    public WebUnit build() {
        return this.webUnit;
    }

    public WebUnitTestDataBuilder withChange(Change change)
    {
        this.webUnit.setChange(change);
        return this;
    }

    public static class WebUnitContentBuilder
    {

        private final WebUnitContent webUnitContent;

        private WebUnitContentBuilder()
        {
            this.webUnitContent = new WebUnitContent();
        }
        public static WebUnitContentBuilder contentBuilder()
        {
            return new WebUnitContentBuilder();
        }
        public WebUnitContentBuilder withContent(String content)
        {
            webUnitContent.setContent(content);
            return this;
        }



        private WebUnitContentBuilder withDate(long time) {
            this.webUnitContent.setDate(new Date(time));
            return this;
        }

        private WebUnitContentBuilder withUrl(String url) {
            this.webUnitContent.setUrl(url);
            return this;
        }

        private WebUnitContent build() {
            return this.webUnitContent;
        }
    }
}
