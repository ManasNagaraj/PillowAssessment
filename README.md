# Pillow Assignment

### TODO:
1. Rate limit the API to Yi per Xi.
2. reponse header contains the rate limit information.
3. If the rate limit is exceeded, return a 429 status code.

### Implmentation:

webfilter implemented and some weird rendition of the token bucket algorithm.


### problems faced
1. lack of a global exception handling mechanism in spring webflux makes it difficult to handle exceptions in a uniform manner.
2. switchifempty() having eager mechanism caused a lot of issues in terms request count consistency. hence leading to cut it out entirely.
3. next nothing knowledge of spring webflux did take a toll on the time taken to complete the assignment.

#### references:
1. google
2. some guy called piotr
3. redis documentation for rate limiting
4. baeldung