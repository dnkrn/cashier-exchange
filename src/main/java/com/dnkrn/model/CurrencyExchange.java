package com.dnkrn.model;

import java.math.BigInteger;

/**
 * Model class which holds the currency quanity to be returned for each denomination
 */

public class CurrencyExchange {

    private BigInteger twentyDollars = BigInteger.ZERO;

    private BigInteger tenDollars = BigInteger.ZERO;

    private BigInteger fiveDollars = BigInteger.ZERO;

    private BigInteger twoDollars = BigInteger.ZERO;

    private BigInteger oneDollars = BigInteger.ZERO;


    public BigInteger getTwentyDollars() {
        return twentyDollars;
    }

    public void setTwentyDollars(BigInteger twentyDollars) {
        this.twentyDollars = twentyDollars;
    }

    public BigInteger getTenDollars() {
        return tenDollars;
    }

    public void setTenDollars(BigInteger tenDollars) {
        this.tenDollars = tenDollars;
    }

    public BigInteger getFiveDollars() {
        return fiveDollars;
    }

    public void setFiveDollars(BigInteger fiveDollars) {
        this.fiveDollars = fiveDollars;
    }

    public BigInteger getTwoDollars() {
        return twoDollars;
    }

    public void setTwoDollars(BigInteger twoDollars) {
        this.twoDollars = twoDollars;
    }

    public BigInteger getOneDollars() {
        return oneDollars;
    }

    public void setOneDollars(BigInteger oneDollars) {
        this.oneDollars = oneDollars;
    }

    /**
     * updates the quantity with the given quantity
     *
     * @param denomination
     * @param quantity
     */
    public void setQuantity(Denomination denomination, BigInteger quantity) {
        switch (denomination) {

            case TWENTY:
                twentyDollars = twentyDollars.add(quantity);
                break;

            case TEN:
                tenDollars = tenDollars.add(quantity);
                break;

            case FIVE:
                fiveDollars = fiveDollars.add(quantity);
                break;

            case TWO:
                twoDollars = twoDollars.add(quantity);
                break;

            case ONE:
                oneDollars = oneDollars.add(quantity);
                break;

            default:
                throw new UnsupportedOperationException();

        }
    }

    /**
     * returns the maximum denomination which has value > zero
     *
     * @return
     */
    public Denomination getMaxDenomination() {

        if (this.twentyDollars.compareTo(BigInteger.ZERO) > 0) {
            return Denomination.TWENTY;
        } else if (this.tenDollars.compareTo(BigInteger.ZERO) > 0) {
            return Denomination.TEN;
        } else if (this.fiveDollars.compareTo(BigInteger.ZERO) > 0) {
            return Denomination.FIVE;
        } else if (this.twoDollars.compareTo(BigInteger.ZERO) > 0) {
            return Denomination.TWO;
        } else if (this.oneDollars.compareTo(BigInteger.ZERO) > 0) {
            return Denomination.ONE;
        }
        return Denomination.UNKNOWN;


    }

    /**
     * method to return the quantity available for each currency
     * @param denomination
     * @return
     */
    public BigInteger getQuantity(Denomination denomination) {

        switch (denomination) {

            case TWENTY:
                return twentyDollars;

            case TEN:
                return tenDollars;

            case FIVE:
                return fiveDollars;

            case TWO:
                return twoDollars;

            case ONE:
                return oneDollars;

            default:
                return BigInteger.ZERO;

        }
    }

}
