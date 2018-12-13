## How to submit an issue
 1. Delete all **Issue** sections except for the one you want to submit
 2. Fill out the missing data.
 3. Delete this **How to** section

***

## Issue: New API call
### Name of new API feature
  - App/Website: Page on the mockup
  - Request type: ... (POST, GET, PUT, DELETE)
  - API description
  - Input data description (optional)
  - Return data description (optional)
  - Status: 
    * 0 - Error
    * 1 - OK
    * [Add if more statuses are needed]

###### Input data:
```JSON
{
    "tmp_key": "tmp_value"
}
```
###### Example Input:
```JSON
{
    "location": "Ljubljana"
}
```

###### Returned value:
```JSON
{
    "status": 0,
    "description": "Error ...",
    "if_needed_key": "if_needed_value"
}
```
###### Example Return:
```JSON
{
    "restaurant_id":"1",
    "restaurant_name": "Foculus",
    "restaurant_type": "Picerija",
    "restaurant_image": "01010101010101001010101010....",
    "restaurant_rating": "4.5",
}
```
***

## Issue: Bug report
### Name of the bugged API feature
  - App/Website: Page on the mockup
  - Request type: ... (POST, GET, PUT, DELETE)
  - Expected output: What should the API return 

###### Input data:
```JSON
{
    "tmp_key": "tmp_value"
}
```
###### Example Input:
```JSON
{
    "location": "Ljubljana"
}
```
###### Returned value:
```JSON
{
    "status": 0,
    "description": "Error ...",
    "if_needed_key": "if_needed_value"
}
```
###### Example Return:
```JSON
{
    "restaurant_id":"1",
    "restaurant_name": "Foculus",
    "restaurant_type": "Picerija",
    "restaurant_image": "01010101010101001010101010....",
    "restaurant_rating": "4.5",
}
```
