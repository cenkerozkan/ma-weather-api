package client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class HttpRequestExecutor implements IHttpRequestExecutor
{
    private final RestOperations restOperations;

    @Override
    public <T> T executeGetRequest(String url, Class<T> resultClass)
    {
        try
        {
            // TODO: I'm not sure if 'fromUriString' is the right method to use here.
            //       I'll check if it works or not.
            URI uri = UriComponentsBuilder.fromUriString(url).build().toUri();
            RequestEntity<Void> requestEntity = RequestEntity.get(uri).build();

            ResponseEntity<T> result = restOperations.exchange(requestEntity, resultClass);

            return result.getBody();
        }
        catch (HttpClientErrorException | HttpServerErrorException ex)
        {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND)
            {
                log.info("Requested resource was not found url: {}", url);
            }

            log.warn("Couldn't get successful result from http request status:{} url: {}", ex.getStatusCode(), url);

            throw new RestClientException("Couldn't get successful result from http request", ex);
        }
        catch (Exception ex)
        {
            log.error("Unknown error occurred while executing http request for url: {}", url, ex);

            throw new IllegalStateException("Unknown error occurred while executing http request", ex);
        }
    }
}