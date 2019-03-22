package org.ibase4j.core.config;

public class BizStatus {

    /************方案相关***************/

    /**已驳回***/
    public static final int DEBTDOWN = 1;

    /**冻结（不展示）***/
    public static final int DEBTFROZ = 2;

    /**补录审核中***/
    public static final int DEBTSUAT = 3;

    /**暂存***/
    public static final int DEBTTEST = 4;

    /**修订审核中***/
    public static final int DEBTREAT = 5;

    /**可发放***/
    public static final int DEBTAVAI = 6;

    /**失效（不展示）***/
    public static final int DEBTINVA = 7;

    /**删除（不展示）***/
    public static final int DEBTDELE = 8;

    /**合同创建失败***/
    public static final int DEBTCOCF = 9;

    /**方案过期***/
    public static final int DEBTREGULAR = 10;

    /**********发放条件相关*************/

    /**已变更***/
    public static final int GRANCHAN = 1;

    /**新建审核中***/
    public static final int GRANNEAT = 2;

    /**已批未放***/
    public static final int GRANUNPU = 3;

    /**发放中***/
    public static final int GRANINTD = 4;

    /**已发放***/
    public static final int GRANALIS = 5;

    /**已废弃***/
    public static final int GRANOBSO = 6;

    /**废弃审核中***/
    public static final int GRANABAT = 7;

    /**变更审核中***/
    public static final int GRANCHAT = 8;

    /**失效（不展示）***/
    public static final int GRANINVA = 9;

    /**已驳回***/
    public static final int GRANDOWN = 10;

    /**冻结（不展示）***/
    public static final int GRANFROZ = 11;

    /**删除（不展示）***/
    public static final int GRANDELE = 12;


    /************方案流程相关***************/
    /** 待审核 */
    public static final int DEPRNEAT = 1;
    /** 已审批 */
    public static final int DEPRAPPR = 2;
    /** 被驳回 */
    public static final int DEPRDOWN = 3;
    /** 退卷 */
    public static final int DEPRREWI = 4;
    /** 发联系单 */
    public static final int DEPRCOSH = 5;
    /** 补充材料 */
    public static final int DEPRSUMA = 6;

    /************发放流程相关***************/
    /** 待审核 */
    public static final int GRPRNEAT = 1;
    /** 已审批 */
    public static final int GRPRAPPR = 2;
    /** 被驳回 */
    public static final int GRPRDOWN = 3;
}
