package com.batrom.ing.atmservice;

import java.io.Serializable;

record Task(int region,
            RequestType requestType,
            int atmId) implements Serializable {
}
