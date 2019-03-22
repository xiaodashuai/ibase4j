package org.ibase4j.core.config;

public class BizContant {
	/**
	 * 1代表前台用户
	 * 2带包后台用户
	 */
	public static final String BIZUSER = "1";
	public static final String SYSUSER = "2";
	public static final String ADMINUSER = "3";
	
	/** 机构贸金部编码 **/
	public static final String DEPT_MJ_CODE = "1550000";
	/** 机构总行编码**/
	public static final String DEPT_ROOT_CODE = "1000000";
	/** 机构类型 **/
	public static final int DEPT_TYPE_CODE = 0;
	
	
	/** 币种(人民币) **/
	public static final String CURRENCY_CNY = "CNY";
	
	/** 金额类型(发放总金额) **/
	public static final String CBC_TYPE = "GRANSUM";
	
	
	/** 交易流水表(发放信息交易代码) **/
	public static final String TRN_TYPE_GRANT = "GRANT";
	
	
	/**
	 * 1代表前台岗位
	 * 2代表后台岗位
	 */
	public static final String BIZROLE = "1";
	public static final String SYSROLE = "2";
	
	/**0000代表担保类型根级菜单 */
	public static final String BIZ_GUARANTEE_TYPE_ROOT_CODE = "0000";
	/**0代表利率类型根级菜单 */
	public static final String BIZ_INTEREST_RATE_ROOT_CODE = "0";
	
	/**001 表示业务类型是抵押类型  */
	public static final String BIZ_BNS_GUARANTEE_TYPE = "001";
	/**002 表示业务类型是利率 */
	public static final String BIZ_BNS_INTEREST_RATE_TYPE = "002";
	/**004 表示业务类型是担保类型  */
	public static final String BIZ_BNS_INFO_TYPE = "004";
	/**005 表示业务类型是行业投向  */
	public static final String BIZ_BNS_ID_TYPE = "005";
	
	/**1代表产品种类根节点 */
	public static final int BIZ_PRODUCT_TYPE_ROOT_LEAF = 1;
	
	/**
	 * 担保类型是C104（其他）
	 */
	public static final String GUANTEE_TYPE_OTHER = "C104";
	
	/** 方案状态(6:审核完成) **/
	public static final int SOLUTION_STATE_06 = 6;
	
	/** 2等值其他币种 **/
	public static final String A_CURRENCY = "2";
	/** 0无其他币种 **/
	public static final String A_CURRENCY0 = "0";

	/** 是否可用1：是 ***/
	public static final int ENABLE_YES = 1;
	/** 是否可用0：否 ***/
	public static final int ENABLE_NO = 0;
	
	
	
	/** 发放申请业务名 ***/
	public static final String BIZ_TABLE_GRANT = "BIZ_DEBT_GRANT";
	
	public static final String BIZWEBTHEME = "BizWebTheme";
	public static final String BIZ_CURRENT_USER = "BIZ_CURRENT_USER";
	public static final String BIZ_CURRENT_USER_OBJ = "BIZ_CURRENT_USER_OBJ";

	/** 缓存命名空间 */
	public static final String CACHE_BIZ_NAMESPACE = "iBase4J:biz:";
	/** 在线用户数量 */
	public static final String ALLUSER_NUMBER = "SYSTEM:" + CACHE_BIZ_NAMESPACE + "ALLUSER_NUMBER";

	// 债项发放
	// 债项发放交易代码
	public static final String DEBT_GRANT_APPRL = "GRANT";
	// 债项发放业务表名称
	public static final String DEBT_GRANT_OBJTYP = "BIZ_DEBT_GRANT";
	// 债项发放主表
	public static final String DEBT_GRANT_MAIN = "BIZ_APPROVAL_WORKFLOW_TASK";
	// 债项发放审批交易名称
	public static final String DEBT_GRANT_NAME = "债项发放审批";
	// 租金保理业务种类
	public static final String RENTAL_FACTORING_BUSINESS_TYPES = "03";

	// 放款操作
	// 放款操作交易代码
	public static final String MAKE_LOANS_APPRL = "GRANT";
	// 放款操作主表
	public static final String MAKE_LOANS_MAIN = "BIZ_MAKE_LOANS";
	// 放款操作交易名称
	public static final String MAKE_LOANS_NAME = "放款操作";

	// 方案操作主表
	public static final String BIZ_DEBT_MAIN = "BIZ_DEBT_MAIN";
	// 债项方案交易代码
	public static final String DEBT_MAIN_APPRL = "ADEBT";
	// 债项发放审批交易名称
	public static final String DEBT_MAIN_NAME = "债项方案审批";

