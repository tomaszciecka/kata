package kata.builder;

import kata.model.User;
import kata.model.UserAuthority;
import kata.model.UserRole;


public class UserBuilder extends BaseBuilder<User, UserBuilder> {

    private String login;
    private UserAuthority authority;
    private boolean enabled;

    public static UserBuilder user() {
        return new UserBuilder();
    }

    public UserBuilder withLogin(String login) {
        this.login = login;
        return thisInstance();
    }

    public UserBuilder enabled(boolean enabled) {
        this.enabled = enabled;
        return thisInstance();
    }

    public UserBuilder withRole(UserRole role) {
        this.authority = new UserAuthority();
        this.authority.setUserRole(role);
        return thisInstance();
    }

    @Override
    public User build() {
        User user = new User(login, enabled, authority);
        return user;
    }

}
