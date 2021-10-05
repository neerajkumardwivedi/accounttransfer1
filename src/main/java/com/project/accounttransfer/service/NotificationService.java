package com.project.accounttransfer.service;


@FunctionalInterface
public interface NotificationService {

	public void sendNotification(String fromEmail,String toEmail,String body,String subject);
}
