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

    @Override
    public void initRegistryInfo(final Map<Denomination, BigInteger> values) {
        values.forEach((denomination, quantity) -> currencyStock.setQuantity(denomination, quantity));
    }


    @Override
    public BigDecimal getTotalAvailableAmount() {

        return Denomination.TWENTY.getValue().multiply(new BigDecimal(currencyStock.getAvailableTwenty()))
                .add(Denomination.TEN.getValue().multiply(new BigDecimal(currencyStock.getAvailableTen())))
                .add(Denomination.FIVE.getValue().multiply(new BigDecimal(currencyStock.getAvailableFive())))
                .add(Denomination.TWO.getValue().multiply(new BigDecimal(currencyStock.getAvailableTwo())))
                .add(Denomination.ONE.getValue().multiply(new BigDecimal(currencyStock.getAvailableOne())));

    }


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


    @Override
    public boolean putInRegistry(Denomination denomination, BigInteger quantity) {

        if (BigInteger.ZERO.compareTo(quantity) >= 0) {
            return false;
        }
        return currencyStock.addQuantity(denomination, quantity);
    }


    @Override
    public boolean takeFromRegistry(Denomination denomination, BigInteger quantity) {

        if (BigInteger.ZERO.compareTo(quantity) >= 0) {
            return false;
        }
        return currencyStock.subtractQuantity(denomination, quantity);
    }


    @Override
    public CurrencyExchange exchangeMoney(BigDecimal exchangeAmount) throws ChangeNotFoundException {

        CurrencyExchange currencyExchange = new CurrencyExchange();

        BigDecimal pendingAmount = calculatePossibleCombos(currencyExchange, exchangeAmount, Denomination.TWENTY);

        Denomination denomination = currencyExchange.getMaxDenomination();

        while (pendingAmount.compareTo(BigDecimal.ZERO) > 0
                && !denomination.equals(Denomination.UNKNOWN)) {

            currencyExchange.setQuantity(denomination, BigInteger.valueOf(-1));

            pendingAmount = pendingAmount.add(denomination.getValue());

            denomination = denomination.nextValue(denomination);

            pendingAmount = calculatePossibleCombos(currencyExchange, pendingAmount, denomination);

        }

        updateRegistry(pendingAmount,currencyExchange);

        return currencyExchange;
    }


    public BigDecimal calculatePossibleCombos(CurrencyExchange currencyExchange, BigDecimal exchangeAmount, Denomination denomination) {

        if (denomination.equals(Denomination.UNKNOWN) || exchangeAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return exchangeAmount;
        }

        BigDecimal[] divideAndRemainder = exchangeAmount.divideAndRemainder(denomination.getValue());
        BigInteger change = divideAndRemainder[0].toBigInteger();
        BigInteger pendingAmount = divideAndRemainder[1].toBigInteger();

        if (exchangeAmount.compareTo(denomination.getValue()) >= 0 && getAvailableCurrencyQuantity(denomination).compareTo(change) >= 0) {
            currencyExchange.setQuantity(denomination, change);
            return calculatePossibleCombos(currencyExchange, new BigDecimal(pendingAmount), denomination);

        } else {
            return calculatePossibleCombos(currencyExchange, exchangeAmount, denomination.nextValue(denomination));
        }
    }

    private void updateRegistry(BigDecimal pendingAmount, CurrencyExchange currencyExchange) throws ChangeNotFoundException {
        if (pendingAmount.compareTo(BigDecimal.ZERO) > 0) {
            throw new ChangeNotFoundException("no change found");
        } else {
            for (Denomination denominationVal : Denomination.values()) {
                BigInteger quantity = currencyExchange.getQuantity(denominationVal);
                takeFromRegistry(denominationVal, quantity);
            }
        }
    }

}
