package com.raphael.cantina.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@SuppressWarnings("serial")
@Entity
@Table(name = "usuario")
public class Usuario extends AbstractEntity<Long>{
    
    @Column
    @NotEmpty(message = "{campo.login.obrigatorio}")
    private String login;

    @Column
    @NotEmpty(message = "{campo.senha.obrigatorio}")    
    private String senha;

    @Column(nullable = false, columnDefinition="tinyint(1) default 1")
	private boolean enabled;

    @OneToMany(mappedBy = "usuario")
	private List<Usuario_Roles> roles;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Usuario_Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Usuario_Roles> roles) {
        this.roles = roles;
    }
}