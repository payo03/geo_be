package com.spring.geo.common.config;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.spring.geo.common.exception.BusinessException;

@Configuration
public class RestConfig {

    private static final Logger LOGGER = LogManager.getLogger(RestConfig.class);

    private int READ_TIMEOUT = 60000;
    private int CONNECT_TIMEOUT = 60000;
    private int MAX_CONN_TOTAL = 1000;
    private int MAX_CONN_PER_ROUTE = 20;
    private int CONN_TIMEOUT_LIVE = 5;
    private long EVICT_IDLE_CONN = 2000L;

    @Bean("transferRestTemplate")
    public RestTemplate restTemplate() {
        LOGGER.debug("transferRestTemplate");

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(READ_TIMEOUT);
        factory.setConnectTimeout(CONNECT_TIMEOUT);

        HttpClient httpClient = HttpClientBuilder
            .create()
            .setMaxConnTotal(MAX_CONN_TOTAL)
            .setMaxConnPerRoute(MAX_CONN_PER_ROUTE)
            .setDefaultRequestConfig(
                RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()
            )
            .setConnectionTimeToLive(CONN_TIMEOUT_LIVE, TimeUnit.SECONDS)
            .evictIdleConnections(EVICT_IDLE_CONN, TimeUnit.MILLISECONDS)
            .build();

        factory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);

        return restTemplate;
    }

    @Bean("transferRestTemplateBypass")
    public RestTemplate restTemplateByPass2()throws Exception {
        LOGGER.debug("transferRestTemplateBypass");
        RestTemplate restTemplate = null;

        try {

            TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
            SSLContext sslContext = SSLContexts
                .custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory> create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();

            BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(socketFactoryRegistry);
            CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLSocketFactory(sslsf)
                .setConnectionManager(connectionManager)
                .setMaxConnTotal(MAX_CONN_TOTAL)
                .setMaxConnPerRoute(MAX_CONN_PER_ROUTE)
                .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                httpClient
            );
            requestFactory.setConnectTimeout(CONNECT_TIMEOUT);
            requestFactory.setReadTimeout(READ_TIMEOUT);

            restTemplate = new RestTemplate(requestFactory);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new BusinessException("RestConfig.transferRestTemplateBypass Error Occurred");
        }
        return restTemplate;
    }
}