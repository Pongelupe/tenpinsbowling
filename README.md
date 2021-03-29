# Ten-pin Bowling

This project is command-line application to score a game of ten-pin bowling.

The rules implemented were based on this [bowling scores rules tutorial].

### Tech

This project is written in Java 11 and use [Apache Maven] for building and managing
dependencies. Using [Apache Maven Assembly Plugin] to package the application
and its dependencies into a single .jar file.

Also, this project has some well-know libraries:

* [Lombok] - Code generation tool
* [JUnit 4] - Testing framework


### Compiling and Running

After cloning the project you'll need only to run a build with Maven.

```sh
$ mvn clean compile assembly:single
```

After compiling to run the application:

```sh
$ java -jar target/tenpinsbowling-1.0.0-SNAPSHOT-jar-with-dependencies.jar <path to file>
```

### Testing

To run the tests:
```sh
$ mvn test 
```
It's also possible to run some samples:

```sh
$ java -jar target/tenpinsbowling-1.0.0-SNAPSHOT-jar-with-dependencies.jar src/main/resources/samples/zero_score # this a sample of a game with zero score 

$ java -jar target/tenpinsbowling-1.0.0-SNAPSHOT-jar-with-dependencies.jar src/main/resources/samples/perfect_game # this a sample of a game with a perfect score 

$ java -jar target/tenpinsbowling-1.0.0-SNAPSHOT-jar-with-dependencies.jar src/main/resources/samples/two_players # this a sample of a game with two players 
```


  [Apache Maven]: <https://maven.apache.org/>
  [Apache Maven Assembly Plugin]: <http://maven.apache.org/plugins/maven-assembly-plugin/>
  [bowling scores rules tutorial]: <https://www.youtube.com/watch?v=aBe71sD8o8c/>
  [JUnit 4]: <https://junit.org/junit4/>
  [Lombok]: <https://projectlombok.org/>
