package com.eternalcode.gitcheck.github;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.eternalcode.gitcheck.git.GitException;
import com.eternalcode.gitcheck.git.GitRelease;
import com.eternalcode.gitcheck.git.GitRepository;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import org.junit.jupiter.api.Test;

class GitHubReleaseProviderTest {

    @Test
    void testGetLatestReleaseSuccess() {
        String json = """
                {
                  "name": "v1.0.0",
                  "target_commitish": "main",
                  "tag_name": "v1.0.0",
                  "html_url": "https://github.com/test/repo/releases/tag/v1.0.0",
                  "published_at": "2020-01-01T00:00:00Z"
                }
                """;

        HttpClient httpClient = createMockClient(200, json);

        GitHubReleaseProvider provider = new GitHubReleaseProvider(httpClient);
        GitRepository repository = GitRepository.of("test", "repo");

        GitRelease release = provider.getLatestRelease(repository);

        assertNotNull(release);
        assertEquals("v1.0.0", release.getName());
        assertEquals("main", release.getBranch());
        assertEquals("v1.0.0", release.getTag().getTag());
        assertEquals("https://github.com/test/repo/releases/tag/v1.0.0", release.getPageUrl());
    }

    @Test
    void testGetLatestRelease404Error() {
        HttpClient httpClient = createMockClient(404, "");

        GitHubReleaseProvider provider = new GitHubReleaseProvider(httpClient);
        GitRepository repository = GitRepository.of("test", "repo");

        GitException exception = assertThrows(
                GitException.class, () ->
                        provider.getLatestRelease(repository));

        assertTrue(exception.getMessage().contains("404"));
    }

    private HttpClient createMockClient(int statusCode, String body) {
        return new HttpClient() {
            @Override
            public Optional<CookieHandler> cookieHandler() {return Optional.empty();}

            @Override
            public Redirect followRedirects() {return Redirect.NEVER;}

            @Override
            public Optional<ProxySelector> proxy() {return Optional.empty();}

            @Override
            public SSLContext sslContext() {return null;}

            @Override
            public SSLParameters sslParameters() {return new SSLParameters();}

            @Override
            public Optional<Authenticator> authenticator() {return Optional.empty();}

            @Override
            public Version version() {return Version.HTTP_1_1;}

            @Override
            public Optional<Executor> executor() {return Optional.empty();}

            @Override
            public Optional<Duration> connectTimeout() {
                return Optional.of(Duration.ofSeconds(10));
            }

            @Override
            public <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
                assertEquals("https://api.github.com/repos/test/repo/releases/latest", request.uri().toString());

                @SuppressWarnings("unchecked")
                T castedBody = (T) body;

                return new MockHttpResponse<>(request, statusCode, castedBody);
            }

            @Override
            public <T> CompletableFuture<HttpResponse<T>> sendAsync(
                    HttpRequest request,
                    HttpResponse.BodyHandler<T> responseBodyHandler) {
                throw new UnsupportedOperationException("sendAsync not supported in tests");
            }

            @Override
            public <T> CompletableFuture<HttpResponse<T>> sendAsync(
                    HttpRequest request,
                    HttpResponse.BodyHandler<T> responseBodyHandler,
                    HttpResponse.PushPromiseHandler<T> pushPromiseHandler) {
                throw new UnsupportedOperationException("sendAsync with push promises not supported in tests");
            }
        };
    }

    private record MockHttpResponse<T>(HttpRequest request, int statusCode, T body) implements HttpResponse<T> {

        @Override
        public int statusCode() {return statusCode;}

        @Override
        public HttpRequest request() {return request;}

        @Override
        public Optional<HttpResponse<T>> previousResponse() {return Optional.empty();}

        @Override
        public HttpHeaders headers() {return HttpHeaders.of(Collections.emptyMap(), (a, b) -> true);}

        @Override
        public T body() {return body;}

        @Override
        public URI uri() {return request.uri();}

        @Override
        public Version version() {return Version.HTTP_1_1;}

        @Override
        public Optional<SSLSession> sslSession() {return null;}
    }
}
