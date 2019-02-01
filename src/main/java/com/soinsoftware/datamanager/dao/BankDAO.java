package com.soinsoftware.datamanager.dao;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;

import com.soinsoftware.datamanager.model.Bank;

public interface BankDAO extends CrudRepository<Bank, BigInteger> {

}