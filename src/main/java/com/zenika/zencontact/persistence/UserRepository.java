package com.zenika.zencontact.persistence;

import com.zenika.zencontact.domain.User;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public static List<User> USERS = new ArrayList<User>(10);

    static {

        USERS.add(User.create()
                .id(123L)
                .firstName("Julien")
                .lastName("Landuré")
                .email("julien.landure@zenika.com")
                .password("julien")
                .lastConnectionDate(DateTime.parse("2014-02-19T14:34:20.000+02:00").toDate()));
        USERS.add(User.create()
                .id(1L)
                .firstName("Pierre")
                .lastName("Reliquet")
                .email("pierre.reliquet@zenika.com")
                .password("pierre")
                .lastConnectionDate(DateTime.parse("2013-03-12T09:33").toDate()));
    }
}
