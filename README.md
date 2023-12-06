## Setup

The project was built and run locally on a linux machine using 
1. JDK 18
2. Gradle 7.5


## Endpoints exposed
### Existing
- `GET /reply/{message}`

Returns the message as a JSON


### Newly Added
- `GET /v2/reply/{message}`

Returns the message as a JSON after applying the rules that are a part of the message. Currently accepts exactly two rules which are executed in sequence:
- `1`: Reverses the string
- `2`: Encode the string via MD5 hash algorithm and display as hex

The data and rules are seperated with a `-`

Note: The consumer always need to send exactly 2 rules as a part of the request
