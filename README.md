# WeatherApp-using-SpringBoot-REST-API
Weather Information API
This is a Spring Boot application that provides weather information for a given pincode and date. It uses the OpenWeather API to fetch weather data and caches the results in a database for future requests.

**Features**

Fetch weather information for a given pincode and date.

Cache weather data in a database to optimize API calls.

Validate pincode and date inputs.

Handle errors gracefully (e.g., invalid pincode, API failures).
Example Requests and Responses
1. Valid Request (Cached Data)
Request
GET /api/weather/411014/2020-10-15
Response
json
{
  "id": 1,
  "pincode": "411014",
  "latitude": 18.5204,
  "longitude": 73.8567,
  "date": "2020-10-15",
  "weatherDescription": "Clear Sky",
  "temperature": 30.5
}


2. Valid Request (Fetch from API)
Request
GET /api/weather/411014/2023-10-15
Response
json
{
  "id": 2,
  "pincode": "411014",
  "latitude": 18.5204,
  "longitude": 73.8567,
  "date": "2023-10-15",
  "weatherDescription": "Cloudy",
  "temperature": 25.0
}


3. Invalid Pincode
Request
GET /api/weather/00000/2020-10-15
Response
json
Copy
{
  "timestamp": "2023-10-15T12:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid pincode. Pincode must be a 6-digit number.",
  "path": "/api/weather/00000/2020-10-15"
}


4. Invalid Date Format
request /api/weather/411014/2020/10/15
Response
{
  "timestamp": "2023-10-15T12:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid date format. Please use 'yyyy-MM-dd'.",
  "path": "/api/weather/411014/2020/10/15"
}


5. Pincode Not Found in Geocoding API
Request: /api/weather/999999/2020-10-15
Response
json
Copy
{
  "timestamp": "2023-10-15T12:00:00.000+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Failed to fetch weather data: Invalid pincode or API error.",
  "path": "/api/weather/999999/2020-10-15"
}
