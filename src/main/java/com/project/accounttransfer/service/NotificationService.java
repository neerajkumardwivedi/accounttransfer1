package com.project.accounttransfer.service;

@FunctionalInterface
public interface NotificationService {

	/* Purpose: For sending email notification to user */
	public void sendNotification(String fromEmail, String toEmail, String body, String subject);
}
