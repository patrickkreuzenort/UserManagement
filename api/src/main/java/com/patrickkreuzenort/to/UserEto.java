package com.patrickkreuzenort.to;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class UserEto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean enabled;
    private boolean tokenExpired;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(boolean tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEto userEto = (UserEto) o;
        return enabled == userEto.enabled && tokenExpired == userEto.tokenExpired && Objects.equals(firstName, userEto.firstName) &&
                Objects.equals(lastName, userEto.lastName) && Objects.equals(email, userEto.email) && Objects.equals(password, userEto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, password, enabled, tokenExpired);
    }
}
