package com.zhenbin.app.proto.hello

object HelloGrpc {
  val METHOD_SAY_HELLO: _root_.io.grpc.MethodDescriptor[com.zhenbin.app.proto.hello.HelloRequest, com.zhenbin.app.proto.hello.HelloReply] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("com.zhenbin.app.proto.Hello", "SayHello"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[com.zhenbin.app.proto.hello.HelloRequest])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[com.zhenbin.app.proto.hello.HelloReply])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(com.zhenbin.app.proto.hello.HelloProto.javaDescriptor.getServices.get(0).getMethods.get(0)))
      .build()
  
  val SERVICE: _root_.io.grpc.ServiceDescriptor =
    _root_.io.grpc.ServiceDescriptor.newBuilder("com.zhenbin.app.proto.Hello")
      .setSchemaDescriptor(new _root_.scalapb.grpc.ConcreteProtoFileDescriptorSupplier(com.zhenbin.app.proto.hello.HelloProto.javaDescriptor))
      .addMethod(METHOD_SAY_HELLO)
      .build()
  
  trait Hello extends _root_.scalapb.grpc.AbstractService {
    override def serviceCompanion = Hello
    def sayHello(request: com.zhenbin.app.proto.hello.HelloRequest): scala.concurrent.Future[com.zhenbin.app.proto.hello.HelloReply]
  }
  
  object Hello extends _root_.scalapb.grpc.ServiceCompanion[Hello] {
    implicit def serviceCompanion: _root_.scalapb.grpc.ServiceCompanion[Hello] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = com.zhenbin.app.proto.hello.HelloProto.javaDescriptor.getServices.get(0)
    def scalaDescriptor: _root_.scalapb.descriptors.ServiceDescriptor = com.zhenbin.app.proto.hello.HelloProto.scalaDescriptor.services(0)
    def bindService(serviceImpl: Hello, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
      _root_.io.grpc.ServerServiceDefinition.builder(SERVICE)
      .addMethod(
        METHOD_SAY_HELLO,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[com.zhenbin.app.proto.hello.HelloRequest, com.zhenbin.app.proto.hello.HelloReply] {
          override def invoke(request: com.zhenbin.app.proto.hello.HelloRequest, observer: _root_.io.grpc.stub.StreamObserver[com.zhenbin.app.proto.hello.HelloReply]): Unit =
            serviceImpl.sayHello(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .build()
  }
  
  trait HelloBlockingClient {
    def serviceCompanion = Hello
    def sayHello(request: com.zhenbin.app.proto.hello.HelloRequest): com.zhenbin.app.proto.hello.HelloReply
  }
  
  class HelloBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[HelloBlockingStub](channel, options) with HelloBlockingClient {
    override def sayHello(request: com.zhenbin.app.proto.hello.HelloRequest): com.zhenbin.app.proto.hello.HelloReply = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_SAY_HELLO, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): HelloBlockingStub = new HelloBlockingStub(channel, options)
  }
  
  class HelloStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[HelloStub](channel, options) with Hello {
    override def sayHello(request: com.zhenbin.app.proto.hello.HelloRequest): scala.concurrent.Future[com.zhenbin.app.proto.hello.HelloReply] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_SAY_HELLO, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): HelloStub = new HelloStub(channel, options)
  }
  
  def bindService(serviceImpl: Hello, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition = Hello.bindService(serviceImpl, executionContext)
  
  def blockingStub(channel: _root_.io.grpc.Channel): HelloBlockingStub = new HelloBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): HelloStub = new HelloStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = com.zhenbin.app.proto.hello.HelloProto.javaDescriptor.getServices.get(0)
  
}