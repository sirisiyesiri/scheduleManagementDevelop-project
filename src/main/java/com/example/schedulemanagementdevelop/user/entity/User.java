package com.example.schedulemanagementdevelop.user.entity;

import com.example.schedulemanagementdevelop.schedule.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

// delete() 호출 시, 실제 DELETE 대신 is_deleted = true로 업데이트하여 soft delete 수행
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")

// 조회 시 is_deleted = false 조건을 자동으로 추가하여 soft delete된 데이터 제외
@SQLRestriction("is_deleted = false")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_deleted")    // isDeleted 필드와 테이블의 is_deleted 매핑
    private boolean isDeleted = false;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public void modifyInfo(String userName, String email) {
        if(userName != null) {
            this.userName = userName;
        }
        if(email != null) {
            this.email = email;
        }
    }
}
