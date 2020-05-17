
package com.ginage.goods.service.impl;

import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.web.bind.annotation.RestController;

import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.goods.es.entity.ProductEntity;
import com.ginage.goods.es.repository.ProductRepository;
import com.ginage.goods.service.ProductSearchService;
import com.ginage.product.dto.ProductDto;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * @date:2020年5月5日
 * @description:
 * @Copyright: ginage.com
 *
 */
@RestController
public class ProductSearchServiceImpl extends BaseApiService<List<ProductDto>> implements ProductSearchService {
	@Autowired
	private ProductRepository productRepository;

	@Override
	public BaseResponse<List<ProductDto>> search(String name) {
		//int i=3/0;
		// 1.拼接查询条件
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		// 2.模糊查询name字段
		builder.must(QueryBuilders.fuzzyQuery("name", name));
		Pageable pageable = new QPageRequest(0, 5);
		// 3.调用ES接口查询
		Page<ProductEntity> page = productRepository.search(builder, pageable);
		// 4.获取集合数据
		List<ProductEntity> content = page.getContent();
		// 5.将entity转换dto
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		List<ProductDto> mapAsList = mapperFactory.getMapperFacade().mapAsList(content, ProductDto.class);
		return setResultSuccess(mapAsList);
	}

}
