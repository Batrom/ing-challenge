package com.batrom.ing.transactions;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class AccountBalanceChangesDeserializer extends JsonDeserializer<AccountBalanceChange[]> {
    @Override
    public AccountBalanceChange[] deserialize(final JsonParser jsonParser, final DeserializationContext context) throws IOException, JacksonException {
        final var node = readTreeToArrayNode(jsonParser);

        return readSequentially(node);
//        return readInParallel(node);
    }

    private static ArrayNode readTreeToArrayNode(final JsonParser jsonParser) throws IOException {
        return jsonParser.getCodec().readTree(jsonParser);
    }

    private static AccountBalanceChange[] readInParallel(final ArrayNode node) {
        return StreamSupport.stream(node.spliterator(), true)
                .flatMap(transaction -> {
                    final var debitAccount = transaction.get("debitAccount").asText();
                    final var creditAccount = transaction.get("creditAccount").asText();
                    final var amount = transaction.get("amount").floatValue();
                    return Stream.of(
                            new AccountBalanceChange(debitAccount, -amount),
                            new AccountBalanceChange(creditAccount, amount));
                })
                .toArray(AccountBalanceChange[]::new);
    }

    private static AccountBalanceChange[] readSequentially(final ArrayNode node) {
        final var changes = new AccountBalanceChange[node.size() * 2];
        final var iterator = node.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            final var transaction = iterator.next();
            final var debitAccount = transaction.get("debitAccount").asText();
            final var creditAccount = transaction.get("creditAccount").asText();
            final var amount = transaction.get("amount").floatValue();
            changes[i] = new AccountBalanceChange(debitAccount, -amount);
            changes[i + 1] = new AccountBalanceChange(creditAccount, amount);
            i += 2;
        }
        return changes;
    }
}
