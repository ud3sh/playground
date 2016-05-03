package com.ud3sh.birt.client.util;


import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class Rest {

    public static String get(final String url) {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);

        try {
            int statusCode = client.executeMethod(method);
            String result;
            if (statusCode == HttpStatus.SC_OK) {
                Header contentEncodingHeader = method.getResponseHeader("content-encoding");
                String contentEncoding = contentEncodingHeader == null ? null: contentEncodingHeader.getValue();
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(getInputStream(method.getResponseBodyAsStream(), contentEncoding)))) {
                    StringBuilder sb=new StringBuilder();
                    String line;
                    while((line=br.readLine()) != null) {
                        sb.append(line);
                    }
                    result = sb.toString();
                }
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return "error";
    }

    private static InputStream getInputStream(final InputStream responseBodyInputStream,
                                              final String contentEncoding) throws IOException{
        if ("gzip".equalsIgnoreCase(contentEncoding)){
            return new GZIPInputStream(responseBodyInputStream);
        } else if ("deflate".equalsIgnoreCase(contentEncoding)) {
            return new InflaterInputStream(responseBodyInputStream);
        }
        else return responseBodyInputStream;

    }
}
