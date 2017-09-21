package com.dnkrn.test;

import com.dnkrn.dao.CashRepository;
import com.dnkrn.dao.CashRepositoryImpl;
import com.dnkrn.exception.ChangeNotFoundException;
import com.dnkrn.model.CurrencyExchange;
import com.dnkrn.model.Denomination;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


public class CashExchangeTest extends TestCase {

    private CashRepository cashRepository;

    @Rule
    private ExpectedException exception = ExpectedException.none();

    /**
     * Initialise Cash Registry with 68$ and 1,2,3,4,5 respectively for 20,10,5,2,1
     */

    private Map<Denomination, BigInteger> denominationIntegerMap = new HashMap<>();

    @Override
    protected void setUp() throws Exception {

        cashRepository = new CashRepositoryImpl();


        denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(1));
        denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(2));
        denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(3));
        denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(4));
        denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(5));

        cashRepository.initRegistryInfo(denominationIntegerMap);

    }

    /**
     * test init registry
     */
    @Test
    public void testInitRegistryInfo() {

        denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(1));
        denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(3));
        denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(3));
        denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(4));
        denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(5));
        cashRepository.initRegistryInfo(denominationIntegerMap);

        assertEquals(BigDecimal.valueOf(78.0), cashRepository.getTotalAvailableAmount());

        assertEquals(BigInteger.ONE, cashRepository.getAvailableCurrencyQuantity(Denomination.TWENTY));
        assertEquals(BigInteger.valueOf(3), cashRepository.getAvailableCurrencyQuantity(Denomination.TEN));
        assertEquals(BigInteger.valueOf(3), cashRepository.getAvailableCurrencyQuantity(Denomination.FIVE));
        assertEquals(BigInteger.valueOf(4), cashRepository.getAvailableCurrencyQuantity(Denomination.TWO));
        assertEquals(BigInteger.valueOf(5), cashRepository.getAvailableCurrencyQuantity(Denomination.ONE));


    }

    /**
     * test condition getting current stock
     * $68 1 2 3 4 5
     */

    @Test
    public void testGetAvailableCurrencyQuantity() {

        assertEquals(BigInteger.ONE, cashRepository.getAvailableCurrencyQuantity(Denomination.TWENTY));
        assertEquals(BigInteger.valueOf(2), cashRepository.getAvailableCurrencyQuantity(Denomination.TEN));
        assertEquals(BigInteger.valueOf(3), cashRepository.getAvailableCurrencyQuantity(Denomination.FIVE));
        assertEquals(BigInteger.valueOf(4), cashRepository.getAvailableCurrencyQuantity(Denomination.TWO));
        assertEquals(BigInteger.valueOf(5), cashRepository.getAvailableCurrencyQuantity(Denomination.ONE));

    }


    /**
     * test condition putting money in stock
     * put 1 2 3 0 5
     * $128 2 4 6 4 10
     */

    @Test
    public void testPutInRegistry() {

        cashRepository.putInRegistry(Denomination.TWENTY, BigInteger.valueOf(1));

        cashRepository.putInRegistry(Denomination.TEN, BigInteger.valueOf(2));

        cashRepository.putInRegistry(Denomination.FIVE, BigInteger.valueOf(3));

        cashRepository.putInRegistry(Denomination.TWO, BigInteger.valueOf(0));

        cashRepository.putInRegistry(Denomination.ONE, BigInteger.valueOf(5));

        assertEquals(BigDecimal.valueOf(128.0), cashRepository.getTotalAvailableAmount());

        assertEquals(BigInteger.valueOf(2), cashRepository.getAvailableCurrencyQuantity(Denomination.TWENTY));
        assertEquals(BigInteger.valueOf(4), cashRepository.getAvailableCurrencyQuantity(Denomination.TEN));
        assertEquals(BigInteger.valueOf(6), cashRepository.getAvailableCurrencyQuantity(Denomination.FIVE));
        assertEquals(BigInteger.valueOf(4), cashRepository.getAvailableCurrencyQuantity(Denomination.TWO));
        assertEquals(BigInteger.valueOf(10), cashRepository.getAvailableCurrencyQuantity(Denomination.ONE));

    }

    /**
     * test condition taking money from stock
     * take 1 4 3 0 10
     * $43 1 0 3 4 0
     */

    @Test
    public void testTakeInRegistry() {

        //Update till 128

        denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(2));
        denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(4));
        denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(6));
        denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(4));
        denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(10));
        cashRepository.initRegistryInfo(denominationIntegerMap);


        cashRepository.takeFromRegistry(Denomination.TWENTY, BigInteger.valueOf(1));

        cashRepository.takeFromRegistry(Denomination.TEN, BigInteger.valueOf(4));

        cashRepository.takeFromRegistry(Denomination.FIVE, BigInteger.valueOf(3));

        cashRepository.takeFromRegistry(Denomination.TWO, BigInteger.valueOf(0));

        cashRepository.takeFromRegistry(Denomination.ONE, BigInteger.valueOf(10));

        assertEquals(BigDecimal.valueOf(43.0), cashRepository.getTotalAvailableAmount());

        assertEquals(BigInteger.ONE, cashRepository.getAvailableCurrencyQuantity(Denomination.TWENTY));
        assertEquals(BigInteger.ZERO, cashRepository.getAvailableCurrencyQuantity(Denomination.TEN));
        assertEquals(BigInteger.valueOf(3), cashRepository.getAvailableCurrencyQuantity(Denomination.FIVE));
        assertEquals(BigInteger.valueOf(4), cashRepository.getAvailableCurrencyQuantity(Denomination.TWO));
        assertEquals(BigInteger.ZERO, cashRepository.getAvailableCurrencyQuantity(Denomination.ONE));

    }


    /**
     * test exchange 11$
     * change 11
     * 0 0 1 3 0
     * @throws ChangeNotFoundException
     */
    @Test
    public void testExchangeMoney11Dollar() throws ChangeNotFoundException {

        denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(1));
        denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(3));
        denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(4));
        denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(0));
        cashRepository.initRegistryInfo(denominationIntegerMap);


        CurrencyExchange currencyExchange = cashRepository.exchangeMoney(BigDecimal.valueOf(11));

        assertEquals(BigInteger.valueOf(0), currencyExchange.getTwentyDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getTenDollars());
        assertEquals(BigInteger.valueOf(1), currencyExchange.getFiveDollars());
        assertEquals(BigInteger.valueOf(3), currencyExchange.getTwoDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getOneDollars());


        assertEquals(BigDecimal.valueOf(32.0), cashRepository.getTotalAvailableAmount());

        assertEquals(BigInteger.valueOf(1), cashRepository.getAvailableCurrencyQuantity(Denomination.TWENTY));
        assertEquals(BigInteger.valueOf(0), cashRepository.getAvailableCurrencyQuantity(Denomination.TEN));
        assertEquals(BigInteger.valueOf(2), cashRepository.getAvailableCurrencyQuantity(Denomination.FIVE));
        assertEquals(BigInteger.valueOf(1), cashRepository.getAvailableCurrencyQuantity(Denomination.TWO));
        assertEquals(BigInteger.valueOf(0), cashRepository.getAvailableCurrencyQuantity(Denomination.ONE));


    }


    /**
     * test exchange 14$
     * change 14
     * no change found
     *
     * @throws ChangeNotFoundException
     */
    @Test
    public void testExchangeMoneyNoChangeFound() throws ChangeNotFoundException {

        try {
            denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(1));
            denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(0));
            denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(2));
            denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(1));
            denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(0));
            cashRepository.initRegistryInfo(denominationIntegerMap);


            //test type
            exception.expect(ChangeNotFoundException.class);
            //test message
            exception.expectMessage("change cannot be given for the input amount as no change found");

            cashRepository.exchangeMoney(BigDecimal.valueOf(14));
        } catch (ChangeNotFoundException che) {
            che.printStackTrace();
        }

    }


    /**
     *  Test cases for 8 $ for different combinations of 13 $
     * @throws Exception
     */


    /**
     * 20 10 5 2 1
     * 0  1  0 1  1  - 13 $
     *No change found
     * @throws ChangeNotFoundException
     */
    @Test
    public void testExchangeMoney8DollarCase1() throws ChangeNotFoundException {
        try {
            denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(0));
            denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(1));
            denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(0));
            denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(1));
            denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(1));
            cashRepository.initRegistryInfo(denominationIntegerMap);

            assertEquals(BigDecimal.valueOf(13.0), cashRepository.getTotalAvailableAmount());

            //test type
            exception.expect(ChangeNotFoundException.class);
            //test message
            exception.expectMessage("change cannot be given for the input amount as no change found");

            cashRepository.exchangeMoney(BigDecimal.valueOf(8));

        } catch (ChangeNotFoundException che) {
            che.printStackTrace();
        }
    }


    /**
     * 20 10 5 2 1
     * 0  0  1 4 0  - 13 $
     * 0  0  0 4 0  - 8 $
     *
     * @throws ChangeNotFoundException
     */
    @Test
    public void testExchangeMoney8DollarCase2() throws ChangeNotFoundException {

        denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(1));
        denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(4));
        denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(0));
        cashRepository.initRegistryInfo(denominationIntegerMap);

        assertEquals(BigDecimal.valueOf(13.0), cashRepository.getTotalAvailableAmount());


        CurrencyExchange currencyExchange = cashRepository.exchangeMoney(BigDecimal.valueOf(8));

        assertEquals(BigInteger.valueOf(0), currencyExchange.getTwentyDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getTenDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getFiveDollars());
        assertEquals(BigInteger.valueOf(4), currencyExchange.getTwoDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getOneDollars());

        assertEquals(BigDecimal.valueOf(5.0), cashRepository.getTotalAvailableAmount());


    }


    /**
     * 20 10 5 2 1
     * 0  0  1 1 6  - 13 $
     * 0  0  1 1 1  - 8 $
     *
     * @throws ChangeNotFoundException
     */
    @Test
    public void testExchangeMoney8DollarCase3() throws ChangeNotFoundException {

        denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(1));
        denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(1));
        denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(6));
        cashRepository.initRegistryInfo(denominationIntegerMap);

        assertEquals(BigDecimal.valueOf(13.0), cashRepository.getTotalAvailableAmount());


        CurrencyExchange currencyExchange = cashRepository.exchangeMoney(BigDecimal.valueOf(8));

        assertEquals(BigInteger.valueOf(0), currencyExchange.getTwentyDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getTenDollars());
        assertEquals(BigInteger.valueOf(1), currencyExchange.getFiveDollars());
        assertEquals(BigInteger.valueOf(1), currencyExchange.getTwoDollars());
        assertEquals(BigInteger.valueOf(1), currencyExchange.getOneDollars());


        assertEquals(BigDecimal.valueOf(5.0), cashRepository.getTotalAvailableAmount());

    }


    /**
     * 20 10 5 2 1
     * 0  0  1 0 8  - 13 $
     * 0  0  1 0 3  - 8 $
     *
     * @throws ChangeNotFoundException
     */
    @Test
    public void testExchangeMoney8DollarCase4() throws ChangeNotFoundException {

        denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(1));
        denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(8));
        cashRepository.initRegistryInfo(denominationIntegerMap);

        assertEquals(BigDecimal.valueOf(13.0), cashRepository.getTotalAvailableAmount());


        CurrencyExchange currencyExchange = cashRepository.exchangeMoney(BigDecimal.valueOf(8));

        assertEquals(BigInteger.valueOf(0), currencyExchange.getTwentyDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getTenDollars());
        assertEquals(BigInteger.valueOf(1), currencyExchange.getFiveDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getTwoDollars());
        assertEquals(BigInteger.valueOf(3), currencyExchange.getOneDollars());

    }


    /**
     * 20 10 5 2 1
     * 0  0  0 0 13  - 13 $
     * 0  0  0 0 8  - 8 $
     *
     * @throws ChangeNotFoundException
     */
    @Test
    public void testExchangeMoney8DollarCase5() throws ChangeNotFoundException {

        denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(13));
        cashRepository.initRegistryInfo(denominationIntegerMap);

        assertEquals(BigDecimal.valueOf(13.0), cashRepository.getTotalAvailableAmount());


        CurrencyExchange currencyExchange = cashRepository.exchangeMoney(BigDecimal.valueOf(8));

        assertEquals(BigInteger.valueOf(0), currencyExchange.getTwentyDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getTenDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getFiveDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getTwoDollars());
        assertEquals(BigInteger.valueOf(8), currencyExchange.getOneDollars());

    }


    /**
     * 20 10 5 2 1
     * 0  0  0 6 1  - 13 $
     * 0  0  0 4 0  - 8 $
     *
     * @throws ChangeNotFoundException
     */
    @Test
    public void testExchangeMoney8DollarCase6() throws ChangeNotFoundException {

        denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(6));
        denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(1));
        cashRepository.initRegistryInfo(denominationIntegerMap);

        assertEquals(BigDecimal.valueOf(13.0), cashRepository.getTotalAvailableAmount());


        CurrencyExchange currencyExchange = cashRepository.exchangeMoney(BigDecimal.valueOf(8));

        assertEquals(BigInteger.valueOf(0), currencyExchange.getTwentyDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getTenDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getFiveDollars());
        assertEquals(BigInteger.valueOf(4), currencyExchange.getTwoDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getOneDollars());

    }

    /**
     * 20 10 5 2 1
     * 0  0  1 3 2  - 13 $
     * 0  0  1 1 1  - 8 $
     *
     * @throws ChangeNotFoundException
     */
    @Test
    public void testExchangeMoney8DollarCase7() throws ChangeNotFoundException {

        denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(1));
        denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(3));
        denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(2));
        cashRepository.initRegistryInfo(denominationIntegerMap);

        assertEquals(BigDecimal.valueOf(13.0), cashRepository.getTotalAvailableAmount());


        CurrencyExchange currencyExchange = cashRepository.exchangeMoney(BigDecimal.valueOf(8));

        assertEquals(BigInteger.valueOf(0), currencyExchange.getTwentyDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getTenDollars());
        assertEquals(BigInteger.valueOf(1), currencyExchange.getFiveDollars());
        assertEquals(BigInteger.valueOf(1), currencyExchange.getTwoDollars());
        assertEquals(BigInteger.valueOf(1), currencyExchange.getOneDollars());

    }

    /**
     * 20 10 5 2 1
     * 0  0  1 2 4  - 13 $
     * 0  0  1 1 1  - 8 $
     *
     * @throws ChangeNotFoundException
     */
    @Test
    public void testExchangeMoney8DollarCase8() throws ChangeNotFoundException {

        denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(1));
        denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(2));
        denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(4));
        cashRepository.initRegistryInfo(denominationIntegerMap);

        assertEquals(BigDecimal.valueOf(13.0), cashRepository.getTotalAvailableAmount());


        CurrencyExchange currencyExchange = cashRepository.exchangeMoney(BigDecimal.valueOf(8));

        assertEquals(BigInteger.valueOf(0), currencyExchange.getTwentyDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getTenDollars());
        assertEquals(BigInteger.valueOf(1), currencyExchange.getFiveDollars());
        assertEquals(BigInteger.valueOf(1), currencyExchange.getTwoDollars());
        assertEquals(BigInteger.valueOf(1), currencyExchange.getOneDollars());

    }


    /**
     * 20 10 5 2 1
     * 0  1  0 0 3  - 13 $
     * No change found
     *
     * @throws ChangeNotFoundException
     */
    @Test
    public void testExchangeMoney8DollarCase9() throws ChangeNotFoundException {

        try {
            denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(0));
            denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(1));
            denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(0));
            denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(0));
            denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(3));
            cashRepository.initRegistryInfo(denominationIntegerMap);

            assertEquals(BigDecimal.valueOf(13.0), cashRepository.getTotalAvailableAmount());


            CurrencyExchange currencyExchange = cashRepository.exchangeMoney(BigDecimal.valueOf(8));
        }
        catch(ChangeNotFoundException che)
        {
            che.printStackTrace();
        }


    }


    /**
     * 20 10 5 2 1
     * 0  0  2 1 1  - 13 $
     * 0  0  1 1 1  - 8 $
     *
     * @throws ChangeNotFoundException
     */
    @Test
    public void testExchangeMoney8DollarCase10() throws ChangeNotFoundException {

        denominationIntegerMap.put(Denomination.TWENTY, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.TEN, BigInteger.valueOf(0));
        denominationIntegerMap.put(Denomination.FIVE, BigInteger.valueOf(2));
        denominationIntegerMap.put(Denomination.TWO, BigInteger.valueOf(1));
        denominationIntegerMap.put(Denomination.ONE, BigInteger.valueOf(1));
        cashRepository.initRegistryInfo(denominationIntegerMap);

        assertEquals(BigDecimal.valueOf(13.0), cashRepository.getTotalAvailableAmount());


        CurrencyExchange currencyExchange = cashRepository.exchangeMoney(BigDecimal.valueOf(8));

        assertEquals(BigInteger.valueOf(0), currencyExchange.getTwentyDollars());
        assertEquals(BigInteger.valueOf(0), currencyExchange.getTenDollars());
        assertEquals(BigInteger.valueOf(1), currencyExchange.getFiveDollars());
        assertEquals(BigInteger.valueOf(1), currencyExchange.getTwoDollars());
        assertEquals(BigInteger.valueOf(1), currencyExchange.getOneDollars());

    }



    @Override
    protected void tearDown() throws Exception {
        cashRepository = null;
    }


}
