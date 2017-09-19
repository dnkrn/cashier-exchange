package com.dnkrn.dao;

import com.dnkrn.model.CurrencyExchange;
import com.dnkrn.model.Denomination;
import junit.framework.TestCase;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


public class CashRepositoryImplTest extends TestCase {

    private CashRepository cashRepository;


    @Override
    protected void setUp() throws Exception {

        cashRepository = new CashRepositoryImpl();

        Map<Denomination, BigInteger> denominationIntegerMap = new HashMap<>();
        denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(1));
        denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(2));
        denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(3));
        denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(4));
        denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(5));

        cashRepository.initRegistryInfo(denominationIntegerMap);


    }


    @Test
    public void testInitRegistryInfo() {

        Map<Denomination, BigInteger> denominationIntegerMap = new HashMap<>();
        denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(1));
        denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(3));
        denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(3));
        denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(4));
        denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(5));
        cashRepository.initRegistryInfo(denominationIntegerMap);

        assertEquals(BigDecimal.valueOf(78.0), cashRepository.getTotalAvailableAmount());

        assertEquals(BigInteger.ONE, cashRepository.getAvailableCurrencyCount(Denomination.TWENTY));
        assertEquals(BigInteger.valueOf(3), cashRepository.getAvailableCurrencyCount(Denomination.TEN));
        assertEquals(BigInteger.valueOf(3), cashRepository.getAvailableCurrencyCount(Denomination.FIVE));
        assertEquals(BigInteger.valueOf(4), cashRepository.getAvailableCurrencyCount(Denomination.TWO));
        assertEquals(BigInteger.valueOf(5), cashRepository.getAvailableCurrencyCount(Denomination.ONE));


    }

    @Test
    public void testGetRegistryInfo() {

        assertEquals(BigDecimal.valueOf(68.0), cashRepository.getTotalAvailableAmount());

        assertEquals(BigInteger.ONE, cashRepository.getAvailableCurrencyCount(Denomination.TWENTY));
        assertEquals(BigInteger.valueOf(2), cashRepository.getAvailableCurrencyCount(Denomination.TEN));
        assertEquals(BigInteger.valueOf(3), cashRepository.getAvailableCurrencyCount(Denomination.FIVE));
        assertEquals(BigInteger.valueOf(4), cashRepository.getAvailableCurrencyCount(Denomination.TWO));
        assertEquals(BigInteger.valueOf(5), cashRepository.getAvailableCurrencyCount(Denomination.ONE));

    }


    @Test
    public void testPutInRegistry() {

        cashRepository.putInRegistry(Denomination.TWENTY, BigInteger.valueOf(1));

        cashRepository.putInRegistry(Denomination.TEN, BigInteger.valueOf(2));

        cashRepository.putInRegistry(Denomination.FIVE, BigInteger.valueOf(3));

        cashRepository.putInRegistry(Denomination.TWO, BigInteger.valueOf(0));

        cashRepository.putInRegistry(Denomination.ONE, BigInteger.valueOf(5));

        assertEquals(BigDecimal.valueOf(128.0), cashRepository.getTotalAvailableAmount());

        assertEquals(BigInteger.valueOf(2), cashRepository.getAvailableCurrencyCount(Denomination.TWENTY));
        assertEquals(BigInteger.valueOf(4), cashRepository.getAvailableCurrencyCount(Denomination.TEN));
        assertEquals(BigInteger.valueOf(6), cashRepository.getAvailableCurrencyCount(Denomination.FIVE));
        assertEquals(BigInteger.valueOf(4), cashRepository.getAvailableCurrencyCount(Denomination.TWO));
        assertEquals(BigInteger.valueOf(10), cashRepository.getAvailableCurrencyCount(Denomination.ONE));

    }



    @Test
    public void testTakeInRegistry() {

        cashRepository.putInRegistry(Denomination.TWENTY, BigInteger.valueOf(1));

        cashRepository.putInRegistry(Denomination.TEN, BigInteger.valueOf(2));

        cashRepository.putInRegistry(Denomination.FIVE, BigInteger.valueOf(3));

        cashRepository.putInRegistry(Denomination.TWO, BigInteger.valueOf(0));

        cashRepository.putInRegistry(Denomination.ONE, BigInteger.valueOf(5));

        //Update till 128

        cashRepository.takeFromRegistry(Denomination.TWENTY, BigInteger.valueOf(1));

        cashRepository.takeFromRegistry(Denomination.TEN, BigInteger.valueOf(4));

        cashRepository.takeFromRegistry(Denomination.FIVE, BigInteger.valueOf(3));

        cashRepository.takeFromRegistry(Denomination.TWO, BigInteger.valueOf(0));

        cashRepository.takeFromRegistry(Denomination.ONE, BigInteger.valueOf(10));

        assertEquals(BigDecimal.valueOf(43.0), cashRepository.getTotalAvailableAmount());

        assertEquals(BigInteger.ONE, cashRepository.getAvailableCurrencyCount(Denomination.TWENTY));
        assertEquals(BigInteger.ZERO, cashRepository.getAvailableCurrencyCount(Denomination.TEN));
        assertEquals(BigInteger.valueOf(3), cashRepository.getAvailableCurrencyCount(Denomination.FIVE));
        assertEquals(BigInteger.valueOf(4), cashRepository.getAvailableCurrencyCount(Denomination.TWO));
        assertEquals(BigInteger.ZERO, cashRepository.getAvailableCurrencyCount(Denomination.ONE));

    }


    @Test
    public void  testGetAvailableCurrencyCount()
    {
        assertEquals(BigInteger.ONE, cashRepository.getAvailableCurrencyCount(Denomination.TWENTY));
        assertEquals(BigInteger.valueOf(2), cashRepository.getAvailableCurrencyCount(Denomination.TEN));
        assertEquals(BigInteger.valueOf(3), cashRepository.getAvailableCurrencyCount(Denomination.FIVE));
        assertEquals(BigInteger.valueOf(4), cashRepository.getAvailableCurrencyCount(Denomination.TWO));
        assertEquals(BigInteger.valueOf(5), cashRepository.getAvailableCurrencyCount(Denomination.ONE));

    }


    @Test
    public void  testExchangeMoney()
    {
        cashRepository.putInRegistry(Denomination.TWENTY, BigInteger.valueOf(1));

        cashRepository.putInRegistry(Denomination.TEN, BigInteger.valueOf(2));

        cashRepository.putInRegistry(Denomination.FIVE, BigInteger.valueOf(3));

        cashRepository.putInRegistry(Denomination.TWO, BigInteger.valueOf(0));

        cashRepository.putInRegistry(Denomination.ONE, BigInteger.valueOf(5));

        //Update till 128

        cashRepository.takeFromRegistry(Denomination.TWENTY, BigInteger.valueOf(1));

        cashRepository.takeFromRegistry(Denomination.TEN, BigInteger.valueOf(4));

        cashRepository.takeFromRegistry(Denomination.FIVE, BigInteger.valueOf(3));

        cashRepository.takeFromRegistry(Denomination.TWO, BigInteger.valueOf(0));

        cashRepository.takeFromRegistry(Denomination.ONE, BigInteger.valueOf(10));


        CurrencyExchange currencyExchange =cashRepository.exchangeMoney(new BigDecimal(11));
        assertEquals(BigInteger.ZERO, currencyExchange.getTwentyDollars());
        assertEquals(BigInteger.ZERO, currencyExchange.getTenDollars());
       // assertEquals(BigInteger.ONE, currencyExchange.getFiveDollars());
       // assertEquals(BigInteger.valueOf(3), currencyExchange.getTwoDollars());
       // assertEquals(BigInteger.ZERO, currencyExchange.getOneDollars());
    }








    @Override
    protected void tearDown() throws Exception {

        cashRepository = null;


    }


}
