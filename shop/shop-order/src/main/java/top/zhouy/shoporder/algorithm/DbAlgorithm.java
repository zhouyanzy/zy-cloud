package top.zhouy.shoporder.algorithm;

import lombok.extern.log4j.Log4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 库分片算法
 * @author zhouYan
 * @date 2020/4/13 19:46
 */
@Component
@Log4j
public class DbAlgorithm implements PreciseShardingAlgorithm<Long> {

	private static List<ShardRangeConfig> configs = new ArrayList<>();
	
	static {
		// 配置范围，多少到多少之前存在哪个库
		ShardRangeConfig config = new ShardRangeConfig();
		config.setStart(1);
		config.setEnd(3000000);
		config.setDatasourceList(Arrays.asList("order0", "order1"));
		configs.add(config);

/*		config = new ShardRangeConfig();
		config.setStart(31);
		config.setEnd(60);
		config.setDatasourceList(Arrays.asList("dss0", "dss1"));
		configs.add(config);*/
	}
	
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardValue) {
		Optional<ShardRangeConfig> configOptional = configs.stream()
				.filter(c -> shardValue.getValue() >= c.getStart() && shardValue.getValue() <= c.getEnd()).findFirst();
		if (configOptional.isPresent()) {
			ShardRangeConfig rangeConfig = configOptional.get();
			for (String ds : rangeConfig.getDatasourceList()) {
				if (ds.endsWith(shardValue.getValue() % 2 + "")) {
					log.info("库" + ds);
					return ds;
				}
			}
		}
		throw new IllegalArgumentException();
	}
}
