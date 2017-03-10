# Azure OpenDev Chapter 7: Introduction to Azure Container Service (ACS)

## Session Overview

Software *Containers* are a commonly-used virtualization method which allows develoeprs to combine all of the resources necessary for their application into a single package which can then be deployed to another computer more-easily than setting up a new virtual machine and individually installing all of the necessary resources for their application to run on the target computer. As applications grow in complexity, developers can utilize mutiple containers to spread the workload and application logic across several virtual machines. In order to coordinate the interaction between these containers, application designers use *Orchestrators* to manage their containers. To help orchestrate their containers which are hosted in Microsoft Azure, developers can utilize the [Azure Container Service], which uses three common Open Source Software (OSS) orchestrators: [DC/OS], [Docker Swarm], and [Kubernetes].

In the video for this session, Ross Gardler and Sean McKenna discuss how to use Azure Container Service to manage a sample application which spans multiple containers.

<!-- BUGBUG: Need final URL here!!! -->

In order to view this video, [click here](http://aka.ms/azureopendev).

*Time to view the video for this session: 21:07*

## Session Objectives

In this session you will learn:

* What are software *Containers* and *Orchestrators*?

* How does the Azure Container Service (ACS) manage containers?

### Prerequisites

In order to use the Azure Container Service, you will first need to create an Azure account. For more information on creating an Azure account for free, see the notes in the [Azure OpenDev Overview][Overview] article.

## Exercise 1 - Getting Started

In the video for this session, Ross and Sean demonstrate a sample application called *BigLittleChallenge*, which consists of a minimum of three containers:

* The *Engine Container* is a single instance for the application and handles the bulk of the processing logic; this is written in Java.

* The *Dashboard Container* is a single instance for the application and displays statistics to a web browser; this is written in Python.

* The *Player Container* can run mutiple instances for the application, and contains the code for an Artificial Intelligence (AI) 'virtual' player; this is written in Java.

As shown in this example, since the containers communicate across common protocols, each container can use different languages.

## OPTIONAL: Exercise 2 - Running the sample code on your own

In order to experiment with the application which was the video, you can download all of the code and build scripts from the following GitHub repository:

<!-- BUGBUG: Need final URL here!!! -->

* https://github.com/Azure/acs-demos/tree/master/incubator/BigLittleChallenge

## Session Summary

In this session you learned:

* Software *Containers* and *Orchestrators*:
   * *Containers* are a commonly-used virtualization method for deploying application with all necessary resources.
   * *Orchestrators* manage the interaction between containers.

* The Azure Container Service (ACS) utilizes three common Open Source Software (OSS) orchestrators for managing containers:
   * [DC/OS]
   * [Docker Swarm]
   * [Kubernetes]

## See Also

For more information about using the Azure Container Service, see the following articles:

* [Azure Container Service]

* [Azure Container Service Documentation]

   * [Introduction to Docker container hosting solutions with Azure Container Service](https://docs.microsoft.com/en-us/azure/container-service/container-service-intro)
   
   * [Manage an Azure Container Service DC/OS](https://docs.microsoft.com/en-us/azure/container-service/container-service-mesos-marathon-ui)
   
   * [Get started with Kubernetes and Windows containers in Container Service](https://docs.microsoft.com/en-us/azure/container-service/container-service-kubernetes-windows-walkthrough)
   
   * [Container management with Docker Swarm](https://docs.microsoft.com/en-us/azure/container-service/container-service-docker-swarm)

<!-- URL List -->

[Azure Java Developer Center]: https://azure.microsoft.com/develop/java/
[Java Tools for Visual Studio Team Services]: https://java.visualstudio.com/
[Azure Node.js Developer Center]: https://azure.microsoft.com/develop/nodejs/
[Azure Container Service]: http://azure.microsoft.com/services/container-service/
[Azure Container Service Documentation]: https://docs.microsoft.com/azure/container-service/

[DC/OS]: https://dcos.io/
[Docker Swarm]: https://docs.docker.com/engine/swarm/
[Kubernetes]: https://kubernetes.io/

[Overview]: ./README.md
[Chapter1Java]: ./chapter-1b-deploying-a-java-app-on-azure.md
[Chapter1Node]: ./chapter-1a-deploying-a-node.js-app-on-azure.md
[Chapter2Java]: ./chapter-2b-leveraging-managed-mongodb-and-redis-services-for-your-java-app.md
[Chapter2Node]: ./chapter-2a-leveraging-managed-mongodb-and-redis-services-for-your-node.js-app.md
[Chapter3]: ./chapter-3-transforming-from-a-single-vm-to-a-highly-scalable-geo-distributed-app.md
[Chapter4]: ./chapter-4-monitoring-your-azure-resources.md
[Chapter5]: ./chapter-5-automating-deployment-of-azure-resources-using-azure-resource-manager.md
[Chapter6]: ./chapter-6-managing-your-azure-resources-using-azure-cli.md
[Chapter7]: ./chapter-7-introduction-to-azure-container-service.md

<!-- IMG List -->
