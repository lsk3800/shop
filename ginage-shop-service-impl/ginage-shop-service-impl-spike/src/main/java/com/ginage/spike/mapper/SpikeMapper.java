
package com.ginage.spike.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @date:2020年6月14日
 * @description:
 * @Copyright: ginage.com
 *
 */

@Mapper
public interface SpikeMapper {
	@Update("UPDATE SECKILL SET INVENTORY=INVENTORY-1 WHERE PRODUCT_ID=#{productId} "
			+ "AND INVENTORY>0")
	public int updateSpikeInventory(Long productId);
}
