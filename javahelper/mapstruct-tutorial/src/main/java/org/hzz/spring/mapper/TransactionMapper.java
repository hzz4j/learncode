package org.hzz.spring.mapper;

import org.hzz.spring.dto.TransactionDTO;
import org.hzz.spring.entity.Transaction;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TransactionMapper {

    public TransactionDTO transactionToTransactionDTO(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }
        TransactionDTO transactionDTO = new TransactionDTO();

        transactionDTO.setUuid( transaction.getUuid() );
        transactionDTO.setTotalInCents(transaction.getTotal().multiply(
                new BigDecimal("100")
        ).longValue());

        return transactionDTO;
    }

    public  abstract List<TransactionDTO> toTransactionDTO(Collection<Transaction> transactions);
}
