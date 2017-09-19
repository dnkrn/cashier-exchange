package com.dnkrn.model;

import java.math.BigDecimal;

/**
 * Created by dinakaran on 9/18/17.
 */
public enum Denomination {

    /**
     * Available Denominations 20,10,5,2,1
     */

    TWENTY(BigDecimal.valueOf(20.0)), TEN(BigDecimal.valueOf(10.0)),
    FIVE(BigDecimal.valueOf(5.0)), TWO(BigDecimal.valueOf(2.0)), ONE(BigDecimal.valueOf(1.0));


    private BigDecimal denominationVal;


    Denomination(BigDecimal denominationVal) {
        this.denominationVal = denominationVal;
    }


    public BigDecimal getValue() {
        return denominationVal;
    }


}
