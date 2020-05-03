
package com.ginage.member.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ginage.member.mappers.entitys.UserDO;
import com.ginage.member.mappers.entitys.UserTokenDO;
import com.ginage.output.dto.UserLoginDTO;
import com.ginage.output.dto.UserOutputDTO;

/**
 * @date:2020年4月4日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Mapper
public interface UserMapper {

	int register(UserDO userEntity);

	UserDO existMobile(String phoneNumber);

	UserDO login(UserLoginDTO userLoginDTO);

	UserTokenDO isLogined(@Param("user_id") Long user_id, @Param("loginType") String loginType);

	int setTokenAvailable(@Param("id") Long id, @Param("status") int status);

	int addUserToken(UserTokenDO newUserTokenDO);

	UserTokenDO checkUserToken(String token);

	UserOutputDTO getUserInfoById(Long user_id);

	UserDO checkQQOpenID(@Param("qq_openid") String qq_openid);

	int addQQOpenID(@Param("user_id") Long user_id, @Param("qqOpenID")String qqOpenID);
	
}
