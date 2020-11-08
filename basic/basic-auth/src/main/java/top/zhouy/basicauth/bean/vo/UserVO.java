package top.zhouy.basicauth.bean.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import top.zhouy.commonresponse.bean.entity.User;

import java.io.Serializable;
import java.util.*;

/**
 * @description: 用户信息
 * @author: zhouy
 * @create: 2020-11-03 18:06:00
 */
@Data
public class UserVO extends User implements UserDetails {

    private String phone;

    private String password;

    private Set<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet(new AuthorityComparator());
        Iterator var2 = authorities.iterator();

        while(var2.hasNext()) {
            GrantedAuthority grantedAuthority = (GrantedAuthority)var2.next();
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }
        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = 510L;

        private AuthorityComparator() {
        }

        @Override
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            if (g2.getAuthority() == null) {
                return -1;
            } else {
                return g1.getAuthority() == null ? 1 : g1.getAuthority().compareTo(g2.getAuthority());
            }
        }
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }
}
