package com.ai.runner.center.omc.virtualdeduct.utils;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
    protected Object determineCurrentLookupKey() {
        // TODO Auto-generated method stub
        return CustomerContextHolder.getCustomerType();
    }
}
