# imp
### when any controller catches the exception in the argument in a funcction, in a file with @restcontrolleradvice, that function is automatically invoked

## points for above
1. now if you have used @Size or @not null, you will get internal server
2. after that if you put valid in controller, you get 404 bad request, no message
3. and if you follow the "imp" above, you put @restcontroller in class, put your defined exception in argument, write a method using bind, and if that error occurs, spring will automaticlly trigger this, now you will get 400 with message and status of your choice, for eg, you can see api exception is in argument, everytime it is thrown, that exception handler method is invoked

# imp
### since argument in controller is dto nd not model, your @size,@notnull annotation have to be in dto and not just model. basically try to keep your dto as same as possible