package com.dnkrn.dao;

import com.dnkrn.exception.ChangeNotFoundException;
import com.dnkrn.model.CurrencyExchange;
import com.dnkrn.model.CurrencyRegistry;
import com.dnkrn.model.Denomination;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/**
 * DAO Implementation
 */

public class CashRepositoryImpl implements CashRepository {


    private CurrencyRegistry currencyRegistry = new CurrencyRegistry();


    @Override
    public void initRegistryInfo(final Map<Denomination, BigInteger> values) {

        values.forEach((denomination, quantity) -> {

            switch (denomination) {

                case TWENTY:
                    currencyRegistry.setAvailableTwenty(quantity);
                    break;

                case TEN:
                    currencyRegistry.setAvailableTen(quantity);
                    break;

                case FIVE:
                    currencyRegistry.setAvailableFive(quantity);
                    break;

                case TWO:
                    currencyRegistry.setAvailableTwo(quantity);
                    break;

                case ONE:
                    currencyRegistry.setAvailableOne(quantity);
                    break;

                default:
                    throw new UnsupportedOperationException();

            }

        });

    }


    @Override
    public BigDecimal getTotalAvailableAmount() {

        return Denomination.TWENTY.getValue().multiply(new BigDecimal(currencyRegistry.getAvailableTwenty()))
                .add(Denomination.TEN.getValue().multiply(new BigDecimal(currencyRegistry.getAvailableTen())))
                .add(Denomination.FIVE.getValue().multiply(new BigDecimal(currencyRegistry.getAvailableFive())))
                .add(Denomination.TWO.getValue().multiply(new BigDecimal(currencyRegistry.getAvailableTwo())))
                .add(Denomination.ONE.getValue().multiply(new BigDecimal(currencyRegistry.getAvailableOne())));

    }


    @Override
    public BigInteger getAvailableCurrencyCount(Denomination denomination) {

        switch (denomination) {

            case TWENTY:
                return currencyRegistry.getAvailableTwenty();

            case TEN:
                return currencyRegistry.getAvailableTen();

            case FIVE:
                return currencyRegistry.getAvailableFive();

            case TWO:
                return currencyRegistry.getAvailableTwo();

            case ONE:
                return currencyRegistry.getAvailableOne();

            default:
                throw new UnsupportedOperationException();

        }
    }


    @Override
    public boolean putInRegistry(Denomination denomination, BigInteger quantity) {

        if (BigInteger.ZERO.compareTo(quantity) >= 0) {
            return false;
        }
        return currencyRegistry.addQuantity(denomination, quantity);
    }


    @Override
    public boolean takeFromRegistry(Denomination denomination, BigInteger quantity) {

        if (BigInteger.ZERO.compareTo(quantity) >= 0) {
            return false;
        }
       return currencyRegistry.subtractQuantity(denomination,quantity);
    }


    @Override
    public CurrencyExchange exchangeMoney(BigDecimal exchangeAmount) throws ChangeNotFoundException{

        CurrencyExchange currencyExchange = new CurrencyExchange();

        /*for(Denomination denomination: Denomination.values())
        {
            if(exchangeAmount.compareTo(denomination.getValue()) >=0  )
            {
                BigDecimal[] divideAndRemainder= exchangeAmount.divideAndRemainder(denomination.getValue());
                BigInteger change = divideAndRemainder[0].toBigInteger();
                BigInteger pendingAmount= divideAndRemainder[1].toBigInteger();
                int i=change.intValue();
                while( i >= 0 && pendingAmount.compareTo(BigInteger.ZERO) >=0) {

                    if (getAvailableCurrencyCount(denomination).compareTo(change) >= 0) {
                        takeFromRegistry(denomination, change);
                        currencyExchange.setQuantity(denomination, change);
                        exchangeAmount = divideAndRemainder[1];
                    }

                }
            }
        }*/


        Denomination[] values = Denomination.values();

        Denomination lastSuccessDenom=values[0];





        if(exchangeAmount.compareTo(BigDecimal.ZERO) > 0)
        {
            throw new ChangeNotFoundException("no change available");
        }
        return currencyExchange;
    }


}
