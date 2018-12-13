Check the apropriate check box of the issue type and delete the part of the issue that is not needed. 

- [ ] New API call
- [ ] Bug Report
***
### API Name of new feature
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
### Bug report
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
