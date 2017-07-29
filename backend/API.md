# API.md #
> Klartext REST API Specification

#### Word/vocabulary full text search ####
    
    GET /api/search/word?query=lagt HTTP/1.1
    Accept: application/json

    curl -i -H 'Accept: application/json' -XGET 'http://localhost:8080/api/search/word?query=lagt'

Example of a success response:

    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8

    [
      {
        "klass": "verb",
        "translation": ["put","put the book on the table!"],
        "lang": "sv",
        "value": "lägga",
        "inflection": ["lade","lagt","lägg","lägga","lägger"]
      }
    ]

#### Posts search ####

    GET /api/search/post?query=hamnade,hamnat
    Accept: application/json
    
    $ curl -i -H 'Accept: application/json' -XGET 'http://localhost:8080/api/search/post?query=hamnade,hamnat' 

Example of a success response:
    
    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    
    [
      {
        "body": "Imorgon, lördag, är det final i musiktävlingen Eurovision.Jag trycker inte att Frans är något bra, men han kan hamna bland topp 10 i alla fall."
        "interp": "",
        "created_at": "2017-06-25T18:25:33",
        "updated_at": "2017-06-25T18:25:33",
      }
    ]

#### Create a post ####
    
    POST /api/users/1/posts
    Content-Type: application/json; charset=UTF-8
    
    curl -i -H 'Content-Type: application/json; charset=utf-8' -XPOST 'http://localhost:8080/api/users/1/posts' -d '{
       "body": "Hon kommer från Göterborg och där växte hon upp med sina tre systrar.",
       "interp": "She is from Gothenburg and growed up with her three systers there"
    }
    '
Example of a success response:

    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    
    {
      "id":62,
      "body":"Hon kommer från Göterborg och där växte hon upp med sina tre systrar.",
      "interp":"She is from Gothenburg and growed up with her three systers there",
      "createdAt":"2017-06-25T22:48:03.203",
      "updatedAt":"2017-06-25T22:48:03.203"
    }
    
#### Update a post ####

    PUT /api/users/1/posts/23
    Content-Type: application/json; charset=UTF-8
    
    curl -i -H 'Content-Type: application/json; charset=utf-8' -XPOST 'http://localhost:8080/api/users/1/posts' -d '{
       "body": "Hon kommer från Göterborg och där växte hon upp med sina tre systrar.",
       "interp": "She is from Gothenburg and growed up with her three systers there"
    }
    '
Example of a success response:

    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    
    {
      "id":23,
      "body":"Hon kommer från Göterborg och där växte hon upp med sina tre systrar.",
      "interp":"She is from Gothenburg and growed up with her three systers there",
      "createdAt":"2017-06-25T22:48:03.203",
      "updatedAt":"2017-07-30T19:23:04.204"
    }

#### Delete a post ####

    DELETE /api/users/1/posts/24
    
    curl -i  -XDELETE 'http://localhost:8080/api/users/1/posts/24' 
    
Example of a success response:

    HTTP/1.1 200 
    
    {
      "id": 24,
      "body": "känna sig accepterad",
      "interp": "feel oneself accepted",
      "createdAt": "2017-07-13T10:14:09",
      "updatedAt": "2017-07-13T10:14:09"
   }
    
#### List posts created by user ####
    
    GET http://localhost:8080/api/users/1/posts?page=1&size=20&sort=createdAt,desc
    
    curl -i -H 'Content-Type: application/json' -XGET 'http://localhost:8080/api/users/1/posts?page=1&size=10&sort=createdAt,desc' 
    
Example of a success response:

    {
      "content": [{
        "body": "lägga fram överväldigande bevis",
        "interp": "produce overwhelming evidence",
        "id": 12522,
        "createdAt": "2017-07-13T10:19:27",
        "updatedAt": "2017-07-13T10:19:27"
      }],
      "totalPages": 1255,
      "totalElements": 12546,
      "last": false,
      "sort": [{
        "direction": "DESC",
        "property": "createdAt",
        "ignoreCase": false,
        "nullHandling": "NATIVE",
        "descending": true,
        "ascending": false
      }],
      "first": false,
      "numberOfElements": 10,
      "size": 10,
      "number": 1
    }
