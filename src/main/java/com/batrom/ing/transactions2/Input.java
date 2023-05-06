package com.batrom.ing.transactions2;

import java.io.Serializable;

public interface Input extends Serializable {
    Account[] toResponse();
}
