[MailHog]: https://github.com/mailhog/MailHog
[VirtualBox]: https://www.virtualbox.org
[Vagrant]: https://www.vagrantup.com

# Roomed

Easy to use Room Management system to schedule meetings within an organization.

# Table of Contents
1. [Installation](#Installation)
    * [Run](#Run)
    * [Configuration](#Configuration)
        * [Mail Configuration](#Mail-Configuration)
        * [Restricting Domains for self enrollment](#Restricting-Domains-for-self-enrollment)
2. [Architecture](#Architecture)
3. [User Guide](#User-Guide)
    * [Login](#Login)
    * [Logout](#Logout)
    * [Recover Password](#Recover-Password)
    * [Register](#Register)
    * [Start Page](#Start-Page)
    * [Chronik](#Chronik)
    * [User Profile](#User-Profile)
        * [Profile Overview](#Profile-Overview)
        * [iCalendar Subscription](#iCalendar-Subscription)
        * [Edit Profile](#Edit-Profile)
    * [Meeting](#Meeting)
        * [Create](#Create)
        * [Edit](#Edit)
        * [Participants](#Participants)
        * [Confirmation](#Confirmation)
        * [Rebooking](#Rebooking)
4. [Admin Guide](#Admin-Guide)
    * [Data Import](#Data-Import)
        * [Room csv import format](#Room-csv-import-format)
        * [User csv import format](#User-csv-import-format)
        * [Meeting csv import format](#Meeting-csv-import-format)
5. [LICENSE](#LICENSE)

# Installation

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

Default user: `admin`  
Default password: `password`

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

To change the URL that Roomed will refer to change the `app.url` property in the configuration file as required.

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

##### Restricting Domains for self enrollment
Roomed has the option to only allow specific domains to be used for self enrollment. Users will receive an error message if the domain is not permitted during registration.

This feature can be enabled by adding the following configuration option to the configuration file `application.properties` (see above).

```bash
register.allowed-domains = domain1.com,domain2.com
```

# Architecture
Roomed is developed in Java, a multi purpose programming language using Spring as a framework with Thymeleaf as template engine.

Roomed uses Hibernate and H2Database for persisting data.

The frontend is built using Thymeleaf, Bootstrap, Fontawesome and jQuery.

# User Guide
This guide is intended for users and administrators of Roomed.

### Login
Once you have [created an account](#Register), or are enrolled by a member of your team, you can login at Roomed. You should automatically be prompted to login when visiting the application.

Alternatively you can login via the url:
```bash
http://localhost:8080/login
```

### Logout
If you use a shared or public computer you should logout at the end of your session.

You can logout via the nav bar or manually with the following url:
```bash
http://localhost:8080/logout
```

### Recover Password
If you have forgotten your password you can reset your password at:
```bash
http://localhost:8080/recoverpw
```

### Register
Roomed allows self enrollment which means that you can register without requiring confirmation from a team member or supervisor.

To register go to the url:
```bash
http://localhost:8080/register
```

Once you have filled all required fields you should receive a confirmation email to your email address. Once you have confirmed your account you can [Login](#Login).

If an error occurred during registration the invalid field will be annotated which an error message.

**Note**: The administrator may only allow specific domains for self enrollment. If you do not have a whitelisted email address you can not enroll on your own.


### Start Page
The start page welcomes the user after they have logged in.

Every user has their own unique start page. The start page shows every meeting for the next three days. The buttons for the days are dynamic and the meetings always consider the timezone.

Each meeting can be accessed for more details. In addition to this the user can access every function from the start page. A meeting can be created on the top-right of the screen, the complete meeting history is accessible with the button in the bottom part of the page.

### Chronik
The chronik shows a short personal overview of all upcoming meetings. You can get detailed information by clicking on a meeting and then clicking on the "i" icon.

The chronik can be accessed at:
```bash
http://localhost:8080/chronik
```

### User Profile
As a user of Roomed you have personal profile which is used to identify you within the application.

##### Profile Overview
The profile gives the user a nice overview of all the information you need. You can check your profile picture, e-mail, role and your location. It is also possible to import all your meetings into your calendar with the iCal URL.

When you first visit your profile you should upload a profile picture and set up a location so the app uses the time zone of that location.

If you press the button „change/recover password“ your password will be resetted and you can choose a new one. That process is equivalent to resetting your password on the login screen.
If you press the button „edit profile“ you get to a new page where you can change all important personal information.

##### iCalendar-Subscription
Roomed allows users to subscribe to their upcoming meetings from their phones.
The iCal URL can be viewed your profile. Please consult your phone's manual on how to subscribe to iCalendar's.

##### Edit Profile
As a user you can edit you’re name, select a location and upload a profile picture.

Changing your first and last name won’t affect your username or email, it is just for you.
Changing your location will affect your time zone, so you should change that information.

If you want you can upload a profile picture. Image, jpeg and png are accepted formats.
When you are done editing your profile you can press the button „Save&Exit“ and your changes will be saved.


### Meeting
As a user you can schedule and participate in meetings.

You can view a meeting by selecting the meeting from the [Start Page](#Start-Page) or [Chronik](#Chronik) and then clicking on the "Edit" button.

##### Create
To create a meeting click on "Create Meeting" in the nav bar or on the calendar with the plus icon in the top right corner.

After you have filled general meeting details and selected at least one location where the meeting should be held possible rooms will be shown.

For each selected location you will get an overview of selectable rooms. You can either select specific rooms or do not check any at all. If you do not check at least one room per location then the best room will automatically be selected.

You can adjust the time frame in "one hour" steps forward and backward.

##### Edit
As an owner of a meeting (meaning you have created the meeting) you are able to edit a meeting.

You can change the title of the meeting by clicking on the "Edit" button in the meeting overview.

##### Participants
If you are the owner of the meeting you can view participants and add or remove them.

To add a participant click on the "Add" button next to the participants in the meeting overview. You can both add internal users (users who have an account) or external users (identified by firstname, lastname and their email address).

A filled plane indicates that this participant will receive notifications about the meeting via email. You can disable notifications for the participant by clicking on it again. Notifications are disabled if the icon is not filled.

Additionally a participant can be removed from the meeting by clicking on the "x" next to the participant.

##### Confirmation
The owner can and should confirm his/her meeting. The meeting can be confirmed within 30 minutes before the start through the meeting details, where a confirm button is on the bottom part of the page, which turns green, when it's confirmed, or through an E-Mail confirmation link, which is also always send 30 minutes before the meeting.

If a user does not confirm the meeting then the meeting will be deleted by the system to open the room for new meetings again.

##### Rebooking
Roomed can automatically rebook meetings. If a meeting should not be rebooked you can check the checkbox during [meeting creation](#Create).
Participants are automatically notified when rooms are changed via email. To disable these notifications see [Participants](#Participants).

# Admin Guide
This guide is intended only for administrators of Roomed.

### Data Import
If the administrator wants to upload a file containing rooms, meeting or possible users go to the following url: 
```bash
http://localhost:8080/csvImport
```

You have the possibility to upload a CSV file with the file chooser.

**Note**: The CSV file must be in the following format and a header must be present in the file:

##### Room csv import format
```bash
Location; name; maximum number of persons; equipment; telephone; notes; office; mail address
```

Equipment items and participants shall be separated by commas.

##### User csv import format
```bash
firstname; lastname; mail address
```

A participant must have a mail address, firstname and lastname, separated by _ (underscore). The dates should be in LocalDateTime format ("yyyy-MM-DD HH:mm") and should be separated by a space (not a "T").

##### Meeting csv import format
```bash
title; startdate; enddate; participants; owner; confirmed; description
```

**Note**: If the uploaded file does not fit the given format the user is redirected to an error page, which states that there has been a problem with the import. You can try to import the data again with the button "Try again".

### Data Export
If the administrators wants to export all rooms, users and meetings visit the following url:
```bash
http://localhost:8080/csvExport
```

The files are written to the file system of the server in the root directory of the executable jar.

# License
The project is subject to the MIT license unless otherwise noted.
A [copy](LICENSE) can be found in the root directory of the project.
