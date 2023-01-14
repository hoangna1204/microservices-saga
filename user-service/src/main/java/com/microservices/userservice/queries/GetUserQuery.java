package com.microservices.userservice.queries;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public record GetUserQuery(String userId) {
}
