package com.social.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "SocialProfile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profile_id;

    @OneToOne //=> mapped by must have the value which is the name of the object in the class social user which maps to this class
    @JoinColumn(name="social_user")
    @JsonIgnore//it ignores the output in json output, so you'll not have cyclic dependency. its work is to ignore output of this field in jso, i.e jso wont have this field from this class
    private SocialUser socialUserProfile;
    private String description;
    //this is bidirectional as both of them know about each other as both of them have one to one mapping.
    //basically social user is the owner class, this is the non-owning side

    public void setSocialUser(SocialUser socialUserObject){
        //socialUserProfile.setSocialProfile(this);=> this will not work as socialprofile might be null
        this.socialUserProfile = socialUserObject;  //social profile -> social user ka reltion ban gaya
        if (socialUserObject.getSocialProfile() != this) // to prevent infinite loop
            socialUserObject.setSocialProfile(this);
    }

}
