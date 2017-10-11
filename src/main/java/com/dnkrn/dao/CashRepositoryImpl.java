package com.dnkrn.dao;

import com.dnkrn.exception.ChangeNotFoundException;
import com.dnkrn.model.CurrencyExchange;
import com.dnkrn.model.CurrencyStock;
import com.dnkrn.model.Denomination;

import java.util.Map;

/**
 * DAO Implementation
 */

public class CashRepositoryImpl implements CashRepository {


    private CurrencyStock currencyStock;


    public CashRepositoryImpl() {
        currencyStock = new CurrencyStock();
    }

    /**
     * initializes the registry with the given input
     *
     * @param values
     */

    @Override
    public void initRegistryInfo(final Map<Denomination, Integer> values) {
        values.forEach((denomination, quantity) -> currencyStock.setQuantity(denomination, quantity));
    }

    /**
     * returns the total sum available in the cash registry
     *
     * @return
     */

    @Override
    public double getTotalAvailableAmount() {

        return Denomination.TWENTY.getValue() * currencyStock.getAvailableTwenty()
                + Denomination.TEN.getValue() * currencyStock.getAvailableTen()
                + Denomination.FIVE.getValue()* currencyStock.getAvailableFive()
                + Denomination.TWO.getValue() * currencyStock.getAvailableTwo()
                + Denomination.ONE.getValue() * currencyStock.getAvailableOne();

    }

    /**
     * returns the number of quantity available for each denomination
     *
     * @param denomination
     * @return
     */

    @Override
    public int getAvailableCurrencyQuantity(Denomination denomination) {

        switch (denomination) {

            case TWENTY:
                return currencyStock.getAvailableTwenty();

            case TEN:
                return currencyStock.getAvailableTen();

            case FIVE:
                return currencyStock.getAvailableFive();

            case TWO:
                return currencyStock.getAvailableTwo();

            case ONE:
                return currencyStock.getAvailableOne();

            default:
                return 0;

        }
    }


    /**
     * returns true if the put operation is success
     * adds the given currency into registry
     *
     * @param denomination
     * @param quantity
     * @return
     */

    @Override
    public boolean putInRegistry(Denomination denomination, int quantity) {

        if (quantity <= 0) {
            return false;
        }
        return currencyStock.addQuantity(denomination, quantity);
    }


    /**
     * returns true if the take operations is success
     * removes the fiven currency from registry
     *
     * @param denomination
     * @param quantity
     * @return
     */
    @Override
    public boolean takeFromRegistry(Denomination denomination, int quantity) {

        if (quantity <= 0) {
            return false;
        }
        return currencyStock.subtractQuantity(denomination, quantity);
    }


    /**
     * returns the currency change for each Currency given
     *
     * @param exchangeAmount
     * @return
     * @throws ChangeNotFoundException
     */
    @Override
    public CurrencyExchange exchangeMoney(double exchangeAmount) throws ChangeNotFoundException {

        //Object which hold the returned denominations
        CurrencyExchange currencyExchange = new CurrencyExchange();

        //calculates the possible amount and returns the pending amount if denomination is not available
        double pendingAmount = calculatePossibleCombos(currencyExchange, exchangeAmount, Denomination.TWENTY);

        /**
         * if there is no pending amount the exchanges are returned
         * when there is pending amount the following happens
         * 1.The logic is to get the max denomination and decrement it by 1
         * 2.pending amount is updated
         * 3.Combo from next denomination is calculated
         * 4.this continues until the last know currency
         *
         */


        Denomination denomination = currencyExchange.getMaxDenomination();



        while (pendingAmount > 0
                && !denomination.equals(Denomination.UNKNOWN)) {

            currencyExchange.setQuantity(denomination, -1);


            pendingAmount = pendingAmount + denomination.getValue();

            //get the next denomination value
            denomination = denomination.nextValue(denomination);
            //calculate the next possible combos
            pendingAmount = calculatePossibleCombos(currencyExchange, pendingAmount, denomination);

            //get the next max Denomination
            denomination = currencyExchange.getMaxDenomination();

        }

        updateRegistry(pendingAmount, currencyExchange);

        return currencyExchange;
    }

    /**
     * Calculates the possible combinations
     *
     * @param currencyExchange
     * @param exchangeAmount
     * @param denomination
     * @return
     */

    public double calculatePossibleCombos(CurrencyExchange currencyExchange, double exchangeAmount, Denomination denomination) {

        /**
         * return back when last denomination or the echange amount reaches to zero
         */

        if (denomination.equals(Denomination.UNKNOWN) || exchangeAmount <= 0) {
            return exchangeAmount;
        }

        /**
         * divide and remainder by the denomination will give the change and the remaining amount
         *
         * e.g amount = 25$
         *
         * after 20 -> change =1 pending =5$
         *
         */
        int change = (int)(exchangeAmount / denomination.getValue());
        double pendingAmount = exchangeAmount % denomination.getValue();

        /**
         * checks for pending amount and if the currency is available in registry.
         * if not available recursively call the next denomination
         *
         *
         * when the available quantity is less than change calculated, use the availble quantity
         * add the pending amount by the difference of the change
         */


        int availableQuantity = getAvailableCurrencyQuantity(denomination);

        if (availableQuantity-change < 0) {
            pendingAmount = pendingAmount + (denomination.getValue() * (change - availableQuantity));
            change = availableQuantity;
        }
        if (exchangeAmount - denomination.getValue() >= 0 && availableQuantity > 0) {

            currencyExchange.setQuantity(denomination, change);
            return calculatePossibleCombos(currencyExchange, pendingAmount, denomination.nextValue(denomination));
        }
        return calculatePossibleCombos(currencyExchange, exchangeAmount, denomination.nextValue(denomination));

    }

    /**
     * updates the currency stock
     *
     * @param pendingAmount
     * @param currencyExchange
     * @throws ChangeNotFoundException
     */
    private void updateRegistry(double pendingAmount, CurrencyExchange currencyExchange) throws ChangeNotFoundException {
        if (pendingAmount > 0) {
            throw new ChangeNotFoundException("no change found");
        }
        for (Denomination denominationVal : Denomination.values()) {
            int quantity = currencyExchange.getQuantity(denominationVal);
            takeFromRegistry(denominationVal, quantity);
        }
    }
}
