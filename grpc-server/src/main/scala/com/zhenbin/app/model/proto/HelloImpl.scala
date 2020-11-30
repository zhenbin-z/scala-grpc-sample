package com.zhenbin.app.model.proto

import cats.effect.IO
import com.zhenbin.app.model.repository.TestRepository

import scala.concurrent.Future
import com.zhenbin.app.proto.hello.{HelloGrpc, HelloReply, HelloRequest}

class HelloImpl(testRepository: TestRepository) extends HelloGrpc.Hello {
  override def sayHello(request: HelloRequest): Future[HelloReply] =
    testRepository.getTestContent
      .flatMap {
        case Some(value) =>
          IO.pure(Future.successful(HelloReply(message = value)))
        case None =>
          IO.pure(Future.successful(HelloReply(message = "データがありません。")))
      }.unsafeRunSync()
}
