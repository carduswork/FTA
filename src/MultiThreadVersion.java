import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadVersion {
	// 每个节点的校正值
	private static Map<String, Double> valid = new HashMap<String, Double>();
	// 待处理的节点
	private static Map<String, Double> nodes = new HashMap<String, Double>();
	static Boolean ifallfinished = true;
	public static void main(String[] args) {
		
		int k = 1;
		new HashMap<>();
		nodes.put("1号", 10.0);
		nodes.put("2号", 6.0);
		nodes.put("3号", 7.0);
		nodes.put("4号", 6.0);
		nodes.put("5号", 9.0);

		// 多线程的方式
		ExecutorService pool = Executors.newFixedThreadPool(20);

		// 所有的future
		Map<String, Future<Boolean>> fs = new HashMap<String, Future<Boolean>>();

		nodes.forEach((key, v) -> {
			FTA f = new FTA(key, v, nodes.values(), k);
			fs.put(key, pool.submit(f));
		});
		
		fs.forEach((key, value) -> {
			try {
				ifallfinished = ifallfinished && value.get();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		});

		if (ifallfinished) {
			//更新值
			pool.submit(new UPDATENodes());
		}
		pool.shutdown();
	}

	private static class FTA implements Callable<Boolean> {
		String key;
		double v;
		Collection<Double> allVal;
		int k;

		FTA(String key, double v, Collection<Double> allVal, int k) {
			this.key = key;
			this.v = v;
			this.allVal = allVal;
			this.k = k;
		}

		@Override
		public Boolean call() throws Exception {
			Iterator<Double> iterator = allVal.iterator();
			List<Double> buffer = new ArrayList<Double>();
			while (iterator.hasNext()) {
				buffer.add(iterator.next());
			}

			buffer.sort(Comparator.naturalOrder());
			buffer.remove(4);
			buffer.remove(0);
			Double newvalue = v + buffer.stream().mapToDouble(e -> e).sum() / (allVal.size() - 2 * k);
			BigDecimal dFormat = new BigDecimal(newvalue);
			newvalue = dFormat.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			valid.put(key, newvalue);
			return true;
		}
	}
	private static class UPDATENodes implements Callable<Boolean>{
		@Override
		public Boolean call() throws Exception {
			
			nodes.forEach((k,v)->{
				System.out.println(k+"节点的值从"+v+"被校正为"+v+valid.get(k));
				nodes.put(k, v+valid.get(k));
			});
			
			return true;
		}
	}
}
