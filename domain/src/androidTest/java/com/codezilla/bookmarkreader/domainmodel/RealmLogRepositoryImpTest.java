package com.codezilla.bookmarkreader.domainmodel;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static  org.hamcrest.MatcherAssert.assertThat;
/**
 * Created by davut on 9/7/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RealmLogRepositoryImpTest
{

    ILogRepository logRepository;

    @Before
    public void before()
    {
        logRepository = new RealmLogRepositoryImp(InstrumentationRegistry.getTargetContext());
        logRepository.clear();
    }

    @Test
    public void shouldReturnEmptyListWhenNoElementExists()
    {
        assertThat(logRepository.logs().size() , equalTo(0));
    }



    @Test
    public void shouldAddLog()
    {
        Date date = new Date(System.currentTimeMillis());
        logRepository.info("test");

        assertThat(logRepository.logs().get(0) , logEqualTo("test" , Log.INFO , date ));
    }

    @Test
    public void shouldAddOtherTypesOfLogs()
    {
        Date date = new Date(System.currentTimeMillis());
        logRepository.info("info");
        logRepository.warning("warning");
        logRepository.error("error");

        assertThat(logRepository.logs().get(2) , logEqualTo("info" , Log.INFO , date ));
        assertThat(logRepository.logs().get(1) , logEqualTo("warning" , Log.WARNING , date ));
        assertThat(logRepository.logs().get(0) , logEqualTo("error" , Log.ERROR , date ));
    }



    private Matcher<? super Log> logEqualTo(final String msg, final int level, final Date minDate) {
        return new BaseMatcher<Log>() {
            @Override
            public boolean matches(Object item) {
                Log log = (Log) item;
                return log.getMsg().equals(msg) && log.getType() == level && (log.getDate().after(minDate)|| log.getDate().compareTo(minDate) == 0  );
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Given log not matches");
            }
        };
    }

}