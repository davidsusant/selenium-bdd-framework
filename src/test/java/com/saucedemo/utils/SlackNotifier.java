package com.saucedemo.utils;

import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import com.slack.api.webhook.WebhookResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SlackNotifier {
    private static final Slack slack = Slack.getInstance();
    private static final String WEBHOOK_URL = ConfigReader.getProperty("slack.webhook.url");
    private static final boolean NOTIFICATIONS_ENABLED = Boolean.parseBoolean(ConfigReader.getProperty("slack.notifications.enabled"));

    public static void sendTestResults(int totalTests, int passed, int failed, int skipped, long duration) {
        if (!NOTIFICATIONS_ENABLED) {
            return;
        }

        String status = failed > 0 ? ":x: FAILED" : ":white_check_mark: PASSED";
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String message = String.format(
                "*Test Execution Report*\n" +
                "Status: %s\n" +
                "Timestamp: %s\n" +
                "Total Tests: %d\n" +
                "Passed: %d :white_check_mark:\n" +
                "Failed: %d :x:\n" +
                "Skipped: %d :warning:\n" +
                "Duration: %d seconds\n" +
                "View detailed report: <http://your-ci-server/allure-report|Allure Report>",
                status, timestamp, totalTests, passed, failed, skipped, duration / 1000
        );

        Payload payload = Payload.builder()
                .text(message)
                .build();

        try {
            WebhookResponse response = slack.send(WEBHOOK_URL, payload);
            if (response.getCode() != 200) {
                System.err.println("Failed to send Slack notification: " + response.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error sending Slack notification: " + e.getMessage());
        }
    }

    public static void sendMessage(String message) {
        if (!NOTIFICATIONS_ENABLED) {
            return;
        }

        Payload payload = Payload.builder()
                .text(message)
                .build();

        try {
            slack.send(WEBHOOK_URL, payload);
        } catch (IOException e) {
            System.err.println("Error sending Slack message: " + e.getMessage());
        }
    }
}
