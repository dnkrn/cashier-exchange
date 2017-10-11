package com.dnkrn.dao;

import com.dnkrn.exception.ChangeNotFoundException;
import com.dnkrn.model.CurrencyExchange;
import com.dnkrn.model.Denomination;

import java.util.Map;

/**
 * Interface which defines the operations to be performed on the cash registry
 */

public interface CashRepository {

    /**
     * initializes the registry with the given input
     *
     * @param values
     */

    void initRegistryInfo(Map<Denomination, Integer> values);


    /**
     * returns the total sum available in the cash registry
     *
     * @return
     */

    double getTotalAvailableAmount();

    /**
     * returns the number of quantity available for each denomination
     *
     * @param denomination
     * @return
     */

    int getAvailableCurrencyQuantity(Denomination denomination);

    /**
     * returns true if the put operation is success
     *
     * @param denomination
     * @param quantity
     * @return
     */

    boolean putInRegistry(Denomination denomination, int quantity);

    /**
     * returns true if the take operations is success
     *
     * @param denomination
     * @param quantity
     * @return
     */

    boolean takeFromRegistry(Denomination denomination, int quantity);

    /**
     * returns the currency change for each Currency given
     *
     * @param exchangeAmount
     * @return
     * @throws ChangeNotFoundException
     */

    CurrencyExchange exchangeMoney(double exchangeAmount) throws ChangeNotFoundException;

}
