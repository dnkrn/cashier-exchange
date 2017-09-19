package com.dnkrn.dao;

import com.dnkrn.exception.ChangeNotFoundException;
import com.dnkrn.model.CurrencyExchange;
import com.dnkrn.model.Denomination;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;


public interface CashRepository {

    void initRegistryInfo(Map<Denomination,BigInteger> values);

    BigDecimal getTotalAvailableAmount();

    BigInteger getAvailableCurrencyCount(Denomination denomination);

    boolean putInRegistry(Denomination denomination,BigInteger quantity);

    boolean takeFromRegistry(Denomination denomination,BigInteger quantity);

    CurrencyExchange exchangeMoney(BigDecimal exchangeAmount)  throws ChangeNotFoundException;

}
