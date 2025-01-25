you get a token back also, because we need to send token along with all request further on in the format, in the body
Authorization: Bearer <jwtToken>
so first you'll do signin, you'll get token in json, in that you have jwt,. then for any admin, you send admin: bearer <jwt> in body

but in cookie based jwt you wouldnt need to do that

instead of jwt it will generate cookie, 
and then instead of taking jwt from header, we will extract it from cookie