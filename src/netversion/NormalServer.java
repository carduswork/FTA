package netversion;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class NormalServer {
	//private MulticastSocket socket;
	private static int port=8000;
	//private String id;
	private InetAddress group;
	public void send(String localaddress,String msg,MulticastSocket socket) {
		byte[] bytes = msg.getBytes();
		try {
			//socket.leaveGroup(group);//发送的人不接收组播消息
			msg = "本机"+localaddress+"组播数据：" + msg;
			System.out.println(msg);
			socket.send(new DatagramPacket(bytes, bytes.length, group, port));
			socket.joinGroup(group);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setGroup(InetAddress group) {
		this.group = group;
	}
	
	// 服务端
//	public static class MyServerHandler extends ChannelInboundHandlerAdapter {
//		private static final Gson GSON = new GsonBuilder().create();
//	    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	    private static final AtomicInteger response = new AtomicInteger();
//		@Override
//		public void channelActive(final ChannelHandlerContext ctx) {			
//			Channel ch = ctx.channel();
//			 ChannelGroups.add(ch);
//			 try {
//				super.channelActive(ctx);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
////	        if (ChannelGroups.size() > 0) {
////	            Message msg = new Message(ch.remoteAddress().toString().substring(1), SDF.format(new Date()));
////	            //进行广播
////	            ChannelGroups.broadcast(GSON.toJson(msg), new ChannelMatchers());
////	        }
//	       
//	
//		}
//
//		@Override
//	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//	        Channel ch = ctx.channel();
//	        if (ChannelGroups.contains(ch) && String.valueOf(msg).equals("welcome")) {
//	            System.out.println(String.format("receive [%s] from [%s] at [%s]",
//	                    String.valueOf(msg), ch.remoteAddress().toString().substring(1), SDF.format(new Date())));
//	            response.incrementAndGet();
//	        }
//	        synchronized (response) {
//	            System.out.println(response.get() + "\t" + ChannelGroups.size());
//	            if (response.get() == ChannelGroups.size() - 1) {
//	                System.out.println("server close all connected channel");
//	                ChannelGroups.disconnect();
//	                response.set(0);
//	            }
//	        }
//	    }
//
//	    @Override
//	    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//	        ctx.flush();
//	    }
//
//	    @Override
//	    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//	        ctx.close();
//	    }
//
//	    @Override
//	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//	        cause.printStackTrace();
//	        ChannelGroups.discard(ctx.channel());
//	        response.decrementAndGet();
//	    }
//
//	    public static class ChannelMatchers implements ChannelMatcher {
//	        public boolean matches(Channel channel) {
//	            return true;
//	        }
//	    }
//	}
//
//	public static class MyServer {
//		int port;
//
//		public MyServer(int port) {
//			this.port = port;
//		}
//
//		public void run() throws Exception {
//			EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
//			EventLoopGroup workerGroup = new NioEventLoopGroup();
//			try {
//				ServerBootstrap b = new ServerBootstrap(); // (2)
//				b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // (3)
//						.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
//							@Override
//							public void initChannel(SocketChannel ch) throws Exception {
//								ChannelPipeline pipeline = ch.pipeline();
//	                            pipeline.addLast("frameDecoder",
//	                                    new LengthFieldBasedFrameDecoder(
//	                                            Integer.MAX_VALUE, 0, 4, 0, 4));
//	                            pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
//	                            pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
//	                            pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
//	                            pipeline.addLast(new MyServerHandler());
//	
//							}
//						}).localAddress(, 8000).option(ChannelOption.SO_BACKLOG, 128).option(ChannelOption.IP_MULTICAST_IF,) // (5)
//						.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
//
//				// 绑定端口，开始接收进来的连接
//				ChannelFuture f = b.bind(port).sync(); // (7)
//				 if (f.isDone()) {
//		                System.out.println(String.format("server bind port %s sucess", port));
//		            }
//				// 等待服务器 socket 关闭 。
//				// 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
//				f.channel().closeFuture().sync();
//			} finally {
//				workerGroup.shutdownGracefully();
//				bossGroup.shutdownGracefully();
//			}
//		}
//	}
}
