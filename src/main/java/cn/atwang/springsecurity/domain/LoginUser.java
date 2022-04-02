package cn.atwang.springsecurity.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails, Serializable {

    private User user;

    //存储权限信息
    private List<String> permissions;

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    //存储springSecurity所需要的权限信息的集合
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> list;

    //返回权限信息
    @JSONField(serialize = false)//此方法不参与转换
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //避免每调用一次就要转换
        if (list!=null){
            return list;
        }
        list = permissions.stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return list;
    }

    //返回密码
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //返回用户名
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    //是否没过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //是否超时
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
