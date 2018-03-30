package com.codezilla.bookmarkreader.article;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TextSizeAdjusterTest
{
    @Mock
    TextSizeAdjuster.ITextView textView;

    TextSizeAdjuster tsa;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
        when(textView.getTextSize()).thenReturn(100f);
        tsa = new TextSizeAdjuster(textView);
    }


    @Test
    public void shouldNotAllowLoverThanMinScale()
    {
        tsa.onScale(0.1f);
        verify(textView).setTextSize(50.0f);
    }

    @Test
    public void shouldSetScaledSizeWhenScaleRangeIsNormal()
    {
        tsa.onScale(0.8f);
        verify(textView).setTextSize(80f);
        tsa.onScale(1.75f);
        verify(textView).setTextSize(175f);
    }


    @Test
    public void shouldNotAllowMoreThanGivenTextSize()
    {
        tsa.onScale(25f);
        verify(textView).setTextSize(300f);
    }
}