package com.ud3sh.birt.client;

import com.ud3sh.birt.client.util.Rest;


public class StackOverflowAnswersApi {

    public static String getMostRecentAnswers() {
        return Rest.get("https://api.stackexchange.com/2.2/answers?order=desc&sort=activity&site=stackoverflow");
    }

    public static String getQuestion(final int questionId){
        final String url = String.format(
                            "http://api.stackexchange.com/2.2/questions/%d?order=desc&sort=activity&site=stackoverflow",
                            questionId);
        return Rest.get(url);
    }

}
