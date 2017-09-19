package com.dnkrn.controller;

import com.dnkrn.model.Denomination;

import java.math.BigInteger;


public class CashierExchange {

    public static void main(String[] args) {
        System.out.println(Denomination.TWENTY.getValue());

        System.out.println(BigInteger.ZERO.compareTo(BigInteger.valueOf(0)));


    }
}
