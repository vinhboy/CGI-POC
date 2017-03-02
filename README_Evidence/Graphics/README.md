![](https://github.com/CGI-Zahid/CGI-POC/blob/README_Evidence/README_Evidence/Graphics/MyCAlerts%20Logo.png)




## II.	A-T Responses and Evidence 

**a.	Assigned one (1) leader and gave that person authority and responsibility and held that person accountable for the quality of the prototype submitted**

During the RFI evaluation, CGI selected a Product Manager (PM) based on his technical and management experience.  CGI gave the PM final decision making authority on the design and development of the prototype. 

**b.	Assembled a multidisciplinary and collaborative team that includes, at a minimum, five (5) of the labor categories as identified in Attachment B: PQVP DS-AD Labor Category Descriptions**

Under the PM’s leadership, CGI assembled a multidisciplinary team with various technical and Agile capabilities.  
Our Team:	

- Zahid Ali, Product Manager
- Roberto Obando, Technical Architect
- Gabriel Roth, Interaction Designer/User Researcher/Usability Tester/Visual Designer
- Nicole Williams, Writer / Content Designer / Content Strategist
- Vincent Baylly and Valon Sejdini, Frontend Developers
- Felix Pelletier and Andrew Lucas, Backend Developers
- Sravan Neppalli, DevOps Engineer
- Chuck Ritchie, Security Engineer
- Nicole Tressler, Delivery Manager
- Nancy Naber, Business Analyst
- Mike Wirth, Agile Coach

**c.	Understood what people needed, by including people in the prototype development and design process;**

CGI followed a user centric approach to the design and development of our prototype.  Our UX/UI designer engaged users early through the use of persona interviews and surveys.  The results of the interviews were quickly turned into wireframes.  The wireframes were refined based on user surveys.  The wireframes provided a quick and visual way to communicate to the developers the look and feel we wanted for the prototype so that development could begin once the PM approved the initial stories.  

**d.	Used at least a minimum of three (3) “user-centric design” techniques and/or tools;**

We used [personas, interviews, wireframes](https://cgisacramento.atlassian.net/wiki/download/attachments/1805131/MyCAlerts_HCDTools-n-Techniques_v2.018017.pdf?api=v2) as our main tools to develop a design for our prototype that focused on the needs and wants of our users.

**e.	Used GitHub to document code commits;**

Comments can be viewed in GitHub

**f.	Used Swagger to document the RESTful API, and provided a link to the Swagger API;**

All communication with the middle tier was done using REST API.  Middle Tier exposes the REST API using JAX RX and is documented in Swagger.  

**g.	Complied with Section 508 of the Americans with Disabilities Act and WCAG 2.0;**

As part of our usability testing, we tested for [508 and WCAG 2.0 compliance.](https://github.com/CGI-Zahid/CGI-POC/tree/integration/README_Evidence/UI-UX/Usability%20Testing/508%20Compliance)  We used automated testing to test for readability and low vision.  We addressed errors as part of our backlog process.  Other testing results were evaluated to determine which to add to the backlog and ones that did not apply to our prototype.

**h.	Created or used a design style guide and/or a pattern library;**

UX/UI created a style guide using the [US Web Design Standards](https://standards.usa.gov/).  Our color scheme was selected based on State of California colors and approved by user feedback.  Applying the US Web Design Standards along with input from users allowed us to build a prototype which was simple and intuitive to navigate and use. 

**i.	Performed usability tests with people;**

As part of our user centric approach we incorporated [usability testing](https://github.com/CGI-Zahid/CGI-POC/tree/integration/README_Evidence/UI-UX/Usability%20Testing) into our process.  Usability testing was done through user surveys on our wireframes as well as feedback from users who tested our prototype throughout our sprints.  Feedback from the usability tests was evaluated by PM and UX designer to determine what to include in the backlog.  Based on PM direction new stories were created, prioritized, and placed in our backlog. 

**j.	Used an iterative approach, where feedback informed subsequent work or versions of the prototype;**

Within each sprint, inputs received from the Product Manager, usability testers, and defects identified during testing were evaluated, prioritized, and incorporated into the backlog as part of our iterative approach.  With each demo the prototype became more and more aligned to the vision of the PM as well as the needs of our users.

**k.	Created a prototype that works on multiple devices, and presents a responsive design;**

Our code has been testing using multiple devices and works with multiple web browsers.  In addition our code has been tested using Apple and Android devices.

**l.	Used at least five (5) modern and open-source technologies, regardless of architectural layer (frontend, backend, etc.);**

1.	USWDS
2.	Docker
3.	Bower
4.	Karma
5.	Grunt
6.	DropWizard v1.0.0

**m.	Deployed the prototype on an Infrastructure as a Service (IaaS) or Platform as Service (PaaS) provider, and indicated which provider they used;**

We used IaaS on Azure

**n.	Developed automated unit tests for their code;**
 
Before checking code into the feature branch developers do a pull request to trigger a code review.  Once the code review is approved the code is merged into the integration branch which triggers the continuous deployment process.  

**o.	Setup or used a continuous integration system to automate the running of tests and continuously deployed their code to their IaaS or PaaS provider;**

We used Jenkins for continuous integration.  It grabs code from GitHub and compiles and executes tests.  If the code passes the tests then Docker creates an image.  The image is deployed to the system test environment where an end to end functional test is performed using Selenium.  
**p.	Setup or used configuration management;**

Azure container registries were used to store and manage our Docker images allowing us to manage configuration

**q.	Setup or used continuous monitoring;**

Azure and New Relic were used to continuously monitor the health of the environment and the application

**r.	Deployed their software in an open source container, such as Docker (i.e., utilized operating-system-level virtualization);**

We deployed our software using Docker

**s.	Provided sufficient documentation to install and run their prototype on another machine;**

**t.	Prototype and underlying platforms used to create and run the prototype are openly licensed and free of charge.**

We used openly licensed and free of charge platforms
Link to list of software

## III.	US Web Design Standards 
Our process for the design and development of our prototype followed and met many of the standards outlined in the U.S. Digital Services Playbook.  We provided a detailed document on GitHub which links to our evidence, as well as our response to each item.

[Link to our US Digital Service Playbook Responses ](https://github.com/CGI-Zahid/CGI-POC/blob/integration/README_Evidence/US%20Digital%20Playbook%20Checklist.xlsx)




