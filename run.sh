#!/bin/bash

nohup java -Xmx512m -Xms512m -XX:+UseG1GC -jar target/ing-jar-with-dependencies.jar &