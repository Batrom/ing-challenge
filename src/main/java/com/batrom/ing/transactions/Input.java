package com.batrom.ing.transactions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

@JsonDeserialize(using = InputDeserializer.class)
interface Input extends Serializable {
    Account[] toResponse();
}
