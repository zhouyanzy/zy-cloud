package top.zhouy.shoporder.algorithm;

import lombok.Data;

import java.util.List;

/**
 * @description: ShardRangeConfig
 * @author: zhouy
 * @create: 2020-12-14 17:07:00
 */
@Data
public class ShardRangeConfig {

	private long start;
	
	private long end;
	
	private List<String> datasourceList;

	private List<String> tableList;
}
