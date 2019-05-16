//package nebula.application.config;
//
//@Configuration
//public class OAuth2ServerConfig {
//
//    @Configuration
//    @EnableAuthorizationServer
//    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
//
//        @Autowired
//        private TokenStore tokenStore;
//
//        @Autowired
//        private UserApprovalHandler userApprovalHandler;
//
//        @Autowired
//        @Qualifier("authenticationManagerBean")
//        private AuthenticationManager authenticationManager;
//
//        @Value("${tonr.redirect:http://localhost:8080/tonr2/sparklr/redirect}")
//        private String tonrRedirectUri;
//
//        @Override
//        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//
//            // @formatter:off
//            clients.inMemory().withClient("tonr")
//                    .resourceIds(SPARKLR_RESOURCE_ID)
//                    .authorizedGrantTypes("authorization_code", "implicit")
//                    .authorities("ROLE_CLIENT")
//                    .scopes("read", "write")
//                    .secret("secret")
//                    .redirectUris("http://localhost:8080/tonr2/sparklr/photos")
//                    .and()
//                    .withClient("tonr-with-redirect")
//                    .resourceIds(SPARKLR_RESOURCE_ID)
//                    .authorizedGrantTypes("authorization_code", "implicit")
//                    .authorities("ROLE_CLIENT")
//                    .scopes("read", "write")
//                    .secret("secret")
//                    .redirectUris(tonrRedirectUri)
//                    .and()
//                    .withClient("my-client-with-registered-redirect")
//                    .resourceIds(SPARKLR_RESOURCE_ID)
//                    .authorizedGrantTypes("authorization_code", "client_credentials")
//                    .authorities("ROLE_CLIENT")
//                    .scopes("read", "trust")
//                    .redirectUris("https://anywhere?key=value")
//                    .and()
//                    .withClient("my-trusted-client")
//                    .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//                    .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//                    .scopes("read", "write", "trust")
//                    .accessTokenValiditySeconds(60)
//                    .and()
//                    .withClient("my-trusted-client-with-secret")
//                    .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//                    .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//                    .scopes("read", "write", "trust")
//                    .secret("somesecret")
//                    .and()
//                    .withClient("my-less-trusted-client")
//                    .authorizedGrantTypes("authorization_code", "implicit")
//                    .authorities("ROLE_CLIENT")
//                    .scopes("read", "write", "trust")
//                    .and()
//                    .withClient("my-less-trusted-autoapprove-client")
//                    .authorizedGrantTypes("implicit")
//                    .authorities("ROLE_CLIENT")
//                    .scopes("read", "write", "trust")
//                    .autoApprove(true);
//            // @formatter:on
//        }
//
//        @Bean
//        public TokenStore tokenStore() {
//            return new InMemoryTokenStore();
//        }
//
//        @Override
//        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//            endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
//                    .authenticationManager(authenticationManager);
//        }
//
//        @Override
//        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//            oauthServer.realm("sparklr2/client");
//        }
//
//    }
//
//}
