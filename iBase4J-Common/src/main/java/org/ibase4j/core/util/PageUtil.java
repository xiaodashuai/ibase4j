package org.ibase4j.core.util;

import com.baomidou.mybatisplus.plugins.Page;

import java.util.Map;

public final class PageUtil {

    private PageUtil() {
    }

    /**
     * 分页查询参数封装
     */
    @SuppressWarnings({"unchecked"})
    public static Page<Long> getPage(Map<String, Object> params) {
        Integer current = 1;
        Integer size = 10;
        String orderBy = "id_";
        if (DataUtil.isNotEmpty(params.get("pageNum"))) {
            current = Integer.valueOf(params.get("pageNum").toString());
        }
        if (DataUtil.isNotEmpty(params.get("pageIndex"))) {
            current = Integer.valueOf(params.get("pageIndex").toString());
        }
        if (DataUtil.isNotEmpty(params.get("pageSize"))) {
            size = Integer.valueOf(params.get("pageSize").toString());
        }
        if (DataUtil.isNotEmpty(params.get("orderBy"))) {
            orderBy = (String) params.get("orderBy");
            params.remove("orderBy");
        }
        if (size == -1) {
            Page<Long> page = new Page<Long>();
            page.setOrderByField(orderBy);
            page.setAsc(false);
            return page;
        }
        Page<Long> page = new Page<Long>(current, size, orderBy);
        page.setAsc(false);

        return page;
    }
}
