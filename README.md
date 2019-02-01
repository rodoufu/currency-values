# currency-values

* Functional requirements
  * Read input data from any http source every x seconds-minutes
  * Process data with any rules
  * Put it in the any in-memory database (H2)
  * By the request in browser show the last 10 entries (no UI, just json) by reading from the in-memody db
* Non functional requirements
  * Framework for tests: junit
  * Do not spend time for complex processing logic.
  * Java 8
  * spring boot
  * Don’t use hibernate.
