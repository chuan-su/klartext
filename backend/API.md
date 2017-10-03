# API.md #
> Klartext REST API Specification

  * [Account](#account-resources)
  * [Authentication](#authentication)
  * [Word](#word-resources
  )
  * [Post](
    #example-resources)
  
## Account Resources

User account resources - register account, view profile

  * [POST /api/users/register](#example-apiusersregister)
  
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

A security token grant users access to secured resources, such as create example, delete example.
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

## Word Resources
    
  * [GET /api/words/search](#get-apiwordssearch
  )
    
### GET /api/words/search

Incremental search words by its value and/or inflection to retrieve its interpretation.

**Request**

  * Header
    
        Accept: application/json
    
  * Query parameter
    
        query=lagt
  
  * Request example
    
        curl -i -H 'Accept: application/json' \
            -XGET 'http://localhost:8080/api/words/search?query=lagt'

**Response**:

    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    
    [
      {
        "id": "AV3idOfbWf0BzlOg0_xm",
        "created_at": null,
        "value": "lagt",
        "klass": null,
        "lang": "sv",
        "translation": ["placed"],
        "inflection": []
      },
      {
        "id": "AV3idOfbWf0BzlOg0_xR",
        "created_at": null,
        "value": "lagd",
        "klass": "adj",
        "lang": "sv",
        "translation": ["endowed","inclined"],
        "inflection": ["lagt","lagda"]
      }
    ]

## Post Resources
    
  * [GET /api/examples/search](#get-apipostssearch
  )
  * [GET /api/users/[user_id]/examples](#get-apiusersuser_idposts
  )
  * [POST /api/examples](#example-apiposts
  )
  * [PUT /api/examples/[post_id]](#put-apipostspost_id
  )
  * [DELETE /api/examples/[post_id]](#delete-apipostspost_id
  )
  * [POST /api/examples/[post_id]/likes](#example-apipostspost_idlikes
  )
  * [DELETE /api/examples/[post_id]/likes](#delete-apipostspost_idlikes
  )
  
### GET /api/examples/search

Search examples by word or/and word inflection

**Request**
  
  * Header
        
        Accept: application/json
  
  * Query parameter

        query=hamnade,hamnat

  * Request example
    
        $ curl -i -H 'Accept: application/json' \
            -XGET 'http://localhost:8080/api/examples/search?query=hamnade,hamnat'

**Response**
    
    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    
    [
      {
        "id": "1468",
        "created_at": "2017-08-14T20:30:21.000",
        "body": "hamna i bryderi",
        "interp": "be in a quandary",
        "total_likes": 0,
        "likes": null,
        "created_by": {
          "id": "1",
          "created_at": "2017-08-14T20:29:00.000",
          "name": "chuan"
        },
        "updated_at": "2017-08-14T20:30:21.000"
      },
      {
        "id": "3912",
        "created_at": "2017-08-14T20:31:24.000",
        "body": "hon hamnade i Paris",
        "interp": "she ended up in Paris",
        "total_likes": 0,
        "likes": null,
        "created_by": {
          "id": "1",
          "created_at": "2017-08-14T20:29:00.000",
          "name": "chuan"
        },
        "updated_at": "2017-08-14T20:31:24.000"
      }
    ]

### GET /api/users/[user_id]/examples

List user's examples with pagination support

**Request**
    
  * Header

        Accept: application/json
        
  * Query parameter
  
        page=0
        size=20
        sort=created_at,desc
        
  * Request example
    
        curl -i -H 'Content-Type: application/json' \
            -XGET 'http://localhost:8080/api/users/1/examples?page=1&size=10&sort=createdAt,desc'
    
 **Response**:
 
        HTTP/1.1 200 
        Content-Type: application/json;charset=UTF-8

        {
          "content": [
            {
              "id": "12510",
              "created_at": "2017-08-14T18:34:56.000",
              "updated_at": "2017-08-14T18:34:56.000"
              "body": "politisk övertygelse",
              "interp": "political conviction",
              "total_likes": 0,
              "likes": [],
              "created_by": {
                "id": "1",
                "created_at": "2017-08-14T18:29:00.000",
                "name": "chuan"
              }
            },
            {
              "id": "12511",
              "created_at": "2017-08-14T18:34:56.000",
              "updated_at": "2017-08-14T18:34:56.000"
              "body": "övervaka att bestämmelserna följs",
              "interp": "see to it that the rules are obeyed",
              "total_likes": 0,
              "likes": [],
              "created_by": {
                "id": "1",
                "created_at": "2017-08-14T18:29:00.000",
                "name": "chuan"
              },
            }
          ],
          "totalPages": 1255,
          "totalElements": 12546,
          "last": false,
          "sort": [
            {
              "direction": "DESC",
              "property": "createdAt",
              "ignoreCase": false,
              "nullHandling": "NATIVE",
              "descending": true,
              "ascending": false
            }
          ],
          "first": false,
          "numberOfElements": 10,
          "size": 10,
          "number": 1
        }

### POST /api/examples

Create a example. Security Token is required.

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
            -XPOST 'http://localhost:8080/api/users/1/examples' \
            -d '{
                "body": "Hon kommer från Göterborg och där växte hon upp med sina systrar.",
                "interp": "She is from Gothenburg and growed up with her systers there"
            }' 
                
**Response**

    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    
    {
      "id": "23",
      "body": "Hon kommer från Göterborg och där växte hon upp med sina systrar.",
      "interp": "She is from Gothenburg and growed up with her systers there",
      "total_likes": 0,
      "likes": [],
      "created_by": {
        "id": "1",
        "created_at": "2017-08-14T18:29:00.000",
        "name": "chuan"
      },
      "created_at": "2017-08-14T18:34:56.000",
      "updated_at": "2017-08-14T18:34:56.000"
    }
    
### PUT /api/examples/[post_id]

Update a example. Security Token is required

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
            -XPUT 'http://localhost:8080/api/users/1/examples/23' \
            -d '{
                "body": "Hon kommer från Göterborg och där växte hon upp med sina systrar.",
                "interp": "She is from Gothenburg and growed up with her systers there"
            }' 
                
**Response**

    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    
    {
      "id": "23",
      "body": "Hon kommer från Göterborg och där växte hon upp med sina systrar.",
      "interp": "She is from Gothenburg and growed up with her systers there"
      "total_likes": 0,
      "likes": [],
      "created_by": {
        "id": "1",
        "created_at": "2017-08-14T18:29:00.000",
        "name": "chuan"
      },
      "created_at": "2017-08-14T18:34:56.000",
      "updated_at": "2017-08-14T18:34:56.000"
    }

### DELETE /api/examples/[post_id]

**Request**
    
  * Header
  
        Accept: application/json
        X-Auth-Token: hWNKZjTGsSXRndryZIKqZIxeZPE
        
  * Request example
      
        curl -i -H 'Accept: application/json' \
            -H 'X-Auth-Token: hWNKZjTGsSXRndryZIKqZIxeZPE' \
            -XDELETE 'http://localhost:8080/api/users/1/examples/24'
                
**Response**

return the deleted example

    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    
    {
      "id": "23",
      "body":"Hon kommer från Göterborg och där växte hon upp med sina tre systrar.",
      "interp":"She is from Gothenburg and growed up with her three systers there",
      "total_likes": 0,
      "likes": [],
      "created_by": {
        "id": "1",
        "created_at": "2017-08-14T18:29:00.000",
        "name": "chuan"
      },
      "created_at": "2017-08-14T18:34:56.000",
      "updated_at": "2017-08-14T18:34:56.000"
    }
    
### POST /api/examples/[post_id]/likes

**Request**
    
  * Header
  
        Accept: application/json
        X-Auth-Token: hWNKZjTGsSXRndryZIKqZIxeZPE
        
  * Request example
      
        curl -i -H 'X-Auth-Token: MmEMYrgywwVZJufpe858PYCqI0c'
            -H 'Accept: application/json' 
            -XPOST 'http://localhost:8080/api/examples/12541/likes'
                
**Response**

return the updated example

    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    
    {
      "id": "12541",
      "body": "Olof bor i studentlägenhet.",
      "interp": null,
      "total_likes": 1,
      "likes": [
        {
          "id": "2",
          "created_at": "2017-08-14T21:13:28.368",
          "user": {
            "id": "2",
            "created_at": "2017-08-14T20:38:44.000",
            "name": "my fullname"
          }
        }
      ],
      "created_by": {
        "id": "1",
        "created_at": "2017-08-14T18:29:00.000",
        "name": "chuan"
      },
      "created_at": "2017-08-14T18:34:56.000",
      "updated_at": "2017-08-14T18:34:56.000"
    }
    
### DELETE /api/examples/[post_id]/likes

**Request**
    
  * Header
  
        Accept: application/json
        X-Auth-Token: hWNKZjTGsSXRndryZIKqZIxeZPE
        
  * Request example
      
        curl -i -H 'X-Auth-Token: MmEMYrgywwVZJufpe858PYCqI0c'
            -H 'Accept: application/json' 
            -XDELETE 'http://localhost:8080/api/examples/12541/likes'
                
**Response**

return the updated example

    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    
    {
      "id": "12541",
      "body": "Olof bor i studentlägenhet.",
      "interp": null,
      "total_likes": 0,
      "likes": [],
      "created_by": {
        "id": "1",
        "created_at": "2017-08-14T18:29:00.000",
        "name": "chuan"
      },
      "created_at": "2017-08-14T18:34:56.000",
      "updated_at": "2017-08-14T18:34:56.000"
    }
