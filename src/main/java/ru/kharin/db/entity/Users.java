package ru.kharin.db.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "users", schema = "public")
public class Users implements Serializable {

    @Id
    @Getter@Setter
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter@Setter
    @Column(name = "login")
    private String login;

    @Getter@Setter
    @Column(name = "password")
    private String password;

    @Getter@Setter
    @Column(name = "nickname")
    private String nickname;

    @Getter@Setter
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserAchievement userAchievement;

    public Users() {}

    public Users(Long id){
        this.id = id;
    }

    public Users(String login, String password, String nickname) {
        this.login = login;
        this.password = password;
        this.nickname = nickname;
    }

    public Boolean isBlocked(){
        return false;
    }


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Users)){
            return false;
        }

        Users type = (Users)obj;
        if((this.id == null && type.id != null) || (this.id != null && !this.id.equals(type.id))){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
}
