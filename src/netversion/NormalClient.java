package netversion;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class NormalClient {
//	private InetAddress group;
//	private static int port=8000;
	public NormalClient(String localaddress,InetAddress group,MulticastSocket socket,TinyCounter c) throws IOException{
		//this.group = group;
		//socket.joinGroup(group);
		final byte[] buf = new byte[256];
		new Thread(new Runnable() {
			@Override
			public void run() {
				//监听
				while (true) {
					try {
						DatagramPacket packet = new DatagramPacket(buf, buf.length);
						socket.receive(packet);
						
						c.increment();
						String msg = new String(packet.getData());
						System.out.println("本机"+localaddress+"接受到的数据：" + msg);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
	}
	// ip地址列表
//	private static List<String> addresslist = new ArrayList<>();
//	private static Map<String, LocalDateTime> nodetimeinfo = new HashMap<String, LocalDateTime>();
//	private static final ExecutorService es = Executors.newFixedThreadPool(5);
//
//	private static void start() {
//		for (int i = 0; i < 4; i++) {
//			TimeClient tcClient = new TimeClient();
//			tcClient.setHost(addresslist.get(i));
//			es.execute(tcClient);
//		}
//		es.shutdown();
//	}
//	// 客户端
//
//	public static class TimeClientHandler extends ChannelInboundHandlerAdapter {
//		@Override
//		public void channelRead(ChannelHandlerContext ctx, Object msg) {
//			ByteBuf m = (ByteBuf) msg; // (1)
//			try {
//				long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
//				InetSocketAddress inSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
//				Date buffer = new Date(currentTimeMillis);
//				Instant instant = buffer.toInstant();
//				ZoneId zone = ZoneId.systemDefault();
//				LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
//				// LocalDate localDate = localDateTime.toLocalDate();
//				nodetimeinfo.put(inSocketAddress.getAddress().getHostAddress(), localDateTime);
//				System.out.println(new Date(currentTimeMillis));
//				ctx.close();
//			} finally {
//				m.release();
//			}
//		}
//
//		@Override
//		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//			cause.printStackTrace();
//			ctx.close();
//		}
//	}
//
//	private static class TimeClient implements Runnable {
//		String hoString;
//
//		public void setHost(String host) {
//			hoString = host;
//		}
//
//		public void run() {
//			EventLoopGroup workerGroup = new NioEventLoopGroup();
//
//			try {
//				Bootstrap b = new Bootstrap();
//				b.group(workerGroup);
//				b.channel(NioSocketChannel.class);
//				b.option(ChannelOption.SO_KEEPALIVE, true);
//				b.handler(new ChannelInitializer<SocketChannel>() {
//					@Override
//					public void initChannel(SocketChannel ch) throws Exception {
//						// ch.pipeline().addLast(new TimeClientHandler());
//						ChannelPipeline pipeline = ch.pipeline();
//						pipeline.addLast("frameDecoder",
//								new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
//						pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
//						pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
//						pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
//						pipeline.addLast(new TimeClientHandler());
//					}
//				});
//
//				// 遍历地址列表
//				// addresslist.forEach((v) -> {
//				// 启动客户端
//				try {
//					ChannelFuture f = b.connect(hoString, 8000).sync();
//					if (f.isSuccess()) {
//						System.out.println(String.format("connect server(%s:%s) sucess", hoString, 8000));
//					}
//					// 监听端口关闭
//					// System.out.println("成功连接"+v);
//					f.channel().closeFuture().sync();
//					System.out.println("连接断开");
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				// });
//
//			} finally {
//				workerGroup.shutdownGracefully();
//			}
//		}
//	}
//
//	public static void main(String[] args) throws Exception {
//		// TODO Auto-generated method stub
//		addresslist.add("10.5.150.61");
//		addresslist.add("10.5.150.31");
//
//		// 客户端逻辑
//		NormalClient.start();
//
//		// 计算校正值
//		FTACore.execFTA(nodetimeinfo, InetAddress.getLocalHost().getHostAddress());
//		// 校正时间，速率校正
//		String cmd = "";
//		String ostype = System.getProperty("os.name");

		// if (ostype.matches("\"^(?i)Windows.*$")) {
		// // 设置时分秒
		// cmd = " cmd /c time 22:35:00";
		// Runtime.getRuntime().exec(cmd);
		// // 设置年份
		// cmd = " cmd /c date 2009-03-26";
		// Runtime.getRuntime().exec(cmd);
		//
		// } else {
		// cmd = " date -s 20090326";
		// Runtime.getRuntime().exec(cmd);
		// // 格式 HH:mm:ss
		// cmd = " date -s 22:35:00";
		// Runtime.getRuntime().exec(cmd);
		// }
	//}

}
