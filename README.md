# My backend experiments with [Spring Boot Framework](https://spring.io)

In my free time i work on my hobby-project trying to cover all sides of application development (and by doing so find out what i like most...). 
This repository contains my work on server side.

[Why Spring Framework?](https://spring.io/why-spring)

Project consists of following folders:

* bytimeserver <=> Application entrance (BytimeServerApplication.java).
* config <=> Configuration classes.
* controllers <=> REST API controllers - these classes contain methods preceded by @Annotations that point which HTTP requests
  should be forwarded to the correposponding method. In the example below i have a HTTP GET request on /templates path which is forwarded to the method getTemplates.
```java
@GetMapping("/templates")
public List<Templates> getTemplates(@RequestParam(value = "username", defaultValue = "") String username) throws AccessDeniedException {
		Users user = username.isEmpty() ? usersService.getCurrentUser() : usersService.getUserByUsername(username);
		return templatesService.getTemplates(user);
}
```
* models <=> Application Model Classes.
* repositories <=> [Interfaces binding data base collections to perform read-write action](https://www.baeldung.com/queries-in-spring-data-mongodb). I am using them as pure connectors for my MongoDB Server Instance.
* services <=> Classes that serve as interface between Controllers and Repositories and perform logical checks and actual data operations.


My progress:

- [x] Server runs
- [x] Login/Registration (in a simple way through /login, /register pathes)
- [x] Create, Edit, Delete events
- [x] Create many other items
- [x] Create templates
- [ ] Edit, Delete templates
- [ ] Messaging
- [ ] Notifications
- [ ] Really lot of stuff a REST API Server requires
