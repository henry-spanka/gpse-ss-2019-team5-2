[MailHog]: https://github.com/mailhog/MailHog
[VirtualBox]: https://www.virtualbox.org
[Vagrant]: https://www.vagrantup.com

# Roomed

Easy to use Room Management system to schedule meetings within an organization.

# Setup / Installation

#### Run

Roomed requires Java 11 and is supported on Linux/macOS but may also run on Windows.

Roomed can be run with the following command on Linux or macOS:
```bash
./mvnw spring-boot:run
```

and on Windows with:
```bash
./mvnw.cmd spring-boot:run
```

Prebuilt JARs can be run with:
```bash
java -jar <artifactname>.jar
```

By default Roomed can be accessed at http://localhost:8080.

---

#### Configuration

Any configuration options (including mail options) are documented
in [src/main/resources/application.properties](src/main/resources/application.properties).

A custom configuration file can be used as following:
```bash
# Linux/macOS
./mvnw spring-boot:run -Drun.jvmArguments="spring.config.location=/path/to/application.properties"
# Windows
./mvnw.cmd spring-boot:run -Drun.jvmArguments="spring.config.location=/path/to/application.properties"
# Artifact
java -jar <artifactname>.jar --spring.config.location=/path/to/application.properties
```

##### Mail Configuration
For various parts of the application to work correctly it's required to configure a mail server to send verification
mails, for example during registration. Otherwise an error may be shown when trying to perform an action that requires
emails to be sent.

By default Roomed will use [MailHog], a mail server for testing.
MailHog can be run with (Requires [Vagrant] and a virtualization software, e.g. [VirtualBox]):
```bash
# Launches a lightweight virtual machine with MailHog installed.
vagrant up
```

and stopped/removed with:
```bash
# Shuts down the virtual machine.
vagrant halt
# Stops and removes the virtual machine.
vagrant destroy
```

MailHog can be accessed at http://localhost:8025.

# License
The project is subject to the MIT license unless otherwise noted.
A copy can be found in the root directory of the project [LICENSE](LICENSE).
