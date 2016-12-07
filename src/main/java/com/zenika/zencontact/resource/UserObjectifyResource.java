package com.zenika.zencontact.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.zenika.zencontact.fetch.PartnerBirthdayService;
import restx.annotations.DELETE;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.PUT;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.zenika.zencontact.domain.User;
import com.zenika.zencontact.domain.blob.PhotoService;
import com.zenika.zencontact.persistence.objectify.UserDaoObjectify;

@Component
@RestxResource
public class UserObjectifyResource {

  private static final String CONTACTS_CACHE_KEY = "com.zenika.training.zencontact.service.ContactService.getAll";
  private MemcacheService cache = MemcacheServiceFactory.getMemcacheService();

  @GET("/v2/users")
  @PermitAll
  /**
   * Get the users defined in a arraylist
   */
  public Iterable<User> getAllUsers() {
    List<User> contacts = (List<User>) cache.get(CONTACTS_CACHE_KEY);
    if (contacts == null) {
      contacts = UserDaoObjectify.getInstance().getAll();
      // on met en cache qu'un liste avec au moins un élément
      boolean isCached = contacts.size() > 0
        && cache.put(CONTACTS_CACHE_KEY, contacts,
        Expiration.byDeltaSeconds(240),
        MemcacheService.SetPolicy. ADD_ONLY_IF_NOT_PRESENT);
    }
    return contacts;
  }

  @GET("/v2/users/{id}")
  @PermitAll
  public Optional<User> getUser(final Long id) {
    User user = UserDaoObjectify.getInstance().get(id);
    PhotoService.getInstance().prepareDownloadURL(user);
    PhotoService.getInstance().prepareUploadURL(user);
    return Optional.fromNullable(user);
  }

  @PUT("/v2/users/{id}")
  @PermitAll
  public Optional<User> updateUser(final Long id, final User user) {
    UserDaoObjectify.getInstance().save(user);
    return Optional.fromNullable(user);
  }

  @DELETE("/v2/users/{id}")
  @PermitAll
  public void deleteUser(final Long id) {
    cache.delete(CONTACTS_CACHE_KEY);
    // don't forget the blob
    PhotoService.getInstance().deleteOldBlob(id);
    UserDaoObjectify.getInstance().delete(id);
  }

  @POST("/v2/users")
  @PermitAll
  public User storeUser(final User user) {
    cache.delete(CONTACTS_CACHE_KEY) ;

    if (user.birthdate == null) {
      String birthdate = PartnerBirthdayService
        .getInstance()
        .findBirthdate(user.firstName, user.lastName);
      if (birthdate != null) {
        try {
          user.birthdate(new SimpleDateFormat("yyyy-MM-dd")
            .parse(birthdate));
        }
        catch (ParseException e) {

        }
      }
    }

    if (user.id == null) {
      user.id(UserDaoObjectify.getInstance().save(user));
    }
    return user;
  }

}