package com.pcda.mb.travel.emailticket.model;

import java.util.List;

import lombok.Data;

@Data
public class Mail {
    private String mailFrom;
    private String mailTo;
    private String mailCc;
    private String mailBcc;
    private String mailSubject;
    private String mailContent;
    private String contenType;
    private List<Object> attachments;
}