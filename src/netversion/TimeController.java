package netversion;

import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

public class TimeController {
	static String localaddress = "127.0.0.1";

	public static void main(String[] args) throws Exception {
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime[] timelist = new LocalDateTime[5];
		MulticastSocket socket = new MulticastSocket(8000);

		// 获取本机IP，考虑到多网卡的情况
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		if (interfaces != null) {
			while (interfaces.hasMoreElements()) {
				try {
					NetworkInterface network = interfaces.nextElement();
					if ("eno1".equals(network.getName())) { // 验证只有eth0才会获取ip
						Enumeration<InetAddress> addresses = network.getInetAddresses();
						if (addresses != null) {
							while (addresses.hasMoreElements()) {
								try {
									InetAddress address = addresses.nextElement();
									if (address.isSiteLocalAddress()) {
										localaddress = address.toString();
									}
								} catch (Throwable e) {
									e.printStackTrace();
								}
							}
						}
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
		TinyCounter counter = new TinyCounter();
		NormalServer mcA = new NormalServer();
		mcA.setGroup(InetAddress.getByName("224.0.0.111"));
		NormalClient mcB = new NormalClient(localaddress, InetAddress.getByName("224.0.0.111"), socket, counter);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// System.out.println("IMP 当前时间" + this.scheduledExecutionTime());
				// 10秒钟广播一次
				mcA.send(localaddress, localDateTime.toString(), socket);
				if (counter.num == 3) {// 调用fta
						
				}
			}
		}, 10000);

		timelist[0] = localDateTime;

	}
}
