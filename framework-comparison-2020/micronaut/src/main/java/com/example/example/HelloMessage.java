package com.example.example;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record HelloMessage(String msg, long ts) {}
