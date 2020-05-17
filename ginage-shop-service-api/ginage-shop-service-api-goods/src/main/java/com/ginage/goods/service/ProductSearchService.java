
package com.ginage.goods.service;

/**
*@date:2020年5月5日
*@description:
*@Copyright: ginage.com
*
*/

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import com.ginage.common.base.BaseResponse;
import com.ginage.product.dto.ProductDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "商品搜索接口")
public interface ProductSearchService {

	@ApiOperation(value = "商品搜索接口")
	@GetMapping("/search")
	public BaseResponse<List<ProductDto>> search(String name);
}
