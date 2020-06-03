# My backend experiments with [Spring Boot Framework](https://spring.io)

In my free time i work on my hobby-project trying to cover all sides of application development (and by doing so find out what i like most...). 
This repository contains my work on server side. I would like to develop an application that performs offline as well as online, which produces extra difficulties like doubled ID management (i have to treat local ids as well as those on the server side, because model items can be created when i am offline), syncing issues (how to properly sync changes made offline and resolve possible conflicts), etc.

[Why Spring Framework?](https://spring.io/why-spring)

## Project structure:

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

## Syncing changes between client and server side

I was very much inspired by trello tech blog as they describe how they solve many of the problems i am interested in. And they also propose their solution for syncing: [tech.trello.com/syncing-changes](tech.trello.com/syncing-changes). The main idea is to use so called change entries to track CRUD-Style changes on model items and include so called deltas to describe how particular fields of items change. At this point, their iOS application even includes 'Sync Queue' listing in account section - that allows you as user to track what change entries with corresponding deltas are currently in the queue when you are offline. If you are online, changes will be immediately sent to the server, i suppose, passing the queue. 
I decided to adopt this idea in my application, with change entries being taken care of on the client side and deltas being sent in the request body as the list when you go online. Important meta data like time of change (to resolve possible conflicts) will be sent as request parameters. 

## The two ID problem

Trello Tech Blog also covers the two id problem i described above. At this point of time, i did not come to solve this problem myself, but here they explain their solution: [https://tech.trello.com/sync-two-id-problem/](https://tech.trello.com/sync-two-id-problem/)

## My progress:

- [x] Server runs
- [x] Login/Registration (in a simple way through /login, /register pathes)
- [x] Create, Edit, Delete events
- [x] Create many other items
- [x] Create templates
- [ ] ID Barrier Solution for client side
- [ ] Choose a logger and log stuff instead of printing it
- [ ] Edit, Delete templates
- [ ] Messaging
- [ ] Notifications
- [ ] Really lot of stuff a REST API Server requires
