package com.zenika.zencontact.fetch;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class PartnerBirthdayService {

  private static final Logger LOG = Logger.getLogger(PartnerBirthdayService.class.getName());

  private static final String SERVICE_URL = "http://zenpartenaire.appspot.com/zenpartenaire";

  private static PartnerBirthdayService INSTANCE = new PartnerBirthdayService();

  public static PartnerBirthdayService getInstance() {
    return INSTANCE;
  }

  public String findBirthdate(String firstname, String lastname) {

    try {

      // Instanciation du service URLFetch
      URLFetchService fetcher;
      fetcher = URLFetchServiceFactory.getURLFetchService();

      // Construction d'une requête POST
      URL url = new URL(SERVICE_URL);
      HTTPRequest postRequest = new HTTPRequest(url, HTTPMethod.POST,
        FetchOptions.Builder.withDeadline(30));

      String payload = firstname + " " + lastname;
      postRequest.setPayload(payload.getBytes());

      // Envoie d'une requête POST
      Future<HTTPResponse> futurePost = fetcher.fetchAsync(postRequest);

      // Traitement de la réponse de la requête POST
      HTTPResponse response = futurePost.get();
      if (response.getResponseCode() != 201) {
      } // Erreur
      byte[] content = response.getContent();

      String s = new String(content).trim();
      LOG.warning("From Partner:" + s);
      if (s.length() > 0) {
        return s;
      }
      return null;

    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }

  }
}