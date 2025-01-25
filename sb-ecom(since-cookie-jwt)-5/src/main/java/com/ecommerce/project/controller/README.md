1. rather than generating token from username, we generate cookie from username
2. we send cookie in a header of response, rather than with the response body itself.
3. //UserInfoResponse response = new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), roles, jwtCookie.toString());
   //if you want to just print cookie in json format, you can just pass it like token and in user response you can enable jwt token field, and you'll get cookie in json
4. //When the server responds to a request and includes a Set-Cookie header, the browser processes the cookie according to its attributes:
   //
   //HTTP/1.1 200 OK
   //Set-Cookie: jwt=abc123; Path=/api; Max-Age=86400; HttpOnly; Secure
   //
   //    Cookie Attributes:
   //        Path: The cookie will be sent only with requests to URLs that match or are under this path (e.g., /api).
   //        Max-Age or Expires: The lifespan of the cookie.
   //        HttpOnly: If present, the cookie is not accessible to JavaScript.
   //        Secure: If present, the cookie is sent only over HTTPS.

   //Once the browser stores the cookie, it automatically includes the cookie in the Cookie header for all subsequent
   // HTTP requests to the same domain and path that match the cookie's attributes.