#!/bin/bash

nohup java -Xmx1g -Xms1g -XX:+UseG1GC -jar target/ing-jar-with-dependencies.jar &