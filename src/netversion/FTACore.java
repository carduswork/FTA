package netversion;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FTACore {
	public static Long execFTA(Map<String, LocalDateTime> nodetimeinfo, String localname) {
		// 暂时固定为1
		int k = 1;

		Map<String, LocalDateTime> backup = nodetimeinfo;

		List<Long> periods = new LinkedList<>();

		for (LocalDateTime i : backup.values()) {
			// 时间做差end-start
			Duration duration = Duration.between(i, nodetimeinfo.get(localname));
			periods.add(duration.toMillis());
		} 

		periods.sort(Comparator.naturalOrder());
		periods.remove(nodetimeinfo.size() - 1);
		periods.remove(0);
		Long validation = periods.stream().mapToLong(e -> e).sum() / (nodetimeinfo.size() - 2 * k);

		return validation;
	}
}
