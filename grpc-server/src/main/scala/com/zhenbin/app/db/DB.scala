package com.zhenbin.app.db

import doobie._
import doobie.hikari._
import cats.effect._
import com.zaxxer.hikari.HikariConfig

case class DBConfig(
    host: String,
    port: Int,
    db: String,
    username: String,
    password: String
) {

  def toHikariConfig: HikariConfig = {
    val config = new HikariConfig
    config.setJdbcUrl(
      s"jdbc:postgresql://$host:${port.toString}/$db"
    )
    config.setUsername(username)
    config.setPassword(password)
    config
  }
}

object DB {
  val awaitConnectionThreadPoolSize: Int = 32

  def mkTransactor[F[_]: Sync: Async: ContextShift](
      config: DBConfig
  ): Resource[F, Transactor[F]] =
    for {
      ce <- ExecutionContexts.fixedThreadPool[F](
        DB.awaitConnectionThreadPoolSize
      )
      be <- Blocker[F]
      xa <- HikariTransactor.fromHikariConfig[F](
        config.toHikariConfig,
        ce,
        be
      )
    } yield xa
}
