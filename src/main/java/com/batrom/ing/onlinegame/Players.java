package com.batrom.ing.onlinegame;

import java.io.Serializable;

record Players(int groupCount, Clan[] clans) implements Serializable {
}
