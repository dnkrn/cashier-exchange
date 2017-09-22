package com.dnkrn.dao;

import com.dnkrn.exception.ChangeNotFoundException;
import com.dnkrn.model.CurrencyExchange;
import com.dnkrn.model.CurrencyStock;
import com.dnkrn.model.Denomination;

import java.math.BigDecimal;
import java.math.BigInteger;
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
    public void initRegistryInfo(final Map<Denomination, BigInteger> values) {
        values.forEach((denomination, quantity) -> currencyStock.setQuantity(denomination, quantity));
    }

    /**
     * returns the total sum available in the cash registry
     *
     * @return
     */

    @Override
    public BigDecimal getTotalAvailableAmount() {

        return Denomination.TWENTY.getValue().multiply(new BigDecimal(currencyStock.getAvailableTwenty()))
                .add(Denomination.TEN.getValue().multiply(new BigDecimal(currencyStock.getAvailableTen())))
                .add(Denomination.FIVE.getValue().multiply(new BigDecimal(currencyStock.getAvailableFive())))
                .add(Denomination.TWO.getValue().multiply(new BigDecimal(currencyStock.getAvailableTwo())))
                .add(Denomination.ONE.getValue().multiply(new BigDecimal(currencyStock.getAvailableOne())));

    }

    /**
     * returns the number of quantity available for each denomination
     *
     * @param denomination
     * @return
     */

    @Override
    public BigInteger getAvailableCurrencyQuantity(Denomination denomination) {

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
                return BigInteger.ZERO;

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
    public boolean putInRegistry(Denomination denomination, BigInteger quantity) {

        if (BigInteger.ZERO.compareTo(quantity) >= 0) {
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
    public boolean takeFromRegistry(Denomination denomination, BigInteger quantity) {

        if (BigInteger.ZERO.compareTo(quantity) >= 0) {
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
    public CurrencyExchange exchangeMoney(BigDecimal exchangeAmount) throws ChangeNotFoundException {

        //Object which hold the returned denominations
        CurrencyExchange currencyExchange = new CurrencyExchange();

        //calculates the possible amount and returns the pending amount if denomination is not available
        BigDecimal pendingAmount = calculatePossibleCombos(currencyExchange, exchangeAmount, Denomination.TWENTY);

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

        /**
         * this should not be unknown initially
         * if none of the denominations are available use the max denomination from stock
         *
         */
        if (Denomination.UNKNOWN.equals(denomination)) {
            denomination = currencyStock.getMaximumAvailableDenomination();

            pendingAmount = pendingAmount.subtract(denomination.getValue());

        }

        while (pendingAmount.compareTo(BigDecimal.ZERO) > 0
                && !denomination.equals(Denomination.UNKNOWN)) {

            currencyExchange.setQuantity(denomination, BigInteger.valueOf(-1));

            pendingAmount = pendingAmount.add(denomination.getValue());

            denomination = denomination.nextValue(denomination);

            pendingAmount = calculatePossibleCombos(currencyExchange, pendingAmount, denomination);

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

    public BigDecimal calculatePossibleCombos(CurrencyExchange currencyExchange, BigDecimal exchangeAmount, Denomination denomination) {

        /**
         * return back when last denomination or the echange amount reaches to zero
         */

        if (denomination.equals(Denomination.UNKNOWN) || exchangeAmount.compareTo(BigDecimal.ZERO) <= 0) {
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
        BigDecimal[] divideAndRemainder = exchangeAmount.divideAndRemainder(denomination.getValue());
        BigInteger change = divideAndRemainder[0].toBigInteger();
        BigInteger pendingAmount = divideAndRemainder[1].toBigInteger();

        /**
         * checks for pending amount and if the currency is available in registry.
         * if not available recursively call the next denomination
         *
         *
         * when the available quantity is less than change calculated, use the availble quantity
         * add the pending amount by the difference of the change
         */
        BigInteger availableQuantity = getAvailableCurrencyQuantity(denomination);

        if (availableQuantity.compareTo(change) <= 0) {
            pendingAmount = pendingAmount.add(denomination.getValue().toBigInteger().multiply(change.subtract(availableQuantity)));
            change = availableQuantity;
        }
        if (exchangeAmount.compareTo(denomination.getValue()) >= 0 && availableQuantity.compareTo(BigInteger.ZERO) > 0) {
            currencyExchange.setQuantity(denomination, change);
            return calculatePossibleCombos(currencyExchange, new BigDecimal(pendingAmount), denomination.nextValue(denomination));

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
    private void updateRegistry(BigDecimal pendingAmount, CurrencyExchange currencyExchange) throws ChangeNotFoundException {
        if (pendingAmount.compareTo(BigDecimal.ZERO) > 0) {
            throw new ChangeNotFoundException("no change found");
        }
        for (Denomination denominationVal : Denomination.values()) {
            BigInteger quantity = currencyExchange.getQuantity(denominationVal);
            takeFromRegistry(denominationVal, quantity);
        }
    }
}
