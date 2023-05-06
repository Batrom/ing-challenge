package com.batrom.ing.transactions;

import java.util.List;

interface TransactionsService {
    Account[] process(final List<Transaction> transactions);
}
