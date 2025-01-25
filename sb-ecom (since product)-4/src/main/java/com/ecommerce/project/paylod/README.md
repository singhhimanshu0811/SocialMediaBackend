# imp
1. jackson library is responsible for serialization/deserialization from/to response to/from json
2. our response is in json. see for pagination, we dont want stuff like page no and all as part of database, but we want them part of resposne to frontend. basically, we want decouple our backend response to the way we show it to front end user.
there can be other consideration too, like you want password to be some form of encryption. so you use custom response for pagination, and in general also. 
3. basically **de-couple presentation layer from model**, because you dont want to change your model, as if you change model, you'll have to change database.
4. the above stuff is done using dtos. data transfer objects - tailor the data- hide any details, params and so on
5. so, dtos become a medium to carry requests and response
6. **categorydto has dto for request**
7. **categoryresponse has dto for response**
8. see that in categorrycontroller.java and impl in services, we need to transform return type, for gte, from List<category>, to an object of categoryresponse, which has list<categorydto> as its only member. to do it, e use model mapper