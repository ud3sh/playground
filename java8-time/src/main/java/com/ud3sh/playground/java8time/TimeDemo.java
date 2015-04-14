package com.ud3sh.playground.java8time;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeDemo {

    public static void timeDifference() {
        LocalDateTime april1st = LocalDateTime.of(2015, 04, 01, 11, 52, 56);
        LocalDateTime aprilEnd = LocalDateTime.of(2015, 04, 30, 10, 52, 56);
        System.out.printf("Time between %s and %s = %d seconds\n",
                april1st.toString(), aprilEnd.toString(),
                ChronoUnit.SECONDS.between(april1st, aprilEnd));

        System.out.printf("Time between %s and %s = %d seconds\n",
                aprilEnd.toString(), april1st.toString(),
                ChronoUnit.SECONDS.between(aprilEnd, april1st)); //negative

        System.out.printf("Time between %s and %s = %d days\n",
                april1st.toString(), aprilEnd.toString(),
                ChronoUnit.DAYS.between(april1st, aprilEnd));

        System.out.printf("Time between %s and %s = %d days\n",
                aprilEnd.toString(), april1st.toString(),
                ChronoUnit.DAYS.between(aprilEnd, april1st)); //negative

    }

    public static void main(String[] args) {
        timeDifference();

    }

}
