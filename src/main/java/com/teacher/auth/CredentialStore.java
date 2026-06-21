package com.teacher.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CredentialStore {

    private static final Path DATA_DIR = Path.of("/data");
    private static final File CRED_FILE = DATA_DIR.resolve("credentials.json").toFile();
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ConcurrentHashMap<String, String> TOKENS = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(DATA_DIR);
    }

    /** Returns true if an admin password has been set */
    public boolean isSetup() {
        return CRED_FILE.exists();
    }

    /** Sets the initial admin password (only if not yet set) */
    public boolean setup(String login, String password) {
        if (CRED_FILE.exists()) return false;
        try {
            String hash = ENCODER.encode(password);
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(CRED_FILE, Map.of(
                "login", login,
                "hash", hash
            ));
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save credentials", e);
        }
    }

    /** Verify login/password and return a token, or null on failure */
    public String login(String login, String password) {
        if (!CRED_FILE.exists()) return null;
        try {
            Map<?,?> creds = MAPPER.readValue(CRED_FILE, Map.class);
            String storedHash = (String) creds.get("hash");
            String storedLogin = (String) creds.get("login");
            if (!storedLogin.equals(login)) return null;
            if (!ENCODER.matches(password, storedHash)) return null;
            String token = java.util.UUID.randomUUID().toString().replace("-", "");
            TOKENS.put(token, login);
            return token;
        } catch (IOException e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        return token != null && TOKENS.containsKey(token);
    }

    public void logout(String token) {
        TOKENS.remove(token);
    }
}
