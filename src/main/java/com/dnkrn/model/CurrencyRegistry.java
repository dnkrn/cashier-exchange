package com.dnkrn.model;

import java.math.BigInteger;


public class CurrencyRegistry {

    private BigInteger availableTwenty;

    private BigInteger availableTen;

    private BigInteger availableFive;

    private BigInteger availableTwo;

    private BigInteger availableOne;


    public BigInteger getAvailableTwenty() {
        return availableTwenty;
    }

    public void setAvailableTwenty(BigInteger availableTwenty) {
        this.availableTwenty = availableTwenty;
    }

    public BigInteger getAvailableTen() {
        return availableTen;
    }

    public void setAvailableTen(BigInteger availableTen) {
        this.availableTen = availableTen;
    }

    public BigInteger getAvailableFive() {
        return availableFive;
    }

    public void setAvailableFive(BigInteger availableFive) {
        this.availableFive = availableFive;
    }

    public BigInteger getAvailableTwo() {
        return availableTwo;
    }

    public void setAvailableTwo(BigInteger availableTwo) {
        this.availableTwo = availableTwo;
    }

    public BigInteger getAvailableOne() {
        return availableOne;
    }

    public void setAvailableOne(BigInteger availableOne) {
        this.availableOne = availableOne;
    }


    public boolean addQuantity(Denomination denomination, BigInteger quantity) {
        switch (denomination) {

            case TWENTY:
                availableTwenty=availableTwenty.add(quantity);
                break;

            case TEN:
                availableTen=availableTen.add(quantity);
                break;

            case FIVE:
                availableFive=availableFive.add(quantity);
                break;

            case TWO:
                availableTwo=availableTwo.add(quantity);
                break;

            case ONE:
                availableOne=availableOne.add(quantity);
                break;

            default:
                throw new UnsupportedOperationException();

        }

        return true;
    }

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
}
