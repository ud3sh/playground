package com.ud3sh.birt.client;

import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class StackOverflowAnswersApiTest {

    @Test
    public void stackOverflowMostRecentAnswers() {
        String result = StackOverflowAnswersApi.getMostRecentAnswers();
        assertNotNull(result);
        JSONObject json = new JSONObject(result);
        assertNotNull(json);
        assertNotNull(json.get("items"));

    }

    @Test
    public void stackOverflowHttpsTest() {
        String result = StackOverflowAnswersApi.getMostRecentAnswersHttps();
        assertNotNull(result);
        JSONObject json = new JSONObject(result);
        assertNotNull(json);
        assertNotNull(json.get("items"));
    }

    @Test
    public void stackOverflowQuestionTest(){
        String result = StackOverflowAnswersApi.getQuestion(37012082);
        assertNotNull(result);
        JSONObject json = new JSONObject(result);
        assertNotNull(json);
        assertNotNull(json.get("items"));
    }
}
