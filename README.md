# Popular Github repo finder
Looking for the most popular github repos ? Those highest stargazers

## How it works

Service retrieves the most popular github repos and offers few filter options. By default with no query params, latest 30 repositories are returned (latest = today)

Results are sorted by stars and order is descending. Below are the available filter options:

- date - from which to retrieve the data (format yyyy-MM-dd)
- language - to filter by (e.g Java, Python)
- page size - how many results are returned (max = 100)

Complete working example of request on localhost and response in json format:

`http://localhost:8080/popular-repositories?language=java&pageSize=5&date=2023-01-01`



```json
{
"data":[
    {
    "name":"Stirling-PDF",
    "description":"locally hosted web application that allows you to perform various operations on PDF files",
    "url":"https://api.github.com/repos/Stirling-Tools/Stirling-PDF",
    "user":"",
    "language":"Java",
    "stars":18612
    },
    {
    "name":"Chat2DB",
    "description":"An intelligent and versatile general-purpose SQL client and reporting tool for databases which integrates ChatGPT capabilities.",
    "url":"https://api.github.com/repos/chat2db/Chat2DB",
    "user":"",
    "language":"Java",
    "stars":11779
    },
  ]
}
```

### Tech used

- Spring boot 3.2.3
- Java 17
- Github API

Internally service caches the requests/response in a simple hashmap to reduce unnecessary load on Github API