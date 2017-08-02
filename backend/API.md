# API.md #
> Klartext REST API Specification

  * [Search](#search)
  * [Account](#account-resources)
  * [Authentication](#authentication)
  * [Post](#post-resources)
  
## Search

  * [GET /api/search/word](#get-apisearchword)
  * [GET /api/search/post](#get-apisearchpost)
  
### GET /api/search/word

Incremental search words by its value and/or inflection to retrieve its interpretation.

**Request**

  * Header
    
        Accept: application/json
    
  * Query parameter
    
        query=lägga,lade,lagt
  
  * Request example
    
        curl -i -H 'Accept: application/json' \
            -XGET 'http://localhost:8080/api/search/word?query=lagt'

**Response**:

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

### GET /api/search/post

Search posts by word or/and word inflection

**Request**
  
  * Header
        
        Accept: application/json
  
  * Query parameter

        query=hamnade,hamnat

  * Request example
    
        $ curl -i -H 'Accept: application/json' \
            -XGET 'http://localhost:8080/api/search/post?query=hamnade,hamnat' 

**Response**
    
    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    
    [
      {
        "body": "Imorgon, lördag, är det final i musiktävlingen Eurovision."
        "interp": "",
        "created_at": "2017-06-25T18:25:33",
        "updated_at": "2017-06-25T18:25:33",
      }
    ]

## Account Resources

User account resources - register account, view profile

  * [POST /api/users/register](#post-apiusersregister)
  
### POST /api/users/register

Register a new user account

**Request**
    
  * Header
  
        Content-Type: application/json; charset=UTF-8
        Accept: application/json
  
  * Request body
    
        {
          "email": "user@gmail.com",
          "name" : "full name",
          "password": "********"
        }
        
  * Request example

        curl -i -H 'Accept: application/json' \
            -H 'Content-Type: application/json; charset=utf-8' \
            -XPOST 'http://localhost:8080/api/users/register' \
            -d '{
                "email": "user@gmail.com",
                "name" : "my fullname",
                "password": "mycredentials321"
            }'

**Response**
    
    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    
    {
      "name": "my fullname",
      "email": "user@gmail.com",
      "id": 4,
      "createdAt": "2017-08-02T12:26:16.841",
      "updatedAt": "2017-08-02T12:26:16.841"
    }

## Authentication

In order to access secured resources, a security token is required to be obtained.

A security token grant users access to secured resources, such as create post, delete post. 
And it is required to be set on `HTTP Request Header` when accessing secured resources.

  * [PUT /api/users/auth](#put-apiusersauth)

### PUT /api/users/auth

Obtaining Security Token. 

**Request**

  * Header
  
        Content-Type: application/json; charset=UTF-8
        Accept: application/json
  
  * Request body
    
        {
          "email": "user@gmail.com",
          "password": "********"
        }
        
  * Request example

        curl -i -H 'Accept: application/json' \
            -H 'Content-Type: application/json; charset=utf-8' \
            -XPUT 'http://localhost:8080/api/users/auth' \
            -d '{
                "email": "user@gmail.com",
                "password": "mycredentials321"
            }'
                
**Response**

    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    
    {
      "id": 2,
      "token": "19-kbbjyCoTVYLCSYG2CHcWiTAQ",
      "expiresAt": "2017-09-01T17:48:54",
      "createdAt": "2017-08-01T17:48:54",
      "updatedAt": "2017-08-02T12:30:59.235",
      "user": {
        "name": "my fullname",
        "email": "user@gmail.com",
        "id": 4,
        "createdAt": "2017-08-01T17:48:21",
        "updatedAt": "2017-08-01T17:48:21"
      }
    }

## Post Resources

  * [GET /api/users/[user_id]/posts](#get-apiusersuser_idposts)
  * [POST /api/users/[user_id]/posts](#post-apiusersuser_idposts)
  * [PUT /api/users/[user_id]/posts/[post_id]](#put-apiusersuser_idpostspost_id)
  * [DELETE /api/users/[user_id]/posts/[post_id]](#delete-apiusersuser_idpostspost_id)

### GET /api/users/[user_id]/posts

List user's posts with pagination support

**Request**
    
  * Header

        Accept: application/json
        
  * Query parameter
  
        page=0
        size=20
        sort=created_at,desc
        
  * Request example
    
        curl -i -H 'Content-Type: application/json' \
            -XGET 'http://localhost:8080/api/users/1/posts?page=1&size=10&sort=createdAt,desc'
    
 **Response**:
 
        HTTP/1.1 200 
        Content-Type: application/json;charset=UTF-8

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

### POST /api/users/[user_id]/posts

Create a post. Security Token is required.

**Request**
    
  * Header
  
        Content-Type: application/json; charset=UTF-8
        Accept: application/json
        X-Auth-Token: hWNKZjTGsSXRndryZIKqZIxeZPE
  
  * Request body
    
        {
          "body": "Hon kommer från Göterborg och där växte hon upp med sina systrar.",
          "interp": "She is from Gothenburg and growed up with her systers there"
        }
        
  * Request example
      
        curl -i -H 'X-Auth-Token: hWNKZjTGsSXRndryZIKqZIxeZPE' \
            -H 'Accept: application/json' \
            -H 'Content-Type: application/json; charset=utf-8' \
            -XPOST 'http://localhost:8080/api/users/1/posts' \
            -d '{
                "body": "Hon kommer från Göterborg och där växte hon upp med sina systrar.",
                "interp": "She is from Gothenburg and growed up with her systers there"
            }' 
                
**Response**

    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    
    {
      "id":62,
      "body":"Hon kommer från Göterborg och där växte hon upp med sina tre systrar.",
      "interp":"She is from Gothenburg and growed up with her three systers there",
      "createdAt":"2017-06-25T22:48:03.203",
      "updatedAt":"2017-06-25T22:48:03.203"
    }
    
### PUT /api/users/[user_id]/posts/[post_id]

Update a post. Security Token is required

**Request**
    
  * Header
  
        Content-Type: application/json; charset=UTF-8
        Accept: application/json
        X-Auth-Token: hWNKZjTGsSXRndryZIKqZIxeZPE
  
  * Request body
    
        {
          "body": "Hon kommer från Göterborg och där växte hon upp med sina systrar.",
          "interp": "She is from Gothenburg and growed up with her systers there"
        }
    
  * Request example
      
        curl -i -H 'X-Auth-Token: hWNKZjTGsSXRndryZIKqZIxeZPE' \
            -H 'Accept: application/json' \
            -H 'Content-Type: application/json; charset=utf-8' \
            -XPUT 'http://localhost:8080/api/users/1/posts/23' \
            -d '{
                "body": "Hon kommer från Göterborg och där växte hon upp med sina systrar.",
                "interp": "She is from Gothenburg and growed up with her systers there"
            }' 
                
**Response**

    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    
    {
      "id":23,
      "body":"Hon kommer från Göterborg och där växte hon upp med sina tre systrar.",
      "interp":"She is from Gothenburg and growed up with her three systers there",
      "createdAt":"2017-06-25T22:48:03.203",
      "updatedAt":"2017-07-30T19:23:04.204"
    }

### DELETE /api/users/[user_id]/posts/[post_id]

**Request**
    
  * Header
  
        Accept: application/json
        X-Auth-Token: hWNKZjTGsSXRndryZIKqZIxeZPE
        
  * Request example
      
        curl -i -H 'Accept: application/json' \
            -H 'X-Auth-Token: hWNKZjTGsSXRndryZIKqZIxeZPE' \
            -XDELETE 'http://localhost:8080/api/users/1/posts/24' 
                
**Response**

return the deleted object

    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    
    {
      "id": 24,
      "body": "känna sig accepterad",
      "interp": "feel oneself accepted",
      "createdAt": "2017-07-13T10:14:09",
      "updatedAt": "2017-07-13T10:14:09"
    }
    
