# imp

## one to many / many to one

1. see in user->OtM
2. in posts->MtO
3. now if you don't write mappedBy in posts, which specifies that post will be the owner table, as it owns relationship, as it will have user id as its field as fireogn id from user, then you will get third table created, which will be join of posts and users.
4. you can remove this third table by putting mappedBy in posts.
5. also please see that you cannot use mapped by and join table in many to many relationship in the same table
6. @JoinColumn annotation is used to specify the foreign key column in a table for a @OneToOne or @ManyToOne relationship.
7. @JoinTable annotation is used to define the join table for a many-to-many relationship or an unidirectional one-to-many relationship.
8. we don't create new table in one to many or many to one relationship as we might just query it, otherwise you could query it
9. for many to many, you can see we have created a new group user_group
10. @JsonIgnore annotation is used in bidirectional relation to ensure that in get operation we do not go in infinite recursion. for example user->profile->user.....
11. also in many to many entity, we have to implement hashCode of our own, as we want list and set, otherwise they go into infinite recursion
12. file for data initializr, initialized as configuration. run if you want to do any housekeeping just after application is started the function is CommandLineRunner
13. now for post, we need cascading. becuase say if we want to post a user, we want profile first, passing just this json
    {
    "userId": 1,
    "socialProfile": {
    "id": 1
    }
    }
    gives error, as socialProfile is not instantiated. so we apply cascading. now there are different types of cascading, but it basically means, in all context, if something happens to parent, it happens to child also

14. direction of cascade depends on the file in which it is writeen. hence if cascade is written in the user and not in profile, changes will be propagated from user to profile but not from profile to user
