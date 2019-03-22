/**
 * 
 */
package org.ibase4j.core.base;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ShenHuaJie
 *
 * @version 2016年6月3日 下午2:30:14
 *
 */
public interface BaseMapper<T extends BaseModel> extends com.baomidou.mybatisplus.mapper.BaseMapper<T> {

	List<Long> selectIdPage(@Param("cm") T params);

	List<Long> selectIdPage(@Param("cm") Map<String, Object> params);

	List<Long> selectIdPage(RowBounds rowBounds, @Param("cm") Map<String, Object> params);

	List<Long> selectIdPage(RowBounds rowBounds, @Param("cm") T params);

	List<T> selectPage(RowBounds rowBounds, @Param("cm") Map<String, Object> params);

	Integer selectCount(@Param("cm") Map<String, Object> params);


	List<Map<String,Object>> selectResultPage(RowBounds rowBounds,@Param("cm") Map<String, Object> params);


}
