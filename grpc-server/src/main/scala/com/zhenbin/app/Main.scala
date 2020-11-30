package com.zhenbin.app

import com.zhenbin.app.proto.hello.{HelloGrpc, HelloReply, HelloRequest}
import io.grpc.Server
import io.grpc.netty.NettyServerBuilder

import scala.concurrent.{ExecutionContext, Future}

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello, World!")
    val server = new RpcServer(ExecutionContext.global)
    server.start()
    server.blockUntilShutdown()
  }

}

class RpcServer(e: ExecutionContext) { self =>
  private[this] var server: Server = null

  def start(): Unit = {
    server = NettyServerBuilder
      .forPort(5001).addService(
        HelloGrpc.bindService(new HelloImp, e)
      ).build.start
  }

  def stop(): Unit =
    if (server != null) server.shutdown()

  def blockUntilShutdown(): Unit =
    if (server != null) server.awaitTermination()
}

class HelloImp extends HelloGrpc.Hello {
  override def sayHello(request: HelloRequest): Future[HelloReply] = {
    println(request.name)
    Future.successful(HelloReply(message = "grpc request ok."))
  }
}
