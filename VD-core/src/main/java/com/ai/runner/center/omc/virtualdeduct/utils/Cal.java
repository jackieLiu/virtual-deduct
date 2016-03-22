package com.ai.runner.center.omc.virtualdeduct.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.ai.runner.center.omc.virtualdeduct.constant.FEESOURCE;

public class Cal {
	public static final BigDecimal BigDecimalFromDouble(Double value,String resourcetype){
		BigDecimal bigDecimal = BigDecimal.valueOf(value);

		if (resourcetype.equals(FEESOURCE.FROMCHARGE)){
			BigDecimal divisor = new BigDecimal(1000000);
			return bigDecimal.divide(divisor,2, RoundingMode.HALF_UP);

		} else if(resourcetype.equals(FEESOURCE.FROMBALANCE)){
			BigDecimal divisor = new BigDecimal(1000);
			return bigDecimal.divide(divisor,2, RoundingMode.HALF_UP);
		}else if(resourcetype.equals(FEESOURCE.FROMCREDIT)){
			BigDecimal divisor = new BigDecimal(100);
			return bigDecimal.divide(divisor,2, RoundingMode.HALF_UP);
		}
		return bigDecimal;
	}
	
	public static final BigDecimal BigDecimalFromDoubleStr(String value,String resourcetype){
		Double doublevalue = Double.parseDouble(value);
		return BigDecimalFromDouble(doublevalue,resourcetype);
	}
	
	public static final BigDecimal BigDecimalFromLong(Long value,String resourcetype){
		Double doublevalue = Double.parseDouble(Long.toString(value));
		return BigDecimalFromDouble(doublevalue,resourcetype);
	}
}
