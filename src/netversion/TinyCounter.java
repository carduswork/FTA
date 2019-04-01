package netversion;
//收信计数器
public class TinyCounter {
	public static int num=0;
	public static void increment() {
		if(num<4)
			num++;
		else {
			num=1;
		}
	}
	
}
