<b> Hi, </b>

<h1>Here's my car rental application.</h1>

<ul>
-> You can rent a car and change dates afterwards.

-> You can check car's vin number (at the moment it's mainly working with cars from the USA).

</ul>

<p>
It's my first attempt to this kind of application. At the moment, you can add cars, users and create rentals out of these two.

Logins are created for users and admin token can be created, but it's not fully functioning yet.

Email scheduler is implemented for admin with a message containing number of rentals at the moment when app is started. Message is set to be sent just after starting the app.

</p>

<b>TO RUN APPLICATION</b>

Clone backend https://github.com/Kacyper/rental-backend and here's link to frontend -> https://github.com/Kacyper/rental-frontend

<ol>
-> Open project and set local MySQL db and credentials according to application.properties.

-> data.sql consists of basic data to check app, of course you can fill it up with your own.

-> Set your admin email to receive admin mail from scheduler. 

-> You can add yourself with your own email to receive user's messages.

-> Email sending is configured with mailgun. If you want you can change email address in line 32 in application.properties to receive messages from a different address, but it should all go through mailgun anyway. 

-> Build the project and start CarRentalBackendApplication.class - it's using local server 8080.

-> Next you can use swagger or use a link below to check controllers through POSTMAN.
</ol>

<b> I have implemented swagger to check controllers quicker, but below I provide a link to postman to access all request. </b>

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/6e5ec90bf6be53a47398?action=collection%2Fimport)

<b>TO RUN FRONTEND APPLICATION</b>

<ol>

-> Build the project and start CarRentalFrontendApplication.class - it's using local server 8081.

-> Open your browser on localhost:8081.
</ol>
