# Serve Up API Documentation
Base URL: `https://serveup-backend.herokuapp.com/api/`

### Admin user registration  
  - `admin_user/register/`
  - Request type: POST
  - Register a new admin user
  - The API call should recive a JSON with an email address and a password
  - The API returnes a JSON with the status and a short description
  - Status: 
    * 0 - Error
    * 1 - OK
###### Input data:
```JSON
{
    "email": "random.admin@mail.com",
    "password": "password_to_be_hashed"
}
```
###### Returned value:
```JSON
{
    "status": 0,
    "description": "Error ..."
}
```
---
### Admin user login
  - `admin_user/login/`
  - Request type: POST
  - Check if the credentials match a user in the database
  - The API call should recive a JSON with an email address and a password
  - The API returnes a JSON with the status and a short description
  - If status is 1 the JSON also has a 'id_restavracija' which is the id of the restaurant or None
  - Status: 
    * 0 - Error (incorect mail / password)
    * 1 - OK 
###### Input data:
```JSON
{
    "email": "random.admin@mail.com",
    "password": "password_to_be_hashed"
}
```
###### Returned value:
```JSON
{
    "status": 0,
    "description": "Error ..."
}
```
---
### Get restaurant types
  - `restaurant_type/`
  - Request type: GET
  - Get a list of restaurant types
  - The API returnes a JSON array of every restaurant type in the database
###### Returned value:
```JSON
[
    {
        "id_tip_restavracije": 1,
        "tip": "Mehiška"
    },
    {
        "id_tip_restavracije": 2,
        "tip": "Kitajska"
    }, ...
]
```
---
### Restaurant registration
- `restaurant/register`
- request type: POST
- Register a new restaurant
- API call should recieve a JSON with the email of the admin, name of the restaurant, type, address and rating
- API returns a JSON with the status, short description and any other actions that were executed
- Status
  * 0 - Error
  * 1 - OK

###### Input data:  

```JSON
{
    "email": "admin@email.com",
    "ime_restavracije": "Cantina Mexicana",
    "id_tip_restavracije": "1",
    "naslov": "Celovška 66, 1000 Ljubljana",
    "ocena" : "5"
}
```
###### Return value:
```JSON
{
    "status" : 0,
    "description": "Error ...",
    "additional actions": "Updated address ..."
}
```
---
### User registration

- `user/register`
- request type: POST
- Register a new user
- API call should recieve a JSON with the token of the user
- API returns a JSON with the status and a short description
- Status
  * 0 - Error
  * 1 - OK - New User
  * 2 - OK - Existing User

###### Input data:  

```JSON
{
    "id_uporabnik": "12342"
}
```
###### Return value:
```JSON
{
    "status" : 0,
    "description": "Error ..."
}
```
