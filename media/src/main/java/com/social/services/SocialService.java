package com.social.services;

import com.social.models.SocialUser;
import com.social.repositories.SocialUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SocialService {

    @Autowired
    private SocialUserRepository socialUserRepository;

    public List<SocialUser> getAllUsers() {
        return socialUserRepository.findAll();
    }

    public SocialUser saveUser(SocialUser socialUser) {
        return socialUserRepository.save(socialUser);
    }

    public SocialUser deleteUser(Long id) {
        SocialUser socialUser = socialUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        socialUserRepository.delete(socialUser);
        return socialUser;
    }
}
