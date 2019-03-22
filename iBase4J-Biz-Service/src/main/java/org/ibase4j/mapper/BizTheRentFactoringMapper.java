package org.ibase4j.mapper;

import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizTheRentFactoring;

import java.util.List;

public interface BizTheRentFactoringMapper extends BaseMapper<BizTheRentFactoring> {

    List queryByDebtCode(String debtCode);

}
