package com.example.FirstExample;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
//passing something in body in post!!, has to be tested using postman, saved there, go ans look
    @PostMapping("/helloP")
    public String helloPost(@RequestBody String name){//from post request
        return "Hello!" + name + "!";
    }

//now for json respnse on postman

    //when you return object, conversion in json happens automatically

    @GetMapping("/helloGetJson")
    public HelloResponse helloResponse(){
        return new HelloResponse("Hello World");
    }

    @PostMapping("/helloPostJson")
    public HelloResponse helloPostJson(@RequestBody String name){
        return new HelloResponse("Hello!" + name + "!");
    }

    //path variable, giving variable in path
    @GetMapping("/hello/{name}")
    public HelloResponse helloPath(@PathVariable String name){
        return new HelloResponse("Hello!" + name);
    }
}
