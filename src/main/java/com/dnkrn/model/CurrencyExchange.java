package com.dnkrn.model;

import java.math.BigInteger;

/**
 * Created by dinakaran on 9/18/17.
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
}
