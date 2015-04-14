package com.ud3sh.playground.java8time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import junit.framework.Assert;

import org.junit.Test;

public class Java8TimeDemoTest  {


    @Test
    public void timeDifferenceInMillisAssociative(){
         LocalDateTime april1st = LocalDateTime.of(2015, 04, 01, 11, 52, 56, 0); //precise up to second
         LocalDateTime aprilEnd = LocalDateTime.of(2015, 04, 29, 21, 32, 53, 0); //precise up to second

         long milliDifference = ChronoUnit.MILLIS.between(april1st, aprilEnd);
         Assert.assertEquals("Expecting milli difference to be the same", aprilEnd, april1st.plus(Duration.ofMillis(milliDifference)));



    }

}
