package br.com.sandes.data.vo.v1.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TokenVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private Boolean authenticated;
    private Date created;
    private Date expiration;
    private String accessToken;
    private String refreshToken;

    public TokenVO(String username,
                   Boolean authenticated,
                   Date created,
                   Date expiration,
                   String accessToken,
                   String refreshToken) {

        this.username = username;
        this.authenticated = authenticated;
        this.created = created;
        this.expiration = expiration;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public TokenVO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenVO tokenVO = (TokenVO) o;

        if (!Objects.equals(username, tokenVO.username)) return false;
        if (!Objects.equals(authenticated, tokenVO.authenticated))
            return false;
        if (!Objects.equals(created, tokenVO.created)) return false;
        if (!Objects.equals(expiration, tokenVO.expiration)) return false;
        if (!Objects.equals(accessToken, tokenVO.accessToken)) return false;
        return Objects.equals(refreshToken, tokenVO.refreshToken);
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (authenticated != null ? authenticated.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (expiration != null ? expiration.hashCode() : 0);
        result = 31 * result + (accessToken != null ? accessToken.hashCode() : 0);
        result = 31 * result + (refreshToken != null ? refreshToken.hashCode() : 0);
        return result;
    }
}
