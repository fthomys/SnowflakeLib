# SnowflakeLib
A Java library for generating and decoding unique, time-ordered identifiers using the 
[Snowflake](https://en.wikipedia.org/wiki/Snowflake_ID) algorithm. 
Supports custom configuration, NTP clock drift checks, 
and decoding formats used by platforms like Discord and Twitter.

---

## Features
- Generate 64-bit time-based unique IDs
- Customizable epoch, worker ID, and Process ID
- Optional NTP-based clock drift detection
- Decode Snowflake IDs into timestamp, worker ID, process ID, and sequence
- Supports Discord, Twitter, and custom SnowflakeLib formats

## Installation
Add the following dependency to your `pom.xml` file:

```xml
<dependency>
    <groupId>me.fthomys</groupId>
    <artifactId>snowflake-lib</artifactId>
    <version>{VERSION}</version>
</dependency>
```
Replace `{VERSION}` with the latest version from
[Maven Central](https://search.maven.org/artifact/me.fthomys/snowflake-lib).

## Usage

### Generate a Snowflake ID
```java
import me.fthomys.SnowflakeLib.*;

public class SnowflakeLibExample {
    public static void main(String[] args) {
        SnowflakeGenerator generator = new SnowflakeFactory()
                .withEpoch(1609459200000L) // Custom epoch (Jan 1, 2021)
                .withWorkerId(1)
                .withProcessId(42)
                .enableNtpCheck(true)
                .withCustomNtpServer("pool.ntp.org")
                .build();

        Long id = generator.generateId();
        System.out.println("Generated ID: " + id);
    }
}
```

### Decode a Snowflake ID
```java
import me.fthomys.SnowflakeLib.*;

public class SnowflakeLibDecoderExample {
    public static void main(String[] args) {
        Long snowflakeId = 123456789012345678L;

        SnowflakeDecoder decoder = SnowflakeDecoderFactory.create(DecoderType.DISCORD);
        DecodedId decoded = decoder.decode(snowflakeId);

        System.out.println(decoded);
    }
}
```

## Configuration
### Custom Epoch
You can set a custom epoch when creating the `SnowflakeGenerator` instance. The epoch is the starting point for the timestamp in milliseconds. By default, it is set to `0`.

```java
SnowflakeGenerator generator = new SnowflakeFactory()
        .withEpoch(1609459200000L) // Custom epoch (Jan 1, 2021)
        .build();
```
### Worker ID and Process ID
The worker ID and process ID are used to differentiate between different instances of the generator. By default, the worker ID is set to `0` and the process ID is set to `0`.

```java
SnowflakeGenerator generator = new SnowflakeFactory()
        .withWorkerId(1)
        .withProcessId(42)
        .build();
```

### NTP Clock Drift Check

The library supports NTP-based clock drift detection. You can enable this feature when creating the `SnowflakeGenerator` instance. By default, it is disabled.

```java
SnowflakeGenerator generator = new SnowflakeFactory()
        .enableNtpCheck(true)
        .withCustomNtpServer("0.pool.ntp.org")
        .build();
```

Without #CustomNtpServer, the library will use `0.pool.ntp.org` as the default NTP server.

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request with your changes.
Make sure to follow the code style and include tests for any new features or bug fixes.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
