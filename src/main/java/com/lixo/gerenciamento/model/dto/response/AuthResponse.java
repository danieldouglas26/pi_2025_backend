package com.lixo.gerenciamento.model.dto.response;

public class AuthResponse {
    private String token;
    private UserInfo user;

    public AuthResponse() {
    }

    public AuthResponse(String token) {
        this.token = token;
    }

    public AuthResponse(String token, UserInfo user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        AuthResponse that = (AuthResponse) o;
        
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = token != null ? token.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + (token != null ? "***MASKED***" : "null") + '\'' +
                ", user=" + user +
                '}';
    }

    public static class UserInfo {
        private String id;
        private String username;

        public UserInfo() {
        }

        public UserInfo(String id, String username) {
            this.id = id;
            this.username = username;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            
            UserInfo userInfo = (UserInfo) o;
            
            if (id != null ? !id.equals(userInfo.id) : userInfo.id != null) return false;
            return username != null ? username.equals(userInfo.username) : userInfo.username == null;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (username != null ? username.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "id='" + id + '\'' +
                    ", username='" + username + '\'' +
                    '}';
        }
    }
}