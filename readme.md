# Core vertX

Đây là project core VertX được viết trong thời gian mình làm việc tại VMG. Cung cấp những phần cơ bản cho việc sử dụng và làm quen với lập trình hướng sự kiện

## Installation (Cài đặt)
Đọc trong phần HDSD.docx

Import locallib bằng list lệnh sau:
```bash
mvn install:install-file -Dfile=.\lib\bonecp-0.8.0.RELEASE.jar -DgroupId=local.bonecp -DartifactId=bonecp -Dversion=0.1.0 -Dpackaging=jar
mvn install:install-file -Dfile=.\lib\commons-codec-1.9.jar -DgroupId=local.commons.codec -DartifactId=commons-codec -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=.\lib\guava-26.0-jre.jar -DgroupId=local.guava -DartifactId=guava -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=.\lib\log4j-1.2.17.jar -DgroupId=local.log4j -DartifactId=log4j -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=.\lib\log4j-rolling-appender.jar -DgroupId=local.log4j.rolling.appender -DartifactId=log4j-rolling-appender -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=.\lib\mail-1.4.7.jar -DgroupId=local.mail -DartifactId=mail -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=.\lib\slf4j-api-1.7.25.jar -DgroupId=local.slf4j.api -DartifactId=slf4j-api -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=.\lib\slf4j-ext-1.7.25.jar -DgroupId=local.slf4j.ext -DartifactId=slf4j-ext -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=.\lib\ojdbc8.jar -DgroupId=local.ojdbc8 -DartifactId=ojdbc8 -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=.\lib\CryptStarApi.jar -DgroupId=local.crypt.star.api -DartifactId=CryptStarApi -Dversion=0.1.0 -Dpackaging=jar
mvn install:install-file -Dfile=.\lib\jtds-1.3.1.jar -DgroupId=local.jtds -DartifactId=jtds -Dversion=1.0 -Dpackaging=jar
```
##Project liên quan có sử dụng
DocTool
Infosky