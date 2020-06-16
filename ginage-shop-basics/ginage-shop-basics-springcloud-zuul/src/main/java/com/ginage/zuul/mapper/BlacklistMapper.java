package com.ginage.zuul.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ginage.zuul.mapper.entity.IPBlacklist;

@Mapper
public interface BlacklistMapper {

	IPBlacklist findBlacklist(String ipAddres);

}
