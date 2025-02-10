package client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;

/**
 * @author sa
 * @date 17.05.2021
 * @time 17:02
 */
public interface IHttpRequestExecutor
{
    <T> T executeGetRequest(String url, Class<T> resultClass);
}

