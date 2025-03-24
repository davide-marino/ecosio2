Compile using maven to create a runnable jar.

Run the generated jar as follow: 

`java -jar crawler-1.0-SNAPSHOT.jar <starting url> <number of threads>`

where the first parameter is the starting url, the second is the number of threads.

For example to download https://ecosio.com using 5 threads use:

`java -jar crawler-1.0-SNAPSHOT.jar https://ecosio.com 5`

