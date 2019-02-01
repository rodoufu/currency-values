# currency-values

* Functional requirements
  * Read input data from any http source every x seconds-minutes
  * Process data with any rules
  * Put it in the any in-memory database (H2)
  * By the request in browser show the last 10 entries (no UI, just json) by reading from the in-memory db
* Non functional requirements
  * Framework for tests: junit
  * Do not spend time for complex processing logic.
  * Java 8
  * spring boot
  * Don’t use hibernate.

The application reads the Bitcoin quotation from a Brazilian exchange at:
https://broker.negociecoins.com.br/api/v3/btcbrl/ticker
Then it modifies the data by mixing it with the currencies quotations from:
https://api.exchangeratesapi.io/latest?base=BRL
Generating a Bitcoin quotation for each currency found.
The data is saved in-memory with H2 the the last 10 entries can be obtained accessing the '/' address using the GET method. 

It is possible to configure the time between each time a Bitcoin quotation is requested,
this can be done on the file 'application.properties' on the property 'bitcoin.price.job.interval',
this value is the amount of milliseconds between two requests. 
