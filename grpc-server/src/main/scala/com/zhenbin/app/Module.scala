package com.zhenbin.app

import cats.Monad
import cats.effect.{Async, Concurrent, ContextShift, Sync}
import com.softwaremill.macwire.wire
import com.zhenbin.app.db.DBConfig
import com.zhenbin.app.db.repository.TestRepositoryImpl
import com.zhenbin.app.model.proto.HelloImpl

class AppModule[F[+_]](
    val config: Config,
    val xa: doobie.Transactor[F]
)(
    implicit val contextShift: ContextShift[F],
    implicit val sync: Sync[F],
    implicit val async: Async[F],
    implicit val monad: Monad[F],
    implicit val concurrent: Concurrent[F]
) extends ConfigModule
    with EffModule[F]
    with TransactorModule[F]
    with RepositoryModule[F]
    with GrpcModule[F]

trait MonadModule[F[_]] {
  implicit def monad: Monad[F]
}

trait EffModule[F[_]] extends MonadModule[F] {
  implicit def contextShift: ContextShift[F]
  implicit def concurrent: Concurrent[F]
}

trait TransactorModule[F[_]] {
  def xa: doobie.Transactor[F]
}

trait RepositoryModule[F[_]] {
  self: EffModule[F] with ConfigModule with TransactorModule[F] =>
  lazy val testRepository = wire[TestRepositoryImpl[F]]
}

trait GrpcModule[F[_]] {
  self: EffModule[F] with ConfigModule with RepositoryModule[F] =>
  lazy val helloGrpc = wire[HelloImpl[F]]
}

trait ConfigModule {
  def config: Config
  def dbConfig: DBConfig = config.db
}
