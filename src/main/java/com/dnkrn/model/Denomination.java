package com.dnkrn.model;

/**
 * Enum which holds the currency value
 **/
public enum Denomination {

    /**
     * Available Denominations 20,10,5,2,1
     */

    TWENTY(20.0),
    TEN(10.0),
    FIVE(5.0),
    TWO(2.0),
    ONE(1.0),
    UNKNOWN(0.0);


    private double denominationVal;


    Denomination(double denominationVal) {
        this.denominationVal = denominationVal;
    }


    public double getValue() {
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
