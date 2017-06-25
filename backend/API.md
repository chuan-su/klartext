# API.md #
> Klartext REST API Specification

## Search Resource ##

### Word/Vocabulary full text search ###
    
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

### Posts search ###

    GET http://localhost:8080/api/search/post?query=hamnade,hamnat
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

## Post resource CRUD ##

### Create a post ###

### Update a post ###

### Delete a post ###

### List posts created by user ###

