package com.zhenbin.app

import org.scalatra._
import com.zhenbin.app.proto.hello.HelloGrpc.HelloBlockingStub
import com.zhenbin.app.proto.hello.HelloRequest

class MyScalatraServlet(blockingStub: HelloBlockingStub)
    extends ScalatraServlet {

  get("/:name") {
    val rpcRequest = HelloRequest(name = params("name"))
    val response = blockingStub.sayHello(rpcRequest)
    Ok(response.message)
  }

}
