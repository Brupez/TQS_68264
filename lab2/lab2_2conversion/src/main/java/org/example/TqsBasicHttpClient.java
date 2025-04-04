package org.example;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class TqsBasicHttpClient implements ISimpleHttpClient{

    @Override
    public String doHttpGet(String url) throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = client.execute(request);

            try {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity);

            } finally {
                if (response != null)
                    response.close();
            }
        }
    }
}
