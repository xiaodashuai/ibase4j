package org.ibase4j.provider;


import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizCust;

public interface SysCustProvider extends BaseProvider<BizCust> {

    void init();
}
