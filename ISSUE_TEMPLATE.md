## How to submit an issue
 1. Delete all **Issue** sections except for the one you want to submit
 2. Fill out the missing data.
 3. Delete this **How to** section

***

## Issue: New API call
### Name of new API feature
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
###### Returned value:
```JSON
{
    "status": 0,
    "description": "Error ...",
    "if_needed_key": "if_needed_value"
}
```
***

## Issue: Bug report
### Name of the bugged API feature
  - Request type: ... (POST, GET, PUT, DELETE)
  - Expected output: What should the API return 

###### Input data:
```JSON
{
    "tmp_key": "tmp_value"
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
