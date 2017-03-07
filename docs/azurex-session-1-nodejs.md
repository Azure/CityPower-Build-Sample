---
title: AzureX - Session 1 (for Node.js Developers) | Microsoft Docs
description: ''
services: ''
documentationcenter: ''
author: rmcmurray
manager: erikre
editor: ''

ms.assetid: 
ms.service: multiple
ms.workload: na
ms.tgt_pltfrm: multiple
ms.devlang: ''
ms.topic: article
ms.date: 03/01/2017
ms.author: robmcm;shsivada;stfollis;ross

---

# AzureX Session 1 - Deploying a Node.js app on Azure

## Session Overview

In this session we will run an application locally on our dev machine, then deploy the application to a virtual machine running in Azure.

*Time to complete all of the exercises in this session: 10:00*

## Session Objectives

Establish a level of familiarity with the Azure Command Line Interface and deploying application code into Azure Virtual Machines.

* Introduce the City Power & Light sample application.
* Provision resources in Azure using the Azure CLI.
* Deploy application code to an Azure Virtual Machine.

### Prerequisites

Before you complete the exercises in this session, you should read the information presented in the [Overview] article.

<!-- The following note would come from an include file when hosted on docs.microsoft.com -->
**NOTE**: You need an Azure account to complete the exericises in this session:

* You can [open an Azure account for free](https://azure.microsoft.com/pricing/free-trial/?WT.mc_id=A261C142F): You get credits you can use to try out paid Azure services, and even after they're used up you can keep the account and use free Azure services, such as Websites. Your credit card will never be charged, unless you explicitly change your settings and ask to be charged.
* You can [activate MSDN subscriber benefits](https://azure.microsoft.com/pricing/member-offers/msdn-benefits-details/?WT.mc_id=A261C142F): Your MSDN subscription gives you credits every month that you can use for paid Azure services.

## Excercise 1 - Introducing City Power & Light

The City Power & Light sample application is available in this GitHub repository. Open a terminal window and clone the repo to your local machine using the command `git clone https://github.com/Azure/OpenDev.git`. Alternatively, download a zip file [here](https://github.com/Azure/OpenDev/archive/master.zip). 

CP&L is a simple web application that allows a user to view a dashboard of open "incidents" and create a new incident via a form. In the scenario, an incident is an outage or issue with a municipality's utilities. The applicatoin is divided into a web tier based on Express, an API tier based on Loopback, and a data tier of MongoDB.

> Ensure that MongoDB 3.4 Community Edition [is installed](https://docs.mongodb.com/manual/administration/install-community/) locally on your developer environment. 

Once you have the repository downloaded, run `npm install` in both the `/node/web` and `/node/api` folders to restore dependencies. Next, open the `/node` folder in [Visual Studio Code](https://code.visualstudio.com/docs/setup/setup-overview) and [run](https://code.visualstudio.com/docs/editor/node-debugging) the application locally with the VSCode debugger. 

Open a browser window to `http://locahost:3000` and create a sample incident.

## Excercise 2 - Provisioning Azure resources

Before we can deploy our application to Azure we need to provision a virtual machine. Install and login to your Azure Subscription in the [Azure Command Line Interface (CLI) 2.0](https://docs.microsoft.com/en-us/cli/azure/get-started-with-azure-cli).

All resources in Azure reside in a "[Resource Group](https://docs.microsoft.com/en-us/azure/azure-resource-manager/resource-group-overview)". A Resource Group allows us to deploy, manage, and deprovision sets of related resources in a consistent manner. To create a Resource Goup via the Azure CLI, run `az group create -n CityPower -l EastUS` within a terminal window. This command created a group named "CityPower" located in the East US [Azure Region](https://azure.microsoft.com/en-us/regions/). Feel free to adjust the name of the group or the region to be closer to your location.

Once the Resource Group is created, run `az vm create -g CityPower -n CityPowerVM --image UbuntuLTS` to provision a virtual machine named "CityPowerVM" within the CityPower resource group and using the image for Ubuntu. 

## Excercise 3 - Migrating application code

After the provisioning process completes, SSH into the virtual machine's Public IP address and verify that a connection can be established. By default, port 22 is enabled for this SSH operation while all other ports are closed by the [Network Security Group (NSG)](https://docs.microsoft.com/en-us/azure/virtual-network/virtual-networks-nsg).  To open a second port in the NSG, run `az vm open-port --port 80`. This will allow us to access the virtual machine from the browser's default port of port 80.

Our new virtual machine is not much use without some application code.  We can use the [SCP](https://en.wikipedia.org/wiki/Secure_copy) command to securely copy files from our local machines to the Azure VM. In the terminal, navigate back to the `/node` directory in the repository, and execute `scp . 000.000.000.000:~/` while replacing the 0s with your VM's public IP address. This will copy the present working directory's files to the home directory of the Azure VM. 

> If you previously ran `npm install` then the node_modules folders will take a long time to copy via scp. Delete the node_modules folders from your local machine before running scp, and then re-run npm install in the Azure VM to save time

SSH back into the VM and verify the application files are present in `/home`. Since the virtual machine was a stock Ubuntu image, we need to install a few supporting applications into the environment. From the Azure VM, run `curl -O https://raw.githubusercontent.com/Azure/OpenDev/master/setup/Extensions/node/SetupSingleVM.sh` to download a pre-made configuration script. This script will install the necessary dependencies ([MongoDB](https://docs.mongodb.com/manual/administration/install-community), [Node.js 7](https://nodejs.org/en/download/), and [pm2](http://pm2.keymetrics.io/)) and move application files from the **/home** directory to **/var/www**.  

In the **/var/www** directory, you should now have the **/web** and **/api** folders. We will use pm2 to startup the application and manage our processes.  Use `curl -O https://raw.githubusercontent.com/Azure/OpenDev/master/setup/Extensions/node/citypower.config.js` to retrieve the sample config file from GitHub. Then, run `pm2 start citypower.config.js` to run the application.

Back on your local machine's browser, navigate to **http://<yourPublicIPaddress** to see the City Power & Light application running out of an Azure datacenter.

## Session Summary

In this session we took a multi-tier Node.js application running on a local developer machine, provisioned virtual machine infrastructure in Azure, and migrated the application to the cloud. 

## What's Next

Once you have completed the exercises detailed in the above sections, your next step is to go through the exercises in [Session 2 (for Node.js Developers)][Session2Node].

## See Also

For more information about using Java with Microsoft Azure, see the [Azure Java Developer Center] and the [Java Tools for Visual Studio Team Services].

For more information about using Node.js on Microsoft Azure, see the [Azure Node.js Developer Center].

<!-- URL List -->

[Azure Java Developer Center]: https://azure.microsoft.com/develop/java/
[Java Tools for Visual Studio Team Services]: https://java.visualstudio.com/
[Azure Node.js Developer Center]: https://azure.microsoft.com/develop/nodejs/

[Overview]: ./azurex-overview.md
[Session1Java]: ./azurex-session-1-java.md
[Session1Node]: ./azurex-session-1-nodejs.md
[Session2Java]: ./azurex-session-2-java.md
[Session2Node]: ./azurex-session-2-nodejs.md
[Session3]: ./azurex-session-3.md
[Session4]: ./azurex-session-4.md

<!-- IMG List -->
