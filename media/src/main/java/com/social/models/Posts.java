package com.social.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.social.models.SocialUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long post_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private SocialUser socialUserPosts;
}
