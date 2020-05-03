
package com.ginage.common.base;

import lombok.Data;

/**
 * @date:2020年4月2日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Data
public class BaseResponse<T> {

	private Integer code;
	private String msg;
	private T data;

	public BaseResponse() {
	}

	public BaseResponse(Integer code, String msg, T data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

}
