<br />
<p align="center">
  <a href="https://github.com/amosproj/amos2021ws05-fin-prod-port-quick-check/blob/main/Deliverables/2021-10-27_sprint-01-team-logo.png">
    <img src="Deliverables/2021-10-27_sprint-01-team-logo.png " alt="Logo" width="350" height="350">
  </a>

  <h3 align="center">Financial Portfolio Quick Check | AMOS Project 5</h3>
  
Welcome to the Financial Portfolio Quick Check repository. This repository contains the source code and the documentation of the Quick Check project. The student team is to develop a software which is helping consultants by evaluating financial products in terms of complexity and by other economical factors. This project is created in cooperation with the [BearingPoint GmbH](https://www.bearingpoint.com/en/).


<!-- Development -->
## **Development**
### Prerequisites
Docker is used to isolate each service (i.e. mysql database, springboot application)
that we are working on and to be able to develop across different environments.

1. [Install Docker](https://docs.docker.com/get-docker/)
2. Start Docker on your machine

### Launch development container

1. Open terminal and ```cd``` in root directory
2. Run command ```docker compose up```

By executing the last command, docker will create two containers.
* Container "database" is based on an image of a mysql:8 database
* Container "service" is based on an image of openjdk:11, which is running the service

### Extending the service
To be able to deploy your changes to the service you need to fire a build command within your IDE.

To fire a build command in Intellij press ```command``` + ```F9``` on Mac or from ```Menu Build``` --> ```Build Project```

The build command causes the springboot to restart within the container and apply your changes.




<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
        <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#demo">Demo</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <!--<li><a href="#acknowledgements">Acknowledgements</a></li>-->
  </ol>
</details>


<!-- ABOUT THE PROJECT -->
## About The Project

-Introduction-

### Product Vision

The goal of the project....

### Project Mission

---

<!-- GETTING STARTED -->
## **Getting Started**

### Prerequisites


<!--### Installation -->
### Installation

--- 
<!-- USAGE EXAMPLES -->
## Usage
### Basics
### Configuration


----------------------------------------------------------------------- 

<!-- ROADMAP -->
## Roadmap


----------------------------------------------------------------------- 
<!-- DEMO -->
## Demo

----------------------------------------------------------------------- 

<!-- LICENSE -->
## License

Distributed under the MIT License.

----------------------------------------------------------------------- 


<!-- CONTACT -->
## Contact

AMOS PROJECT - amos-fau-proj5@group.riehle.org

Industry Partner - [Bearing Point GmbH](https://www.bearingpoint.com/en/)

Project Link: [https://github.com/amosproj/amos2021ws05-fin-prod-port-quick-check](https://github.com/amosproj/amos2021ws05-fin-prod-port-quick-check)
