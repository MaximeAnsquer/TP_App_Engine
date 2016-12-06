package com.zenika.zencontact.resource;

import com.google.common.base.Optional;
import com.zenika.zencontact.domain.User;
import com.zenika.zencontact.persistence.objectify.UserDaoObjectify;
import restx.annotations.*;
import restx.factory.Component;
import restx.security.PermitAll;

@Component
@RestxResource
public class UserObjectifyResource {

  @GET("/v2/users")
  @PermitAll
  public Iterable<User> getAllUsers() {
    return UserDaoObjectify.getInstance().getAll();
  }

  @GET("/v2/users/{id}")
  @PermitAll
  public Optional<User> getUser(final Long id) {
    User user = UserDaoObjectify.getInstance().get(id);
    return Optional.fromNullable(user);
  }

  @PUT("/v2/users/{id}")
  @PermitAll
  public Optional<User> getUser(final Long id, final User user) {
    long key = UserDaoObjectify.getInstance().save(user);
    user.id = key;
    return Optional.fromNullable(user);
  }

  @DELETE("/v2/users/{id}")
  @PermitAll
  public void deleteUser(final Long id) {
    UserDaoObjectify.getInstance().delete(id);
  }

  @POST("/v2/users")
  @PermitAll
  public User storeUser(final User user) {
    if (user.id == null) {
      user.id(UserDaoObjectify.getInstance().save(user));
    }
    return user;
  }

}