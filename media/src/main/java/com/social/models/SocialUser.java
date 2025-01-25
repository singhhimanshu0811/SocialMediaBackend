package com.social.models;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Entity(name = "SocialUser")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @OneToOne(mappedBy = "socialUserProfile", cascade = CascadeType.ALL)
 // @OneToOne(mappedBy = "socialUserProfile", cascade = {CascadeType.ALL, CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER) or you cn have FetchType.LAZY
    // => multiple cascading example, see pdf for documentation
//    @JoinColumn(name="social_profile_id")
    private SocialProfile socialProfile;

    //now one user can have many posts, you represent them in a list
    @OneToMany(mappedBy = "socialUserPosts", cascade = CascadeType.REMOVE)
    private List<Posts>posts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),//in the entity where e are defining the relationship
            inverseJoinColumns = @JoinColumn(name = "group_id")//other entity
    )
    private Set<Group>groups = new HashSet<>();//we want unique groups

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
    //custom setter=>important for creating bidirectional relationship=>consistency across bidirectional relationship
    //not implemented in others as right now we are only querying user
    /*
    * Yes, for bidirectional relationships, it is a best practice to implement a custom setter or similar mechanism in
    * all three cases (One-to-One, One-to-Many/Many-to-One, and Many-to-Many) to ensure consistency.
    * */
    public void setSocialProfile(SocialProfile socialProfileObject) {
        socialProfileObject.setSocialUserProfile(this);
        this.socialProfile = socialProfileObject;

    }

}
