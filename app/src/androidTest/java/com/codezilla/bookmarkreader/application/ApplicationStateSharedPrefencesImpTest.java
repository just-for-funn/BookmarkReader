package com.codezilla.bookmarkreader.application;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by davut on 8/27/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationStateSharedPrefencesImpTest {

    ApplicationStateSharedPrefencesImp aspc;
    @Before
    public void before()
    {
        aspc = new ApplicationStateSharedPrefencesImp(InstrumentationRegistry.getTargetContext());
        aspc.deleteAll();

    }

    @After
    public void after()
    {
        aspc.deleteAll();
    }


    @Test
    public void shoudlReturnFalseOnAppInitOnFirstOpen()
    {
        assertThat(aspc.isAppInitlized() , is(false));
    }


    @Test
    public void shouldReturnSettedValueForInit()
    {
        aspc.saveAppInitilized(true);
        assertThat(aspc.isAppInitlized() , is(true));
    }
}