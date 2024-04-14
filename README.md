# keycloak + springboot 3 + redis backchannel logout example project

# Preparing Environment
- Redis should be running on port 6379.
- The Redis password should be set to "test".
- Create a Keycloak realm named "myrealm" and a client named "myblog".
- Update the Keycloak client secret in the application.yml file.


# Getting Started
Run `docker compose up -d`.
Access `http://localhost:8080`.


- [use-keycloak-spring-adapter-with-spring-boot-3](https://stackoverflow.com/questions/74571191/use-keycloak-spring-adapter-with-spring-boot-3/74572732#74572732)
