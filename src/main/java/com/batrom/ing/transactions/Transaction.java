package com.batrom.ing.transactions;

import jakarta.annotation.Nonnull;

public record Transaction(@Nonnull String debitAccount,
                          @Nonnull String creditAccount,
                          @Nonnull Float amount) {
}
