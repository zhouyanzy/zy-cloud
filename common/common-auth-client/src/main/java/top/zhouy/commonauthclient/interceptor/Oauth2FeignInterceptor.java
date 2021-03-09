package top.zhouy.commonauthclient.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description: feign拦截，添加请求头 Authorization
 * @author: zhouy
 * @create: 2020-12-21 11:16:00
 */
public class Oauth2FeignInterceptor implements RequestInterceptor {

    public static final String BEARER = "bearer";
    public static final String AUTHORIZATION = "Authorization";
    public static final String TOKEN = "token";
    private final OAuth2ClientContext oAuth2ClientContext;
    private final OAuth2ProtectedResourceDetails resource;
    private final String tokenType;
    private final String header;
    private AccessTokenProvider accessTokenProvider;

    public Oauth2FeignInterceptor(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource) {
        this(oAuth2ClientContext, resource, BEARER, AUTHORIZATION);
    }

    public Oauth2FeignInterceptor(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource, String tokenType, String header) {
        this.accessTokenProvider = new AccessTokenProviderChain(Arrays.asList(new AuthorizationCodeAccessTokenProvider(), new ImplicitAccessTokenProvider(), new ResourceOwnerPasswordAccessTokenProvider(), new ClientCredentialsAccessTokenProvider()));
        this.oAuth2ClientContext = oAuth2ClientContext;
        this.resource = resource;
        this.tokenType = tokenType;
        this.header = header;
    }

    @Override
    public void apply(RequestTemplate template) {
        Map<String, Collection<String>> map = template.headers();
        if (map.containsKey(TOKEN) && ! map.get(TOKEN).isEmpty() && map.get(TOKEN).size() > 0) {
            template.header(TOKEN, String.valueOf(map.get("token").toArray()[0]));
        }
        template.header(this.header, new String[]{this.extract(this.tokenType)});
    }

    protected String extract(String tokenType) {
        OAuth2AccessToken accessToken = this.getToken();
        return String.format("%s %s", tokenType, accessToken.getValue());
    }

    public OAuth2AccessToken getToken() {
        OAuth2AccessToken accessToken = this.oAuth2ClientContext.getAccessToken();
        if (accessToken == null || accessToken.isExpired()) {
            try {
                accessToken = this.acquireAccessToken();
            } catch (UserRedirectRequiredException var5) {
                this.oAuth2ClientContext.setAccessToken((OAuth2AccessToken) null);
                String stateKey = var5.getStateKey();
                if (stateKey != null) {
                    Object stateToPreserve = var5.getStateToPreserve();
                    if (stateToPreserve == null) {
                        stateToPreserve = "NONE";
                    }

                    this.oAuth2ClientContext.setPreservedState(stateKey, stateToPreserve);
                }

                throw var5;
            }
        }

        return accessToken;
    }

    protected OAuth2AccessToken acquireAccessToken() throws UserRedirectRequiredException {
        AccessTokenRequest tokenRequest = this.oAuth2ClientContext.getAccessTokenRequest();
        if (tokenRequest == null) {
            throw new AccessTokenRequiredException("Cannot find valid context on request for resource '" + this.resource.getId() + "'.", this.resource);
        } else {
            String stateKey = tokenRequest.getStateKey();
            if (stateKey != null) {
                tokenRequest.setPreservedState(this.oAuth2ClientContext.removePreservedState(stateKey));
            }

            OAuth2AccessToken existingToken = this.oAuth2ClientContext.getAccessToken();
            if (existingToken != null) {
                this.oAuth2ClientContext.setAccessToken(existingToken);
            }

            OAuth2AccessToken obtainableAccessToken = this.accessTokenProvider.obtainAccessToken(this.resource, tokenRequest);
            if (obtainableAccessToken != null && obtainableAccessToken.getAdditionalInformation() != null) {
                LinkedHashMap<String, String> hashMap = (LinkedHashMap) obtainableAccessToken.getAdditionalInformation().get("data");
                OAuth2AccessToken oAuth2AccessToken = DefaultOAuth2AccessToken.valueOf(hashMap);
                this.oAuth2ClientContext.setAccessToken(oAuth2AccessToken);
                return oAuth2AccessToken;
            } else {
                throw new IllegalStateException(" Access token provider returned a null token, which is illegal according to the contract.");
            }
        }
    }

    public void setAccessTokenProvider(AccessTokenProvider accessTokenProvider) {
        this.accessTokenProvider = accessTokenProvider;
    }
}