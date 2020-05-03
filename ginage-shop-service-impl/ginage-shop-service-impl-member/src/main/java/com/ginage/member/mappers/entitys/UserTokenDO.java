
package com.ginage.member.mappers.entitys;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
*@date:2020年4月5日
*@description:
*@Copyright: ginage.com
*
*/
@Data
public class UserTokenDO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String token;
	private String loginType;
	private String deviceInfo;
	private int is_available;
	private Long user_id;
	private Date create_time;
	private Date update_time;
	private String login_ip;
}
