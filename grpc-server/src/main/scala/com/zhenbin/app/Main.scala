package com.zhenbin.app

import cats.effect._
import com.zhenbin.app.db.DB
import io.grpc.ServerServiceDefinition
import io.grpc.netty.NettyServerBuilder
import scala.concurrent.ExecutionContext
import com.zhenbin.app.proto.hello.HelloGrpc

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello, World!")

    implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

    val config = Config.load(None)
    val transactor = DB.mkTransactor[IO](config.db)
    transactor.use { xa =>
      val app = new AppModule[IO](config, xa)
      val helloService: ServerServiceDefinition =
        HelloGrpc.bindService(app.helloGrpc, ExecutionContext.global)

      NettyServerBuilder
        .forPort(5001)
        .addService(helloService).build.start.awaitTermination()
      IO(ExitCode.Success)
    }

  }
}
