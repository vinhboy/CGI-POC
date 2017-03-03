

[https:Github.com/cgi-zahid/cgi-poc](https:Github.com/cgi-zahid/cgi-poc)

[Obtain Admin and sample End User credential](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/MyCAlerts_GettingStarted.pdf)

[![LOGO](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Graphics/MyCAlerts%20Logo.png)](https://MyCAlerts.com)

Link to our application:
[https://MyCAlerts.com]

# I.	Technical Approach #

CGI’s approach to the Pre-Qualified Vendor Pool for Digital Services – Agile Development (PQVP DS-AD) effort employed user-centric design techniques, a sprint based development workflow and modern and open-source technologies to design and build MyCAlerts, our implementation of Working Prototype B. MyCAlerts allows California residents to establish and manage user profiles, subscribe to receive severe fire/weather/flood/ad hoc alerts, and track their past notifications. Users can receive notifications via Short Message Service (SMS) and e-mail based on the street-location and contact information provided in their user profile.  MyCAlerts allows authorized administrative users to track and visualize events and send out notifications of ad hoc emergency and non-emergency events.

## Our Approach ##
### Sprint Zero###

We began with a review of the draft RFI. CGI established our team and began Sprint 0 planning. We determined the technical architecture and environments we would use. We deployed our standard developer tools and agile collaboration resources, to build a “Hello World” application (a simple login page) to test our technical stack and Continuous Integration/Continuous Deployment (CI/CD) framework.

### Prototype Selection and Kickoff ###

Upon receipt of the final RFI, our Product Manager (PM) led a prototype analysis session.  The team came together and held a planning and sizing session to evaluate the complexity, team interest, and risks of each prototype. With great enthusiasm our team selected Working Prototype B.

Based on initial user interviews, the PM selected the three datasets deemed most relevant to CA users. He selected to poll for automated emergency notifications wildfires (Active Fire Boundaries service from USGS GeoMAC), floods (River Gauge - Current and Forecast service from NOAA) and weather (Weather Hazards service from NOAA). 

At the kickoff, our PM provided his vision for the prototype and a high-level [roadmap](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Process/Roadmap.pdf) for completion of the work. The team established roles and responsibilities as well as a collaborative [team agreement](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Team/Teaming%20Agreeement.pdf).  We solidified and established our team working relationships. Using the roadmap and prototype requirements, the team developed an initial series of user stories. Our PM prioritized these stories along with UX/UI and technical infrastructure setup stories to establish our product backlog.

### User-Centric – User Driven###

Our UX/UI Designer facilitated a user centric – user driven design approach by engaging users early through the use of [persona interviews](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/UI-UX/MyCAlerts_Personas.pdf) and [surveys](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/UI-UX/Usability%20Testing/MyCAlerts_End-User_AuthorizedParticipantExitQuestionnaire.pdf). We leveraged AngularJS along with the standards and component set from the [U.S. Web-design (USWDS) style guide](https://standards.usa.gov/) for to implement a modern accessible web application.  We also tested for [ADA 508 and WCAG 2.0 compliance](https://github.com/CGI-Zahid/CGI-POC/tree/master/README_Evidence/UI-UX/Usability%20Testing/508%20Compliance). We tapped into users of various ages, roles, experiences, and backgrounds. During Sprint 1 we interviewed users and our results were quickly turned into [wireflows](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/UI-UX/MyCAlerts_SystemWireflow.pdf) leveraging a responsive design accommodating both desktop and mobile platforms. These wireflows were continuously refined based on user input. Our wireflows provided a visual to communicate the look and feel of the prototype to the developers. Beyond the initial design, users were engaged through usability testing and their input was evaluated and prioritized through improvement stories which were then added  in the product backlog for inclusion in subsequent sprints. 

### Agile Process###

We followed an agile process (Figure 1) of weekly sprint cycles, with each cycle starting on Wednesday and ending the following Tuesday. 

*Figure 1 - Our Agile Process*
![Figure 1 - Our Agile Process](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Graphics/Agile%20Sprint%20Cycle%20-%20New.png)

Each week Sprint rituals included:
Stand-Up - Monday-Friday @ 8:45 – 9:00 AM facilitated by the Agile Coach. Development team members reported work completed since the last session; planned work before the next session and any blockers. Blockers identified were cleared by the Agile Coach and Delivery Manager. Stand-Up provided a great forum for coordination  across the team. 

Backlog Grooming – Monday, our PM reviewed and reprioritized backlog items. The Agile coach and Delivery Manager supported the review and confirmed User Stories agreed with team’s “Definition of Ready”. 

Sprint Review - Tuesday morning, the team presented completed User Stories in the Sprint to the PM for review and approval. Approved User stories aligned with the team’s “Definition of Done”.

Sprint Retrospective – Tuesday morning, team reflected on how their tools, processes and peers performed on the recently completed sprint. Each team member was asked to identify one improvement trait they wanted to see the team start doing; one they wanted the team to stop doing and one they wanted the team to continue. 
 Facilitated by the Agile Coach, the identified start/stop/continue traits were consolidated and next steps defined by the team.
 
Sprint Planning – Tuesday afternoon, a one hour session for the PM and team interactively discussed and agreed on the payload of the next sprint. Sizing of the items in the sprint was coordinated by the Agile Coach and Delivery Manager.  [PlanITPoker.com](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Process/Sprint%20Estimation%20Poker.jpg) was used for story estimation. 

See our [Team Photo Album](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Process/CGITeamPhotoAlbum.pdf) for visual examples of the team and our agile process in action. 

With each iteration, the prototype became increasingly aligned to the vision of the PM, as well as the needs of our users. Our high-level roadmap included several user stories that ultimately were not included in the Working Prototype. These included [spike research](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Process/Spike%20Research.pdf) for an iOS native client application and native push notification functionality. While both were not in the posted Minimum Viable Product (MVP), they are included in the product backlog, architecture artifacts and GitHub source code.

Throughout the process, the team was able to coordinate work and monitor progress by using our [Scrum board](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Process/Physical%20Scrum%20Board%20.png). We used [JIRA](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Process/JIRA_Export.xlsx) to track user stories on an [electronic board](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Process/Example%20JIRA%20Board%20Sprint%203.pdf) (as well as bugs), and maintained a physical board in the team room. We used Confluence for document sharing and [HipChat](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Communication/HipChat%20Team%20Communication%202017-2-21_15-27-38.png) as our team collaboration tool. Metrics were tracked so the team understood how they were doing and potential areas for process improvement with each Sprint. Metrics showed the team their development velocity, technical backlog, and what percent of story points actually was implemented with each sprint.

## Technology Stack ##

For every technology decision, we considered open options, resulting in a stack that is predominately open source. Our technology target was a browser-based modern web application, but we also investigated the possibility of a native mobile app on iOS.

*Figure 2 – Our Technology Stack*
![Figure 2 – Our Technology Stack](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Graphics/Modern%20Web%20Application%20graphic.png)

- AngularJS frontend modern web framework
- DropWizard Java Application framework
- MySQL database
- JUnit unit testing framework
- Jasmine JavaScript testing
- Karma test runner
- Mockito mock test dependent services in the development environment
- Selenium automated running of functional tests 

From a DevOps standpoint:

- Jenkins configuration management
- CI platform continuous build, test, and prep for deployment to Docker. 
- Docker containerization solution for its cross-platform robustness for  deployments from non-production to production environments
- Docker images development environment, allowing new developers to get up and running quickly.

We tested and deployed the prototype on Microsoft’s Azure Infrastructure as a Service (IaaS) solution.  We used Azure’s monitoring solution for continuous infrastructure monitoring including networking, and New Relic for continuous application performance monitoring. We used the Key Performance Indicators (KPI) data to fine-tune our infrastructure solution and application.

## **Continuous Integration and Deployment** ##

Our solution used GitHub to document code and unit test commits in our public [GitHub repository](https://github.com/CGI-Zahid/CGI-POC).  Our GitHub structure has master and integration branches as well as feature branches. Development of individual stories was done in a feature branch in a local environment. Before checking code in, developers issued a pull request to trigger a code review. Once the code review was approved, code was merged into the integration branch, triggering the continuous integration process. 

Figure 3 displays the tools view and high level code migration from development to production using our CI/CD process. 

*Figure 3 – Continuous Integration and Deployment (Tools)*
![Figure 3 – Continuous Integration and Deployment ](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Graphics/CI%20and%20Deployment%20Graphic.%20-%20Tool%20View.png)

Jenkins retrieved the code from GitHub, built the application, and executed unit tests. If all unit tests passed, Docker created a distribution image. We employed a moderated CD approach to the test environment nightly to avoid interfering with ongoing functional testing. Ad hoc deployments were accommodated as needed. Once a build was deployed to test, our functional test suite (using Selenium) ran automatically.

*Figure 4 – Continuous Integration and Deployment (Process View)*
![Figure 4 – Continuous Integration and Deployment (Process View)](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Graphics/CI%20and%20Deployment%20Graphic%20-%20Process%20View.png)

Here is an overview of the steps we followed in our approach:

a.	Developer sets their local development environment using Docker files to mimic the operations environment and creates feature branches from the GitHub master branch (step 0). 

b.	Developer creates [unit tests](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Testing/Unit%20Functional%20Test%20Log.pdf) (step 1) and writes the appropriate source code (step 2) to implement a user story/feature. 

c.	To merge the unit test and [source code](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Process/Source%20Code%20Management.pdf), developer submits a pull request; triggers code review by a peer developer; reviewer approves/ denies the merge into the integration branch; finally developer resolves the code review observations. Once the code review is approved, the feature branch is merged into the integration branch (step 4). 

d.	Testers create automated functional scripts (step 3), which are merged in the integration branch (step 4). 

e.	A pre-determined schedule, Jenkins compiles the source code and all unit tests are executed automatically (step 5). 

f.	If the unit tests fail, a notification is sent regarding the failure and the developer fixes it in the correspondent feature branch (step 15). Steps 4 and 5 are repeated until the unit tests pass.

g.	Once the unit tests pass, Jenkins executes Docker files to build the Docker images for the UI and the backend (Step 6). 

h.	Docker pushes the images to the Azure Registry (Step 7), and then deploys them to the test environment where the functional tests are executed automatically (Step 8). 

i.	If the functional tests fail, a notification is sent (Step 14), and the developers fix the issues (Step 15). Steps 4, 5, 6, 7 and 8 are repeated until the functional tests pass. 

j.	Once the functional tests succeed, a notification is sent regarding the successful test execution (Step 9). 

k.	QA performs [ad-hoc/manual tests](https://github.com/CGI-Zahid/CGI-POC/tree/master/README_Evidence/Testing). If these fail, developer is notified to fix the issue (Step 15). Steps 4, 5, 6, 7, 8, 9 and 10 are repeated until the ad-hoc tests pass. 

l.	Once the error is fixed, the integration branch is merged with production tag in the master branch (Step 11).
 
m.	Finally, the image created for testing is deployed to the production environment (Step 12). 

## Code Flow ##

Our source code is structured to follow our distributed architecture and the software used to implement it. The frontend is stored in the [angular folder](https://github.com/CGI-Zahid/CGI-POC/tree/master/angular), and the backend in the [DropWizard folder](https://github.com/CGI-Zahid/CGI-POC/tree/master/dropwizard). We also have folders for automated functional tests in the [selenium folder](https://github.com/CGI-Zahid/CGI-POC/tree/master/selenium).

### Client UI and JavaScript library ###

The UI is built using AngularJS. Within the angular folder, the [app folder](https://github.com/CGI-Zahid/CGI-POC/tree/master/angular/app) includes subfolders for images, language, cascading stylesheets, scripts, and views. The [scripts folder](https://github.com/CGI-Zahid/CGI-POC/tree/master/angular/app/scripts) contains controllers, factory, and services. The [controllers folder](https://github.com/CGI-Zahid/CGI-POC/tree/master/angular/app/scripts/controllers) in turn hosts the JavaScript files, while the view folder contains HTML files. Unit tests are kept separately from the code in the [test folder](https://github.com/CGI-Zahid/CGI-POC/tree/master/angular/test).

The frontend communicates with the backend using RESTful APIs. The frontend code invoking the services resides in the [services subfolder](https://github.com/CGI-Zahid/CGI-POC/tree/master/angular/app/scripts/services) under the scripts folder.

### REST service and Backend ###

The application backend implements business logic, communicates with external services, sends notifications, and interacts with the database. The backend is implemented using DropWizard, which provides a java framework with REST and Junit support. Business logic and endpoints are in the [resource folder](https://github.com/CGI-Zahid/CGI-POC/tree/master/dropwizard/src/main/java/com/cgi/poc/dw/rest/resource), and the implementation of services is in the [service folder](https://github.com/CGI-Zahid/CGI-POC/tree/master/dropwizard/src/main/java/com/cgi/poc/dw/service).

The application also implements external API wrappers (implemented [here](https://github.com/CGI-Zahid/CGI-POC/tree/master/dropwizard/src/main/java/com/cgi/poc/dw/api/service/impl)) to retrieve data from external data sources.

Unit tests reside in the [test folder](https://github.com/CGI-Zahid/CGI-POC/tree/master/dropwizard/src/test).

### Database ###

The application communicates with the relational database (MySQL) using Hibernate. We use standard jaxb bean validations for data validation. The data access objects and model objects are located in the [dao folder](https://github.com/CGI-Zahid/CGI-POC/tree/master/dropwizard/src/main/java/com/cgi/poc/dw/dao).

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
- Dawna Floyd and Charles Jekal, Frontend Developers
- Valon Sejdini and Andrew Lucas, Backend Developers
- Sravan Neppalli, DevOps Engineer
- Chuck Ritchie, Security Engineer
- Nicole Tressler, Delivery Manager
- Nancy Naber, Business Analyst
- Mike Wirth, Agile Coach

**c.	Understood what people needed, by including people in the prototype development and design process;**

CGI followed a user-centric approach to the design and development of our prototype.  Our UX/UI designer engaged users early through the use of persona interviews and surveys.  The interview results were quickly turned into wireflows.  The wireflows were refined based on user surveys as well as usability testing with our users.  The wireflows provided a quick, visual way to communicate to developers the desired prototype look and feel so development could begin once the PM approved the initial stories.  

We applied design techniques and tools including Persona Interviews, Wireflow Development, Usability Testing, and Lean UX to develop our UI. To support a responsive browser-based interface, we leveraged the guidelines from U.S. Web Design (USWD) for modern web standards and AngularJS. Applying these standards, along with input from users, allowed us to build a prototype that was simple and intuitive to navigate and use. We also tested for [ADA 508 and WCAG 2.0 ](https://github.com/CGI-Zahid/CGI-POC/tree/master/README_Evidence/UI-UX/Usability%20Testing/508%20Compliance)compliance, using automated tools to test for adaptive reader support and other low vision options. 

**d.	Used at least a minimum of three (3) “user-centric design” techniques and/or tools;**

We used [personas interviews, wireflows, and usability tests](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/UI-UX/MyCAlerts_HCDTools-n-Techniques.pdf) as our main tools to develop a design for our prototype that focused on the needs and wants of our users.

**e.	Used GitHub to document code commits;**

Commits can be viewed in GitHub: [https://github.com/CGI-Zahid/CGI-POC](https://github.com/CGI-Zahid/CGI-POC "https://github.com/CGI-Zahid/CGI-POC")

**f.	Used Swagger to document the RESTful API, and provided a link to the Swagger API;**

All communication with the middle tier was done using REST API.  Middle Tier exposes the REST API using JAX RX and is documented in [Swagger](https://app.mycalerts.com/swagger).  

**g.	Complied with Section 508 of the Americans with Disabilities Act and WCAG 2.0;**

As part of our usability testing, we tested for [508 and WCAG 2.0 compliance.](https://github.com/CGI-Zahid/CGI-POC/tree/master/README_Evidence/UI-UX/Usability%20Testing/508%20Compliance)  We used automated testing to test for readability and low vision.  We addressed errors as part of our backlog process.  Other testing results were evaluated to determine which to add to the backlog and ones that did not apply to our prototype.

We used ACTF aDesigner for our 508 testing.

**h.	Created or used a design style guide and/or a pattern library;**

UX/UI created a style guide using the [US Web Design Standards](https://standards.usa.gov/).  Our color scheme was selected based on State of California colors and approved by user feedback.  Applying the US Web Design Standards along with input from users allowed us to build a prototype which was simple and intuitive to navigate and use. 

**i.	Performed usability tests with people;**

As part of our user centric approach we incorporated [usability testing](https://github.com/CGI-Zahid/CGI-POC/tree/master/README_Evidence/UI-UX/Usability%20Testing) into our process.  Usability testing was done through user surveys on our wireframes as well as feedback from users who tested our prototype throughout our sprints.  Feedback from the usability tests was evaluated by PM and UX designer to determine what to include in the backlog.  Based on PM direction new stories were created, prioritized, and placed in our backlog. 

**j.	Used an iterative approach, where feedback informed subsequent work or versions of the prototype;**

Within each sprint, inputs received from the Product Manager, usability testers, and defects identified during testing were evaluated, prioritized, and incorporated into the backlog as part of our iterative approach.  With each demo the prototype became more and more aligned to the vision of the PM as well as the needs of our users.

**k.	Created a prototype that works on multiple devices, and presents a responsive design;**

Our code has been testing using multiple devices and works with multiple web browsers.  In addition our code has been tested using Apple and Android devices.

We tested on:

1. PC: IE11; Chrome; Firefox
2. Mac:  Safari 
3. iPad: Chrome; Safari 
4. iPhone: Safari 
5. Android: Chrome


**l.	Used at least five (5) modern and open-source technologies, regardless of architectural layer (frontend, backend, etc.);**

We used the following six (6) modern and technologies and standards:

1.	USWDS
2.	Docker
3.	Bower
4.	Karma
5.	Grunt
6.	DropWizard v1.0.0


A list of of all of our technologies is provided:[Technology List](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Technology/PQVP%20Tool%20List.xlsx).  The rows highlighted in green on the spreadsheet are the open source technologies.

**m.	Deployed the prototype on an Infrastructure as a Service (IaaS) or Platform as Service (PaaS) provider, and indicated which provider they used;**

We used IaaS on Azure.

**n.	Developed automated unit tests for their code;**
 
Before checking code into the feature branch developers do a pull request to trigger a code review.  Once the code review is approved the code is merged into the integration branch which triggers the continuous deployment process.  

**o.	Setup or used a continuous integration system to automate the running of tests and continuously deployed their code to their IaaS or PaaS provider;**

We used Jenkins for continuous integration.  It grabs code from GitHub and compiles and executes tests.  If the code passes the tests then Docker creates an image.  The image is deployed to the system test environment where an end to end functional test is performed using Selenium.  

**p.	Setup or used configuration management;**

Azure container registries were used to store and manage our Docker images allowing us to manage configuration

**q.	Setup or used continuous monitoring;**

[Azure and New Relic](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Technology/Azure%20and%20New%20Relic%20Dashboard%20Screen%20Captures.pdf) were used to continuously monitor the health of the environment and the application

**r.	Deployed their software in an open source container, such as Docker (i.e., utilized operating-system-level virtualization);**

We deployed our software using Docker

**s.	Provided sufficient documentation to install and run their prototype on another machine;**

Below is a link to our install instructions.

[Instructions](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Technology/Setup%20Instructions%20for%20Another%20Machine.pdf)

**t.	Prototype and underlying platforms used to create and run the prototype are openly licensed and free of charge.**

We used openly licensed and free of charge platforms

[List of tools](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/Technology/PQVP%20Tool%20List.xlsx)

## III.	US Web Design Standards 
Our process for the design and development of our prototype followed and met many of the standards outlined in the U.S. Digital Services Playbook.  We provided a detailed document on GitHub which links to our evidence, as well as our response to each item.

[Our US Digital Service Playbook Responses ](https://github.com/CGI-Zahid/CGI-POC/blob/master/README_Evidence/US%20Digital%20Playbook%20Checklist.xlsx)




