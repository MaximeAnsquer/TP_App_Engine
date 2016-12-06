package com.zenika.zencontact.persistence.datastore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.zenika.zencontact.domain.User;
import com.zenika.zencontact.persistence.UserDao;

public class UserDaoDatastore implements UserDao {

    private static UserDaoDatastore INSTANCE = new UserDaoDatastore();

    public static UserDaoDatastore getInstance() {
        return INSTANCE;
    }

    public DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public long save(User contact) {
        Entity e = new Entity("User");
        if (contact.id != null) {
            Key k = KeyFactory.createKey("user",contact.id);
            try{
                e = datastore.get(k);
            } catch(EntityNotFoundException ex){}
        }
        e.setProperty("firstname",contact.firstName);
        e.setProperty("lastname",contact.lastName);
        e.setProperty("email",contact.email);
        e.setProperty("notes",contact.notes);

        Key key = datastore.put(e);

        return key.getId();

    }

    public void delete(Long id) {
        Key k = KeyFactory.createKey("User",id);
        datastore.delete(k);

    }

    public User get(Long id) {
        Entity e;
        try{
            e=datastore.get(KeyFactory.createKey("User", id));
        }
        catch(EntityNotFoundException ex){
            throw new RuntimeException(ex);
        }

        return  User.create()
          .id(e.getKey().getId())
          .firstName((String) e.getProperty("firstname"))
          .lastName((String) e.getProperty("lastname"))
          .email((String) e.getProperty("email"))
          .notes((String) e.getProperty("notes"));
    }

    public List<User> getAll() {
        List<User> contacts = new ArrayList<>();

        Query q = new Query("User")
          .addProjection(new PropertyProjection("firstname", String.class))
          .addProjection(new PropertyProjection("lastname", String.class))
          .addProjection(new PropertyProjection("email", String.class))
          .addProjection(new PropertyProjection("notes", String.class));

        PreparedQuery pq = datastore.prepare(q);

        for(Entity e : pq.asIterable()){
            contacts.add(
              User.create()
                .id(e.getKey().getId())
                .firstName((String) e.getProperty("firstname"))
                .lastName((String) e.getProperty("lastname"))
                .email((String) e.getProperty("email"))
                .notes((String) e.getProperty("notes"))
            );
        }

        return contacts;
    }
}