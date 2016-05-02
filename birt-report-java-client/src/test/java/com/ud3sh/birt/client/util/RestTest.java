package com.ud3sh.birt.client.util;

import org.json.JSONObject;
import org.junit.Test;


import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

//Integration Tests
public class RestTest {

    @Test
    public void getJson(){
        String result = Rest.get("http://httpbin.org/ip");
        assertNotNull(result);
        assertTrue(result.trim().startsWith("{"));
        assertTrue(result.trim().endsWith("}"));
        JSONObject json = new JSONObject(result);
        assertNotNull(json.get("origin"));
    }

    @Test
    public void getGzip(){
        String result = Rest.get("http://httpbin.org/gzip");
        assertNotNull(result);
        JSONObject json = new JSONObject(result);
        assertNotNull(json.get("gzipped"));
        assertTrue(((Boolean)json.get("gzipped")));
    }

    @Test
    public void getDeflate(){
        String result = Rest.get("http://httpbin.org/deflate");
        assertNotNull(result);
        JSONObject json = new JSONObject(result);
        assertNotNull(json.get("deflated"));
        assertTrue(((Boolean)json.get("deflated")));
    }

    @Test
    public void testUtf8() {
        String result = Rest.get("http://httpbin.org/encoding/utf8");
        assertNotNull(result);
        assertTrue(result.trim().startsWith("<h1>Unicode Demo</h1>"));
        assertTrue(result.indexOf("The Greek anthem:  Σὲ") > 0);
    }


}