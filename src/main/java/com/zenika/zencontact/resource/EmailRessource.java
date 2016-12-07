package com.zenika.zencontact.resource;

import com.zenika.zencontact.domain.Email;
import com.zenika.zencontact.email.EmailService;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;

@Component
@RestxResource
public class EmailRessource {

  @POST("/v2/email")
  @PermitAll
  public void sendEMail(Email email) {
    EmailService.getInstance().sendEmail(email);
  }


}
