# playframework 2.1.5 + slick2 with mysql を cloudn paas で動かす

## Build.scala

```
  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "org.cloudfoundry" % "auto-reconfiguration" % "0.6.6",
    "mysql" % "mysql-connector-java" % "5.1.29",
    "com.typesafe.slick" %% "slick" % "2.0.0"
  )
```

```
"org.cloudfoundry" % "auto-reconfiguration" % "0.6.6",
```

この一文がないとオートコネクションが動かない、と思う。
使わないけど。

また、resolverに以下を追加する。

```
resolvers += "Spring Milestone Repository" at "http://maven.springframework.org/milestone"
```


## conf/cloud.conf

````
include "application.conf"
include "cloudfoundry.properties"

db.default.driver=${?cloud.services.{tasks-db}.connection.driver}
db.default.url=${?cloud.services.{tasks-db}.connection.url}
db.default.password=${?cloud.services.{tasks-db}.connection.password}
db.default.user=${?cloud.services.{tasks-db}.connection.username}
````

tasks-db の箇所はすでに存在している接続したいサービス名にしておく

## conf/cloudfoundry.properties

```
autoconfig=false
```

自動的にserviceにbindする設定をoffにする


# manifest.yml

```
---
applications:
- name: play-on-cloudn-paas
  framework: play
  runtime: java7
  memory: 1G
  instances: 1
  url: play-on-cloudn-paas.${target-base}
  path: dist/play-on-cloudn-paas-1.0-SNAPSHOT.zip
  services:
    tasks-db:
      plan: free
      type: mysql
```

cloud.confで設定したサービス名がなんなのかとか設定
distする対象のzipを定義

# deploy

*前提条件、vmcコマンドをinstallしておいてloginができる状態にしておくこと*

*autoconfig=falseにしているのでDBは作成しておくこと*

アプリケーションディレクトリで

````
$ play clean compile
$ play -Dconfig.file=conf/cloud.conf dist
$ vmc push
````

これで動く。

