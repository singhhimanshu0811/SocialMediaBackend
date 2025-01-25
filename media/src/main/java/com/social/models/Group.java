package com.social.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "og_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long group_id;

    @ManyToMany(mappedBy = "groups")
    @JsonIgnore

    private Set<SocialUser>users = new HashSet<SocialUser>();

    @Override
    public int hashCode() {
        return Objects.hash(group_id);
    }
}