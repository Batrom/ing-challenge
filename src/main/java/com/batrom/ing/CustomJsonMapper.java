package com.batrom.ing;

import com.batrom.ing.atmservice.ATM;
import com.batrom.ing.atmservice.ATMServiceInput;
import com.batrom.ing.atmservice.ATMServiceInputJsonDeserializer;
import com.batrom.ing.atmservice.ATMsSerializer;
import com.batrom.ing.onlinegame.Group;
import com.batrom.ing.onlinegame.GroupsSerializer;
import com.batrom.ing.onlinegame.OnlineGameInput;
import com.batrom.ing.onlinegame.OnlineGameInputJsonDeserializer;
import com.batrom.ing.transactions.Account;
import com.batrom.ing.transactions.AccountsSerializer;
import com.batrom.ing.transactions.TransactionsInput;
import com.batrom.ing.transactions.TransactionsInputJsonDeserializer;
import io.javalin.json.JsonMapper;

import java.lang.reflect.Type;

class CustomJsonMapper implements JsonMapper {

    @Override
    @SuppressWarnings("unchecked")
    public Object fromJsonString(final String json, final Type targetType) {
        if (OnlineGameInput.class.equals(targetType)) {
            return OnlineGameInputJsonDeserializer.deserialize(json);
        } else if (TransactionsInput.class.equals(targetType)) {
            return TransactionsInputJsonDeserializer.deserialize(json);
        } else if (ATMServiceInput.class.equals(targetType)) {
            return ATMServiceInputJsonDeserializer.deserialize(json);
        }

        throw new IllegalArgumentException("Unknown target type " + targetType);
    }

    @Override
    public String toJsonString(final Object obj, final Type type) {
        if (obj instanceof Account[] accounts) {
            return AccountsSerializer.serialize(accounts);
        } else if (obj instanceof Group[] groups) {
            return GroupsSerializer.serialize(groups);
        } else if (obj instanceof ATM[] atms) {
            return ATMsSerializer.serialize(atms);
        }

        return obj.toString();
    }
}
