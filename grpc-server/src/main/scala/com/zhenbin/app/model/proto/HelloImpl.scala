package com.zhenbin.app.model.proto

import cats.effect.Sync
import com.zhenbin.app.model.repository.TestRepository

import scala.concurrent.Future
import com.zhenbin.app.proto.hello.{HelloGrpc, HelloReply, HelloRequest}

class HelloImpl[F[_]: Sync](testRepository: TestRepository[F])
    extends HelloGrpc.Hello {
  override def sayHello(request: HelloRequest): Future[HelloReply] = {
    println(request.name)
    testRepository.getTestContent
    Future.successful(HelloReply(message = "grpc request ok."))
  }
}
