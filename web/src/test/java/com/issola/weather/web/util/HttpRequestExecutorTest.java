package com.issola.weather.web.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.http.RequestEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HttpRequestExecutorTest {

    @Mock
    private RestOperations restOperations;

    @InjectMocks
    private HttpRequestExecutor httpRequestExecutor;

    @Test
    void executeGetRequest_Success() {
        String expectedResponse = "test response";
        when(restOperations.exchange(
            any(RequestEntity.class),
            eq(String.class)
        )).thenReturn(ResponseEntity.ok(expectedResponse));

        String result = httpRequestExecutor.executeGetRequest("http://test.com", String.class);

        assertEquals(expectedResponse, result);
    }

    @Test
    void executeGetRequest_ThrowsRestClientException() {
        when(restOperations.exchange(
            any(RequestEntity.class),
            eq(String.class)
        )).thenThrow(HttpClientErrorException.class);

        assertThrows(RestClientException.class, () ->
            httpRequestExecutor.executeGetRequest("http://test.com", String.class)
        );
    }

    @Test
    void executeGetRequest_ThrowsIllegalStateException() {
        when(restOperations.exchange(
            any(RequestEntity.class),
            eq(String.class)
        )).thenThrow(RuntimeException.class);

        assertThrows(IllegalStateException.class, () ->
            httpRequestExecutor.executeGetRequest("http://test.com", String.class)
        );
    }
} 