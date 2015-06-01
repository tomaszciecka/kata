package kata.builder;

import kata.model.User;


public class UserBuilder extends BaseBuilder<User, UserBuilder>{

    private String login;
    
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
    
    @Override
    public User build() {
        User user = new User(login, enabled);
        return user;
    }

}
