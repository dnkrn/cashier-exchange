package com.dnkrn.model;


/**
 * Class that holds the currency registry state
 */

public class CurrencyStock {

    private int availableTwenty = 0;

    private int availableTen = 0;

    private int availableFive = 0;

    private int availableTwo = 0;

    private int availableOne= 0;


    public int getAvailableTwenty() {
        return availableTwenty;
    }


    public int getAvailableTen() {
        return availableTen;
    }


    public int getAvailableFive() {
        return availableFive;
    }


    public int getAvailableTwo() {
        return availableTwo;
    }


    public int getAvailableOne() {
        return availableOne;
    }


    /**
     * Method to add the currency to the registry
     *
     * @param denomination
     * @param quantity
     * @return
     */
    public boolean addQuantity(Denomination denomination, int quantity) {

        switch (denomination) {

            case TWENTY:
                availableTwenty = availableTwenty+quantity;
                break;

            case TEN:
                availableTen = availableTen+quantity;
                break;

            case FIVE:
                availableFive = availableFive+quantity;
                break;

            case TWO:
                availableTwo = availableTwo+quantity;
                break;

            case ONE:
                availableOne = availableOne+quantity;
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
    public boolean subtractQuantity(Denomination denomination, int quantity) {


        switch (denomination) {

            case TWENTY:
                if (availableTwenty-quantity >= 0) {
                    availableTwenty = availableTwenty-quantity;
                    return true;
                }
                break;
            case TEN:
                if (availableTen-quantity >= 0) {
                    availableTen = availableTen-quantity;
                    return true;
                }
                break;
            case FIVE:
                if (availableFive-quantity >= 0) {
                    availableFive = availableFive-quantity;
                    return true;
                }
                break;
            case TWO:
                if (availableTwo-quantity >= 0) {
                    availableTwo = availableTwo-quantity;
                    return true;
                }
                break;
            case ONE:
                if (availableOne-quantity >= 0) {
                    availableOne = availableOne-quantity;
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
    public void setQuantity(Denomination denomination, int quantity) {
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