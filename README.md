# izetta
[![CircleCI](https://circleci.com/gh/pine/izetta/tree/master.svg?style=shield)](https://circleci.com/gh/pine/izetta/tree/master)

## Requirements

- JDK 11 or later

## Libraries

- Spring Boot 2.x

## Getting started

```sh
$ cp app/src/main/resources/application-sample.yml app/src/main/resources/application-local.yml
$ vim app/src/main/resources/application-local.yml

$ ./gradlew :app:bootRun
```

## Development
### JDK
For macOS users.

```
$ brew tap adoptopenjdk/openjdk
$ brew cask install adoptopenjdk11
```

### Deployment

```sh
$ heroku apps:create your-app
$ heroku config:set SPRING_PROFILES_ACTIVE=prod
$ heroku config:set TZ=Asia/Tokyo
$ heroku config:set 'JAVA_OPTS=-XX:+UseStringDeduplication'

# Deploy JAR file
$ ./gradlew build
$ heroku plugins:install java
$ heroku deploy:jar --jar app/build/libs/app.jar --jdk 11
```

## See also

- [izetta-obsolete](https://github.com/pine/izetta-obsolete) Old version

## License
MIT &copy; [Pine Mizune](https://profile.pine.moe/)
