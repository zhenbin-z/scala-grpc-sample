import org.scalatra._
import com.zhenbin.app._
import com.zhenbin.app.proto.hello.HelloGrpc
import io.grpc.netty.NettyChannelBuilder
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    val channel =
      NettyChannelBuilder.forAddress("127.0.0.1", 5001).usePlaintext().build
    val blockingStub = HelloGrpc.blockingStub(channel)
    context.mount(new MyScalatraServlet(blockingStub), "/*")
  }
}
