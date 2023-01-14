package com.microservices.userservice.controllers;

import com.microservices.userservice.data.User;
import com.microservices.userservice.queries.GetUserQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserQueryController {

  private final QueryGateway queryGateway;

  @GetMapping("{userId}")
  public User getUser(@PathVariable String userId) {
    var getUserQuery = new GetUserQuery(userId);
    return queryGateway.query(getUserQuery, ResponseTypes.instanceOf(User.class)).join();
  }
}
