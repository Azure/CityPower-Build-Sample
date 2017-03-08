# Chapter 3: Transforming from a Single Virtual Machine to a Highly-Scalable Geographically-Distributed App

## Session Overview

In previous sessions we migrated an application to Azure and enhanced it with a series of PaaS services. Up to this point we have worked with a single virtual machine, however in this session we will horizontally scale that VM into multiple instances, multiple tiers, and multiple regions.

*Time to complete all of the exercises in this session: 20:00*

## Session Objectives

* Learn how to scale virtual machine workloads on Azure with Availability Sets and Virtual Machine Scale Sets.
* Orchestrate multi-VM deployments with Jenkins.
* Patterns for deploying multi-geography solutions across Azure regions.

### Prerequisites

Before you complete the exercises in this session, you should read the information presented in the [Overview] article.

In order to complete the exercises in this session, you must first complete the prerequisites and steps outlined in [Session 1 (for Java Developers)][Session1Java] or [Session 1 (for Node.js Developers)][Session1Node].

## Exercise 1 - Deploying Code from Jenkins

1. Provision Azure Jenkins either from the [Azure Portal](http://portal.azure.com) Marketplace, or directly with an [ARM Template](http://aka.ms/azjenkins).  

1. Create Jenkins Projects to build each tier, then to release each tier into a set of VMs.

1. SSH into the virtual machine and install the [Azure CLI 2.0](https://docs.microsoft.com/en-us/cli/azure/get-started-with-azure-cli). 

1. Create a Service Principal and update the Jenkins Projects with the credentials

1. Update the environment variables within the Jenkins Project to match your deployed resources.

1. Deploy the code to the target VMs

## Exercise 2 - Geographic Redundancy

Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.

* https://docs.microsoft.com/azure/guidance/guidance-resiliency-checklist

## Session Summary

Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.

* Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
* Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
* Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.

## What's Next

Once you have completed the exercises detailed in the above sections, your next step is to go through the exercises in [Session 4][Session4].

## See Also

For more information about using Java with Microsoft Azure, see the [Azure Java Developer Center] and the [Java Tools for Visual Studio Team Services].

For more information about using Node.js on Microsoft Azure, see the [Azure Node.js Developer Center].

<!-- URL List -->

[Azure Java Developer Center]: https://azure.microsoft.com/develop/java/
[Java Tools for Visual Studio Team Services]: https://java.visualstudio.com/
[Azure Node.js Developer Center]: https://azure.microsoft.com/develop/nodejs/

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
