package io.keycloak.keycloaklogout.repository;

//@Component
//@EnableRedisHttpSession
//public class CustomRedisSessionRepository extends RedisOperationsSessionRepository {
//
//    public CustomRedisSessionRepository(RedisOperations<Object, Object> sessionRedisOperations, HttpSessionIdResolver sessionIdResolver) {
//        super(sessionRedisOperations);
//        this.setSessionIdResolver(sessionIdResolver);
//    }
//
//    @Override
//    public void save(RedisSession session) {
//        // 세션 저장 전에 원하는 속성을 추가
//        session.setAttribute("customAttribute", "customValue");
//
//        super.save(session);
//    }
//}