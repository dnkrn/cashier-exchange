package com.dnkrn.model;

import java.math.BigInteger;

/**
 * Class that holds the currency registry state
 */

public class CurrencyStock {

    private BigInteger availableTwenty = BigInteger.ZERO;

    private BigInteger availableTen = BigInteger.ZERO;

    private BigInteger availableFive = BigInteger.ZERO;

    private BigInteger availableTwo = BigInteger.ZERO;

    private BigInteger availableOne = BigInteger.ZERO;


    public BigInteger getAvailableTwenty() {
        return availableTwenty;
    }


    public BigInteger getAvailableTen() {
        return availableTen;
    }


    public BigInteger getAvailableFive() {
        return availableFive;
    }


    public BigInteger getAvailableTwo() {
        return availableTwo;
    }


    public BigInteger getAvailableOne() {
        return availableOne;
    }


    /**
     * Method to add the currency to the registry
     *
     * @param denomination
     * @param quantity
     * @return
     */
    public boolean addQuantity(Denomination denomination, BigInteger quantity) {
        switch (denomination) {

            case TWENTY:
                availableTwenty = availableTwenty.add(quantity);
                break;

            case TEN:
                availableTen = availableTen.add(quantity);
                break;

            case FIVE:
                availableFive = availableFive.add(quantity);
                break;

            case TWO:
                availableTwo = availableTwo.add(quantity);
                break;

            case ONE:
                availableOne = availableOne.add(quantity);
                break;

            default:
                throw new UnsupportedOperationException();

        }

        return true;
    }


    /**
     * Method to remove the value from registry
     *
     * @param denomination
     * @param quantity
     * @return
     */
    public boolean subtractQuantity(Denomination denomination, BigInteger quantity) {
        switch (denomination) {

            case TWENTY:
                if (availableTwenty.subtract(quantity).compareTo(BigInteger.ZERO) >= 0) {
                    availableTwenty = availableTwenty.subtract(quantity);
                    return true;
                }
                break;
            case TEN:
                if (availableTen.subtract(quantity).compareTo(BigInteger.ZERO) >= 0) {
                    availableTen = availableTen.subtract(quantity);
                    return true;
                }
                break;
            case FIVE:
                if (availableFive.subtract(quantity).compareTo(BigInteger.ZERO) >= 0) {
                    availableFive = availableFive.subtract(quantity);
                    return true;
                }
                break;
            case TWO:
                if (availableTwo.subtract(quantity).compareTo(BigInteger.ZERO) >= 0) {
                    availableTwo = availableTwo.subtract(quantity);
                    return true;
                }
                break;
            case ONE:
                if (availableOne.subtract(quantity).compareTo(BigInteger.ZERO) >= 0) {
                    availableOne = availableOne.subtract(quantity);
                    return true;
                }
                break;
            default:
                throw new UnsupportedOperationException();

        }

        return false;
    }


    /**
     * Method to add the currency to the registry
     *
     * @param denomination
     * @param quantity
     * @return
     */
    public void setQuantity(Denomination denomination, BigInteger quantity) {
        switch (denomination) {

            case TWENTY:
                availableTwenty = quantity;
                break;

            case TEN:
                availableTen = quantity;
                break;

            case FIVE:
                availableFive = quantity;
                break;

            case TWO:
                availableTwo = quantity;
                break;

            case ONE:
                availableOne = quantity;
                break;

            default:
                throw new UnsupportedOperationException();

        }
    }
}