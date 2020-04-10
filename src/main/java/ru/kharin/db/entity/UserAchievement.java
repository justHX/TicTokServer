package ru.kharin.db.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_achievement", schema = "public")
public class UserAchievement implements Serializable {

    @Id
    @Getter@Setter
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter@Setter
    @Column(name = "points")
    private String points;

    @Getter@Setter
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Users user;

    @Getter@Setter
    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Getter@Setter
    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Getter@Setter
    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    private Users updatedBy;

    @Getter@Setter
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = false)
    private Users createdBy;

    public UserAchievement() {
    }

    public UserAchievement(Long id) {
        this.id = id;
    }

    public UserAchievement(String points, Users user) {
        this.points = points;
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UserAchievement)) {
            return false;
        }
        UserAchievement type = (UserAchievement) obj;
        if ((this.id == null && type.id != null) || (this.id != null && !this.id.equals(type.id))) {
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
