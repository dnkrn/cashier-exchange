package com.dnkrn.model;

import java.math.BigDecimal;

/**
 * Enum which holds the currency value
 **/
public enum Denomination {

    /**
     * Available Denominations 20,10,5,2,1
     */

    TWENTY(BigDecimal.valueOf(20.0)),
    TEN(BigDecimal.valueOf(10.0)) ,
    FIVE(BigDecimal.valueOf(5.0)),
    TWO(BigDecimal.valueOf(2.0)),
    ONE(BigDecimal.valueOf(1.0)),
    UNKNOWN(BigDecimal.valueOf(0.0));


    private BigDecimal denominationVal;


    Denomination(BigDecimal denominationVal) {
        this.denominationVal = denominationVal;
    }


    public BigDecimal getValue() {
        return denominationVal;
    }


    public Denomination nextValue(Denomination denomination) {
        switch (denomination) {
            case TWENTY:
                return TEN;
            case TEN:
                return FIVE;
            case FIVE:
                return TWO;
            case TWO:
                return ONE;
            case ONE:
                return UNKNOWN;
            default:
                return UNKNOWN;
        }
    }
}
