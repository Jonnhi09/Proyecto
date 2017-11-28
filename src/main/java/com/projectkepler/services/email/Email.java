package com.projectkepler.services.email;

public interface Email {

    String getFrom();

    String getTo();

    String getCc();

    String getBcc();

    String getSubject();

    String getMessage();

}