	// CBTTXT 业务类型
	public static final String MAKE_LOANS_IN_CBTTXT = "LOANIN";
	public static final String MAKE_LOANS_OUT_CBTTXT = "LOANOUT";
	public static final String DEBT_GRANT_IN_CBTTXT = "GRANIN";
	public static final String DEBT_GRANT_OUT_CBTTXT = "GRANOUT";
	public static final String DEBT_SUMMARY_IN_CBTTXT = "SOLUIN";
	public static final String DEBT_SUMMARY_OUT_CBTTXT = "AKZOUT";
	// CBCTXT 金额类型
	public static final String MAKE_LOANS_CBCTXT = "LOANSUM";
	public static final String DEBT_GRANT_CBCTXT = "GRANSUM";
	public static final String DEBT_SUMMARY_CBCTXT = "SOLUSUM";
	public static final String OVEDUSUM_CBCTXT = "OVEDUSUM";
	public static final String INATESUM_CBCTXT = "INATESUM";
	public static final String OUATESUM_CBCTXT = "OUATESUM";

	//债项方案审批流程
	/** 待审核 */
	public static final int DEBT_PROCESS_1 = 1;
	/** 已审批 */
	public static final int DEBT_PROCESS_2 = 1;
	/** 被驳回 */
	public static final int DEBT_PROCESS_3 = 1;
	/** 退卷 */
	public static final int DEBT_PROCESS_4 = 1;
	/** 发联系单 */
	public static final int DEBT_PROCESS_5 = 1;
	/** 补充材料 */
	public static final int DEBT_PROCESS_6 = 1;

	//债项发放审批流程
	/** 待审核 */
	public static final int GRANT_PROCESS_1 = 1;
	/** 已审批 */
	public static final int GRANT_PROCESS_2 = 1;
	/** 被驳回 */
	public static final int GRANT_PROCESS_3 = 1;


	// 结束日期
	public static final String END_DATE = "2299-12-31 23:59:59";

	/** 方案状态 2:冻结; ***/
	public static final Integer DEBT_STATE_2 = 2;
	/** 方案状态 3:已提交; ***/
	public static final Integer IN_REVISION = 3;
	/** 方案状态 4:待提交; ***/
	public static final Integer PENDE_SUNBMIT = 4;
	/** 方案状态 5:审核中; ***/
	public static final Integer IN_REVIEW = 5;
	/** 方案状态 6:可发放; ***/
	public static final Integer CAN_BE_ISSUED = 6;
	/** 方案状态 7:失效; ***/
	public static final Integer FAILURE = 7;
	/** 方案状态 8:已修订; ***/
	public static final Integer DEBT_STATE_8 = 8;
	/** 方案状态 9:已驳回; ***/
	public static final Integer DEBT_STATE_9 = 9;

	/** 发放状态 1:已提交; ***/
	public static final int GRANT_STATE_1 = 1;
	/** 发放状态 2审核中;***/
	public static final int GRANT_STATE_2 = 2;
	/** 发放状态 3已批未放; ***/
	public static final int GRANT_STATE_3 = 3;
	/** 发放状态 4发放中 ***/
	public static final int GRANT_STATE_4 = 4;
	/** 发放状态 5已发放 ***/
	public static final int GRANT_STATE_5 = 5;
	/** 发放状态 6已废弃 ***/
	public static final int GRANT_STATE_6 = 6;
	/** 发放状态 7废弃审核中  ***/
	public static final int GRANT_STATE_7 = 7;
	/** 发放状态 8变更审核中***/
	public static final int GRANT_STATE_8 = 8;
	/** 发放状态 9已变更***/
	public static final int GRANT_STATE_9 = 9;
	/** 发放状态 10已驳回***/
	public static final int GRANT_STATE_10 = 10;
	/** 发放状态 11冻结***/
	public static final int GRANT_STATE_11 = 11;

	// 接口名称 核心64002贷款合同查询接口
	public static final String INTERFACE_NAME_ONE = "dkblhtztcx";
	// 接口名称 核心64005借据许可证接口 创建
	public static final String INTERFACE_NAME_TWO = "jjxkzff";
	// 接口名称 信贷额度占用接口
	public static final String INTERFACE_NAME_THREE = "edjyjk";
	// 接口名称 信贷业务与担保合同关系接口
	public static final String INTERFACE_NAME_FOUR = "ywydbhtgx";
	// 接口名称 核心64005借据许可证接口 查询
	public static final String INTERFACE_NAME_FIVE = "jjxkzffQ";
	// 接口名称 核心还款计划接口
	public static final String INTERFACE_NAME_SIX = "bgzhkjhfs";
	// 接口名称 信贷额度释放接口
	public static final String INTERFACE_NAME_SEVEN = "edsf";
	// 接口名称 信贷业务与担保合同关系解除接口
	public static final String INTERFACE_NAME_EIGHT = "ywydbhtgxjc";

	// 接口返回结果
	public static final String INTERFACE_RESULT_SUCCESS = "-1";

}
