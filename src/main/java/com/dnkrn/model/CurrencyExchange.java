package com.dnkrn.model;


/**
 * Model class which holds the currency quanity to be returned for each denomination
 */

public class CurrencyExchange {

    private int twentyDollars = 0;

    private int tenDollars = 0;

    private int fiveDollars = 0;

    private int twoDollars = 0;

    private int oneDollars = 0;


    public int getTwentyDollars() {
        return twentyDollars;
    }

    public void setTwentyDollars(int twentyDollars) {
        this.twentyDollars = twentyDollars;
    }

    public int getTenDollars() {
        return tenDollars;
    }

    public void setTenDollars(int tenDollars) {
        this.tenDollars = tenDollars;
    }

    public int getFiveDollars() {
        return fiveDollars;
    }

    public void setFiveDollars(int fiveDollars) {
        this.fiveDollars = fiveDollars;
    }

    public int getTwoDollars() {
        return twoDollars;
    }

    public void setTwoDollars(int twoDollars) {
        this.twoDollars = twoDollars;
    }

    public int getOneDollars() {
        return oneDollars;
    }

    public void setOneDollars(int oneDollars) {
        this.oneDollars = oneDollars;
    }

    /**
     * updates the quantity with the given quantity
     *
     * @param denomination
     * @param quantity
     */
    public void setQuantity(Denomination denomination, int quantity) {
        switch (denomination) {

            case TWENTY:
                twentyDollars = twentyDollars+ quantity;
                break;

            case TEN:
                tenDollars = tenDollars+ quantity;
                break;

            case FIVE:
                fiveDollars = fiveDollars+ quantity;
                break;

            case TWO:
                twoDollars = twoDollars+ quantity;
                break;

            case ONE:
                oneDollars = oneDollars+ quantity;
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

        if (this.twentyDollars > 0) {
            return Denomination.TWENTY;
        } else if (this.tenDollars > 0) {
            return Denomination.TEN;
        } else if (this.fiveDollars > 0) {
            return Denomination.FIVE;
        } else if (this.twoDollars > 0) {
            return Denomination.TWO;
        } else if (this.oneDollars > 0) {
            return Denomination.ONE;
        }
        return Denomination.UNKNOWN;


    }

    /**
     * method to return the quantity available for each currency
     * @param denomination
     * @return
     */
    public int getQuantity(Denomination denomination) {

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
                return 0;

        }
    }

}
