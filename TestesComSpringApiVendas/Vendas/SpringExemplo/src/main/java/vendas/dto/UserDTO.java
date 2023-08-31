package vendas.dto;

import vendas.entites.User;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private static final long serialverionUID=1L;
    private Long id;
    private String name,email;

    public UserDTO() {
    }

    public UserDTO(User obj){
        id= obj.getId();
        name=obj.getName();
        email=obj.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
