package com.batrom.ing.onlinegame;

import java.io.Serializable;

public record OnlineGameInput(int groupCount, Clan[] clans, int[] clanSizeCounters) implements Serializable {
}
