# README for security config file

1. makes this class chie config / overrides over other config
2. //the following schema of code is pasted from -> press shift 2 times on any window, which takes you to source file->search springbootwebsecurityconfiguration.java file. read the documentation there
3. based on h2 config we have done, springboot alreaady has a datasource bean ready for us
4. form and basic authentication
5. form and basic authentication
6. basically you ont be prompted when you type h2-console end point
7. upper line for making the authentication stateless. so you wont find cookies in headers of network in inspect element
8. when you connect to h2-console with the above exceptions only frame is shown. this line is there to include all contents of ui in frames
9. for exceptions in h2 to work. we want csrf disables so that we may give preferential treatment to some, like /h2-console/
10. part of spring framework. manages users in memory->non persistent
11. without password encryption
12. with encryption=>    you'll see password encrypted in database h2 now
13. see because e are not using @entity here to create table, we have to explicitly create some tables, schema to run this h2. we have done that in schema.sql file in resources section.
    //so everytime the application starts, they need to run.
    //these commands from spring boot github homepage, search users.ddl there. you'll notice the path of this file is very same to the package name of UserDetails class!!
    //you'll then notice in h2 two table, authorities and users, automatically created,as tht sql file is run
    //and in the user table, you can find your user and admin which you created above