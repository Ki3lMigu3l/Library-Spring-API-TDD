package com.github.ki3lmigu3l.library.api.service;

import java.util.List;

public interface EmailService {
    void sendMails(String message, List<String> mailsList);
}
