package com.ginage.goods.es.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ginage.goods.es.entity.ProductEntity;

public interface ProductRepository extends ElasticsearchRepository<ProductEntity, Long> {

}
