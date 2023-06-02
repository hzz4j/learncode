package org.hzz.mapper;

import org.hzz.spring.dto.TransactionDTO;
import org.hzz.spring.entity.Transaction;
import org.hzz.spring.mapper.TransactionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionMapperTest {

    @Autowired
    private TransactionMapper transactionMapper;

    @Test
    public void test(){
        Transaction transaction = new Transaction();
        transaction.setId(1l);
        transaction.setTotal(new BigDecimal(2));

        TransactionDTO transactionDTO = transactionMapper.transactionToTransactionDTO(transaction);
        System.out.println(transactionDTO);
        // TransactionDTO(uuid=fde2ef85-8269-4808-9eb6-e1d27de2376f, totalInCents=200)
    }
}
