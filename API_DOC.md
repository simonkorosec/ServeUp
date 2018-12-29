# Serve Up API Documentation

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
---
### Restaurants for city

- `restaurant/home`
- request type: POST
- Retrive all restaurants in given city
- API call should recieve a JSON with city name
- API returns a JSON array of restaurants and their data
- If the API call is not properly formated return error status with description
- This inconsistency between API calls is the blame of Urban Jagodic
- Status
  * 0 - Error

###### Input data:  
```JSON
{
    "location": "City name"
}
```
###### Return value:
```JSON
[
  {
    "id_restavracija": x,
    "ime_restavracije": "name",
    "ocena": x,
    "tip": "x",
    "ulica": "x",
    "hisna_stevilka": x,
    "postna_stevilka": "x",
    "kraj": "x",
    "slika": "base64 encoded restaurant image"
  }
]

```
---
### Orders for user

- `user/get_orders`
- request type: POST
- Retrive all orders and meal data for given user
- API call should recieve a JSON with the user id and an optional field 'num_orders'
- Field 'num_orders' specifies how many orders are to be returned, default value is 10
- API returns a JSON with the status and a short description, if status is OK also return list of orders
- Each order also has an array of its meals
- Order status:
  	* 0 - New Order
	* 1 - Order ready
	* 2 - Order finished
- Status
	* 0 - Error
  	* 1 - OK 

###### Input data:  

```JSON
{
	"id_uporabnik": "user id",
	"num_orders": 10
}
```
###### Return value:
```JSON
{
    "status": 1,
    "description": "Orders for user: id",
    "orders": [
        {
            "id_narocila": x,
            "cas_prevzema": "2018-12-14T18:31:25Z",
            "cas_narocila": "2018-12-14T18:31:27Z",
            "ime_restavracije": "x",
            "cena": 5,
	    "status": 0,
            "jedi": [
                {
		    "id_jed": 0,
                    "ime_jedi": "x",
                    "cena": 2,
                    "opis_jedi": "x",
		    "kolicina": 1
                },
                {
		    "id_jed": 0,
                    "ime_jedi": "x",
                    "cena": 3,
                    "opis_jedi": "x",
		    "kolicina": 2
                },
            ]
        }
    ]
}
```
---
### All orders for restaurant

- `orders/?id_restavracija=id`
- request type: GET
- Retrieve all active orders for the specified restaurant
- Order is active if its status is 'Nova Naročila', 'V Pripravi', 'Pripravljeno'
- Returnes a status indicator and a list of all orders
- Status:
  * 0 - Error
  * 1 - Ok
  
###### Return value:
```JSON
{
     "status": 1,
     "data": [
    	{
            "cas_prevzema": "2018-12-22T14:34:00Z",
            "cas_narocila": "2018-12-21T14:34:00Z",
            "id_restavracija": 6,
            "id_uporabnik": 56756,
            "cena": 11.8,
            "id_narocila": 17,
            "status": 0,
	    "checked_in" : true
            "jedi": [
                {
                    "id_jed": 1,
                    "ime_jedi": "Test",
                    "kolicina": 2,
                    "cena": 5.0
                },
                {
                    "id_jed": 3,
                    "ime_jedi": "Jed 3",
                    "kolicina": 3,
                    "cena": 6.8
                }
            ]
        }
    ]
}
```
---
### Refresh orders for restaurant

- `orders/refresh/?id_restavracija=id`
- request type: GET
- Retrive all new and cancelled orders for the specified restaurant since tha last call of this API
- Returnes a status indicator and two lists
- Status:
  * 0 - Error
  * 1 - Ok
  
###### Return value:
```JSON
{
    "status": 1,
    "new_orders": [
        {
            "cas_prevzema": "2018-12-22T14:34:00",
            "cas_narocila": "2018-12-21T14:34:00",
            "id_restavracija": 6,
            "id_uporabnik": "56756",
	    "cena": 30.4,
	    "id_narocila": 23,
	    "status": 0,
	    "checked_in" : true,
            "jedi": [
                {
                    "id_jed": 1,
		    "ime_jedi": "Jed 1",
                    "kolicina": 2,
                    "cena": 5
                },
                {
                    "id_jed": 3,
		    "ime_jedi": "Jed 2",
                    "kolicina": 3,
                    "cena": 6.8
                }
            ],
            
        }
    ],
    "cancelled_orders": [16, 17]
}
```
---
### New order by user

- `orders/new_order/`
- request type: POST
- Create new order for the current user
- Input fields 'ime_jedi', 'cena' should be the same as the ones you get from the database, this is so we reduce the number of queries
- Status:
  * 0 - Error
  * 1 - Ok

###### Input data:
```JSON
{
	"cas_prevzema": "2018-12-22T14:34:00",
	"cas_narocila": "2018-12-21T14:34:00",
	"id_restavracija": 6,
	"id_uporabnik": "56756",
	"jedi": [
	 {
           "id_jed": 1,
           "ime_jedi": "Jed 1",
           "kolicina": 2,
           "cena": 5
	 },
	 {
           "id_jed": 3,
           "ime_jedi": "Jed 2",
           "kolicina": 3,
           "cena": 6.8
	 }
	]
}
```
---
### Cancel order by user

- `orders/cancel_order/`
- request type: POST
- Cancel an order
- API call should recieve the order id
- Status:
  * 0 - Error
  * 1 - Ok

###### Input data:
```JSON
{
	"id_narocilo": 18
}
```
---
### Create new meal in restaurant

- `meals/new_meal/`
- request type: POST
- Status:
  * 0 - Error
  * 1 - Ok
  
###### Input data:
```JSON
{
    "ime_jedi": "Jed 1",
    "cena": 3,
    "opis_jedi": "To je jed 1",
    "id_jedilni_list": 2,
    "id_restavracija": 6
}
```
---
### Get meals for restaurant

- `meals/?id_restavracija=id`
- request type: Get
- Return every meal in requested restaurant, grouped by their meal type
- Status:
  * 0 - Error

###### Return data:
```JSON
{
    "meal_type": [
        {
	    "id_jed": 1,
            "ime_jedi": "ime jedi",
            "opis_jedi": "opis jedi",
            "cena": 1.0
        }
    ],
    "Juhe": [
        {
	    "id_jed": 1,
            "ime_jedi": "Test",
            "opis_jedi": "Testna jed",
            "cena": 5.0,
	    "kolicina": 1
        },
        {
   	    "id_jed": 2,
            "ime_jedi": "Test Jed",
            "opis_jedi": "Testna jed 1",
            "cena": 5.8,
	    "kolicina": 1
        }
    ]
}
```
