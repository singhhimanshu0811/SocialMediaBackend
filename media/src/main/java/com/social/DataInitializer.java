////file for data initializr, initialized as configuration. run if you want to do any housekeeping just after application is started
////the function is CommandLineRunner
package com.social;

import com.social.repositories.PostRepository;
import com.social.repositories.SocialProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.social.models.Posts;
import com.social.models.Group;
import com.social.models.SocialProfile;
import com.social.models.SocialUser;
import com.social.repositories.GroupRepsitory;
import com.social.repositories.SocialUserRepository;

@Configuration
public class DataInitializer {

    private final SocialUserRepository userRepository;
    private final GroupRepsitory groupRepository;
    private final SocialProfileRepository socialProfileRepository;
    private final PostRepository postRepository;

    public DataInitializer(SocialUserRepository userRepository, GroupRepsitory groupRepository, SocialProfileRepository socialProfileRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.socialProfileRepository = socialProfileRepository;
        this.postRepository = postRepository;
    }

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            // Create some users
            SocialUser user1 = new SocialUser();
            SocialUser user2 = new SocialUser();
            SocialUser user3 = new SocialUser();

            // Save users to the database
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            // Create some groups
            Group group1 = new Group();
            Group group2 = new Group();

            // Add users to groups
            group1.getUsers().add(user1);
            group1.getUsers().add(user2);

            group2.getUsers().add(user2);
            group2.getUsers().add(user3);

            // Save groups to the database
            groupRepository.save(group1);
            groupRepository.save(group2);

            // Associate users with groups
            user1.getGroups().add(group1);
            user2.getGroups().add(group1);
            user2.getGroups().add(group2);
            user3.getGroups().add(group2);

            // Save users back to database to update associations
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);


            // Create some posts
            Posts post1 = new Posts();
            Posts post2 = new Posts();
            Posts post3 = new Posts();

            // Associate posts with users
            post1.setSocialUserPosts(user1);
            post2.setSocialUserPosts(user2);
            post3.setSocialUserPosts(user3);

            // Save posts to the database (assuming you have a PostRepository)
            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);

            // Create some social profiles
            SocialProfile profile1 = new SocialProfile();
            SocialProfile profile2 = new SocialProfile();
            SocialProfile profile3 = new SocialProfile();

            // Associate profiles with users
            profile1.setSocialUser(user1);
            profile2.setSocialUser(user2);
            profile3.setSocialUser(user3);

            // Save profiles to the database (assuming you have a SocialProfileRepository)
            socialProfileRepository.save(profile1);
            socialProfileRepository.save(profile2);
            socialProfileRepository.save(profile3);
        };
    }
}
