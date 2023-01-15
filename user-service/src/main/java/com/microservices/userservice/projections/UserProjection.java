package com.microservices.userservice.projections;

import com.microservices.userservice.data.User;
import com.microservices.userservice.queries.GetUserQuery;
import com.microservices.userservice.data.UserRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserProjection {
  private final UserRepository userRepository;

  @QueryHandler
  public User getUser(GetUserQuery query) {
    return userRepository.findById(query.userId()).orElseThrow();
  }
}
