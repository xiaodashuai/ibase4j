package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**
 * 描述：检查计划配置
 * 
 * @author lwh
 * @date 2018/10/10
 */

@TableName("BIZ_CBS_ERROR_MESSAGE")
@SuppressWarnings("serial")
public class BizCbsErrorMessage extends BaseModel implements Serializable {
	/**
	 * 错误编码
	 */
	@TableField("ERROR_CODE")
	private String errorCode;
	/**
	 * 错误信息
	 */
	@TableField("ERROE_MESSAGE")
	private String errorMessage;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BizCbsErrorMessage() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("errorCode", errorCode)
				.add("errorMessage", errorMessage)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizCbsErrorMessage)) return false;
		BizCbsErrorMessage that = (BizCbsErrorMessage) o;
		return Objects.equal(getErrorCode(), that.getErrorCode()) &&
				Objects.equal(getErrorMessage(), that.getErrorMessage());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getErrorCode(), getErrorMessage());
	}
}
