package com.zhenbin.app

import cats.Monad
import cats.effect._
import com.softwaremill.macwire.wire
import com.zhenbin.app.db.DBConfig
import com.zhenbin.app.db.repository.TestRepositoryImpl
import com.zhenbin.app.model.proto.HelloImpl
import com.zhenbin.app.model.repository.TestRepository
import org.slf4j.LoggerFactory
import ch.qos.logback.classic.{Level, Logger}

class AppModule[F[+_]](
    val config: Config,
    val xa: doobie.Transactor[IO]
)(
    implicit val contextShift: ContextShift[F],
    implicit val sync: Sync[F],
    implicit val async: Async[F],
    implicit val monad: Monad[F],
    implicit val concurrent: Concurrent[F]
) extends ConfigModule
    with EffModule[F]
    with TransactorModule
    with RepositoryModule[F]
    with GrpcModule[F]
    with LogModule

trait MonadModule[F[_]] {
  implicit def monad: Monad[F]
}

trait EffModule[F[_]] extends MonadModule[F] {
  implicit def contextShift: ContextShift[F]
  implicit def concurrent: Concurrent[F]
}

trait TransactorModule {
  def xa: doobie.Transactor[IO]
}

trait RepositoryModule[F[_]] {
  self: EffModule[F] with ConfigModule with TransactorModule =>
  lazy val testRepository: TestRepository = wire[TestRepositoryImpl]
}

trait GrpcModule[F[_]] {
  self: EffModule[F] with ConfigModule with RepositoryModule[F] =>
  lazy val helloGrpc = wire[HelloImpl]
}

trait LogModule { self: ConfigModule =>
  LoggerFactory
    .getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME)
    .asInstanceOf[Logger]
    .setLevel(Level.valueOf(config.server.logLevel))
}

trait ConfigModule {
  def config: Config
  def serverConfig: ServerConfig = config.server
  def dbConfig: DBConfig = config.db
}
