package com.batrom.ing.transactions;

import java.util.List;

public record AccountsBalanceChange(List<AccountBalanceChange> accountsStartingWith0,
                                    List<AccountBalanceChange> accountsStartingWith1,
                                    List<AccountBalanceChange> accountsStartingWith2,
                                    List<AccountBalanceChange> accountsStartingWith3,
                                    List<AccountBalanceChange> accountsStartingWith4,
                                    List<AccountBalanceChange> accountsStartingWith5,
                                    List<AccountBalanceChange> accountsStartingWith6,
                                    List<AccountBalanceChange> accountsStartingWith7,
                                    List<AccountBalanceChange> accountsStartingWith8,
                                    List<AccountBalanceChange> accountsStartingWith9,
                                    List<AccountBalanceChange> otherAccounts) {
}
