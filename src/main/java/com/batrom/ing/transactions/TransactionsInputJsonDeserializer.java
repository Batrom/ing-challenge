package com.batrom.ing.transactions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static com.batrom.ing.SerializationHelper.*;
import static org.eclipse.jetty.util.StringUtil.isEmpty;

public class TransactionsInputJsonDeserializer {

    private static final Account[] EMPTY_ACCOUNTS = new Account[0];
    private static final TransactionsInput EMPTY_INPUT = new TransactionsInput(EMPTY_ACCOUNTS);

    public static TransactionsInput deserialize(final String json) {
        if (isEmpty(json)) return EMPTY_INPUT;

        final var accountsMap = new HashMap<String, Account>();
        final var chars = json.toCharArray();

        // find start and end
        final var startingIndex = startingIndexForArrayRoot(chars);
        final var endingIndex = endingIndexForArrayRoot(chars);
        for (int jsonPointer = startingIndex + 1; jsonPointer < endingIndex; jsonPointer++) {
            var character = chars[jsonPointer];

            // skip until next object opening found - so skip everything that is not '{'
            if (character != START_OBJECT) {
                continue;
            }

            char[] debitAccount = new char[26];
            char[] creditAccount = new char[26];
            long amount = 0;

            // iterate until closing object found - so until '}' reached
            while (character != END_OBJECT) {

                if (character == LOWER_D) {
                    jsonPointer = fillDebitAccount(debitAccount, chars, jsonPointer);
                } else if (character == LOWER_C) {
                    jsonPointer = fillCreditAccount(creditAccount, chars, jsonPointer);
                } else if (character == LOWER_A) {
                    // skip 'amount":' sentence
                    jsonPointer += 8;

                    // skip until value started - so skip until digit reached
                    jsonPointer = skipUntilStartOfNumberReached(chars, jsonPointer);

                    final int startNumberIndex = jsonPointer;

                    // skip until end of value - so skip until non-digit reached
                    jsonPointer = skipUntilEndOfNumberReached(chars, jsonPointer);

                    final int endNumberIndex = jsonPointer;
                    final var numberLength = endNumberIndex - startNumberIndex + 3;

                    // check decimal places
                    var dotCharacter = chars[++jsonPointer];
                    if (dotCharacter == DOT) {
                        final var tenthsDigit = chars[++jsonPointer];
                        if (isDigit(tenthsDigit)) {
                            amount += Character.getNumericValue(tenthsDigit) * 10L;

                            final var hundredthsDigit = chars[++jsonPointer];
                            if (isDigit(hundredthsDigit)) {
                                amount += Character.getNumericValue(hundredthsDigit);

                                final var thousandthsDigit = chars[++jsonPointer];
                                // round up if digit at thousandths decimal place is greater or equal to 5
                                if (isDigitGreaterOrEqualToFive(thousandthsDigit)) {
                                    ++amount;
                                } else {
                                    --jsonPointer;
                                }
                            } else {
                                --jsonPointer;
                            }
                        } else {
                            --jsonPointer;
                        }
                    } else {
                        --jsonPointer;
                    }

                    // create number that is multiplied by 100 - to get rid of decimal places
                    for (int digitPositon = 2, numberIndex = endNumberIndex; digitPositon < numberLength; digitPositon++, numberIndex--) {
                        amount += POWERS_OF_TEN[digitPositon] * Character.getNumericValue(chars[numberIndex]);
                    }
                }
                character = chars[++jsonPointer];
            }

            updateCreditAccount(accountsMap, String.valueOf(creditAccount), amount);
            updateDebitAccount(accountsMap, String.valueOf(debitAccount), -amount);
        }
        return new TransactionsInput(toSortedArray(accountsMap));
    }

    private static int fillDebitAccount(final char[] debitAccount, final char[] chars, int jsonPointer) {
        // skip 'debitAccount":' sentence
        jsonPointer += 14;

        // skip until value started - so skip everything that is not equal to '"'
        jsonPointer = skipUntilStartOfTextValueReached(chars, jsonPointer);

        // copy value and increment jsonPointer by value length (26)
        System.arraycopy(chars, jsonPointer, debitAccount, 0, 26);
        jsonPointer += 26;
        return jsonPointer;
    }

    private static int fillCreditAccount(final char[] creditAccount, final char[] chars, int jsonPointer) {
        // skip 'creditAccount":' sentence
        jsonPointer += 15;

        // skip until value started - so skip everything that is not equal to '"'
        jsonPointer = skipUntilStartOfTextValueReached(chars, jsonPointer);

        // copy value and increment jsonPointer by value length (26)
        System.arraycopy(chars, jsonPointer, creditAccount, 0, 26);
        jsonPointer += 26;
        return jsonPointer;
    }

    private static void updateCreditAccount(final Map<String, Account> accountsMap, final String accountNumber, final long amount) {
        final var account = accountsMap.get(accountNumber);
        if (account != null) {
            account.updateCredit(amount);
        } else {
            accountsMap.put(accountNumber, Account.initializeWithCredit(accountNumber, amount));
        }
    }

    private static void updateDebitAccount(final Map<String, Account> accountsMap, final String accountNumber, final long amount) {
        final var account = accountsMap.get(accountNumber);
        if (account != null) {
            account.updateDebit(amount);
        } else {
            accountsMap.put(accountNumber, Account.initializeWithDebit(accountNumber, amount));
        }
    }

    private static Account[] toSortedArray(final Map<String, Account> accountsMap) {
        final var accounts = accountsMap.values().toArray(EMPTY_ACCOUNTS);
        Arrays.sort(accounts, Comparator.comparing(Account::getAccount));
        return accounts;
    }
}
