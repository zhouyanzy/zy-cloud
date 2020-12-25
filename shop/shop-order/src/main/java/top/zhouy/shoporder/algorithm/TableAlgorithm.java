package top.zhouy.shoporder.algorithm;

import lombok.extern.log4j.Log4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 表分片算法
 * @author zhouYan
 * @date 2020/4/13 19:46
 */
@Component
@Log4j
public class TableAlgorithm implements PreciseShardingAlgorithm<Long> {
	
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardValue) {
		Long real = shardValue.getValue() % 2;
		String realTable = shardValue.getLogicTableName() + "_" + real;
		if (availableTargetNames.contains(realTable)) {
			return realTable;
		}
		throw new IllegalArgumentException();
	}
}
