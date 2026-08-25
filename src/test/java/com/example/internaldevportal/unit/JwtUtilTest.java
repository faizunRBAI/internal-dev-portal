package com.example.internaldevportal.unit;

import com.example.internaldevportal.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil("testSecretKeyMustBe32CharactersLong!!", 3600000L);
    }

    @Test
    void generateToken_thenExtractUsername_matches() {
        String token = jwtUtil.generateToken("alice", "ROLE_USER");
        assertThat(jwtUtil.extractUsername(token)).isEqualTo("alice");
    }

    @Test
    void validToken_isValid_returnsTrue() {
        String token = jwtUtil.generateToken("bob", "ROLE_ADMIN");
        assertThat(jwtUtil.isValid(token)).isTrue();
    }

    @Test
    void tamperedToken_isValid_returnsFalse() {
        String token = jwtUtil.generateToken("carol", "ROLE_USER") + "tampered";
        assertThat(jwtUtil.isValid(token)).isFalse();
    }

    @Test
    void expiredToken_isValid_returnsFalse() {
        JwtUtil shortLived = new JwtUtil("testSecretKeyMustBe32CharactersLong!!", 1L);
        String token = shortLived.generateToken("dave", "ROLE_USER");
        try { Thread.sleep(10); } catch (InterruptedException ignored) { }
        assertThat(shortLived.isValid(token)).isFalse();
    }
}
