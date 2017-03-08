

# Chapter 1 (Node.js): Deploying a Node.js app on Azure

## Session Overview
In this session you will run an application locally on your dev machine, then deploy the application to a VM running in Microsoft Azure.

*Time to complete all of the exercises in this session: 30:00*

## Session Objectives

* Introduce the City Power & Light sample application.
* Become familiar with the Azure Command Line Interface (CLI) 2.0. 
* Provision Azure resources using the Azure CLI.
* Deploy application code to a VM in an Azure Virtual Machines.

### Prerequisites

* (Recommended) Review the [Overview](azurex-overview.md) topic.
* [MongoDB 3.4 Community Edition](https://docs.mongodb.com/manual/administration/install-community/). Required by the app to store data on you local developer computer. 
* An active Azure subscription. You can create a new Azure subscription for free in one of these ways: 
    * Use the [Azure free trial](https://azure.microsoft.com/pricing/free-trial/?WT.mc_id=A261C142F). Azure gives you credits to try out paid Azure services. Even when  the credits are gone, you can still keep the account and use some serviced for free, such as App Service Web Apps. Your credit card is never charged, unless you explicitly change your settings and ask to be charged.
    * You can [activate MSDN subscriber benefits](https://azure.microsoft.com/pricing/member-offers/msdn-benefits-details/?WT.mc_id=A261C142F). Your MSDN subscription gives you credits every month that you can use to paid for Azure services.
* Azure CLI 2.0. Follow the steps in the [Azure Command Line Interface (CLI) 2.0](https://docs.microsoft.com/en-us/cli/azure/get-started-with-azure-cli) topic to install and sign in to your Azure subscription.
* On a Windows computer, you will need to install a Git distribution and command-line tools, such as [Git for Windows]. MacOSX and Linux already have Git enabled in the Terminal. 

## Exercise 1: Introducing City Power & Light

City Power & Light (CP&L) is a simple web app that allows a user to create and view open *incidents*, which are outages or issues with a municipality's utilities. Existing open incidents are viewed in a dashboard. New incidents are created by filling out and submitting a form. The application is divided into a web tier based on Express.js, an API tier based on Loopback, and a data tier that uses MongoDB. The following diagram represents the architecture of the app running on a single VM:

![CP&L single-server application architecture](./media/Azure-OpenDev-Single-Machine-Architecture-1.png)

To get started, you must download the sample to your local computer from the GitHub repo. 

1. Open a terminal window and clone the repo to your local machine using the command `git clone https://github.com/Azure/OpenDev.git`. Alternatively, download a zip file [here](https://github.com/Azure/OpenDev/archive/master.zip). 

CP&L 



Once you have the repository downloaded, run `npm install` in both the `/node/web` and `/node/api` folders to restore dependencies. Next, open the `/node` folder in [Visual Studio Code](https://code.visualstudio.com/docs/setup/setup-overview) and [run](https://code.visualstudio.com/docs/editor/node-debugging) the application locally with the VSCode debugger. 

Open a browser window to `http://locahost:3000` and create a sample incident.

## Exercise 2 - Provisioning Azure resources

Before we can deploy our application to Azure we need to provision a virtual machine. 

All resources in Azure reside in a "[Resource Group](https://docs.microsoft.com/en-us/azure/azure-resource-manager/resource-group-overview)". A Resource Group allows us to deploy, manage, and deprovision sets of related resources in a consistent manner. To create a Resource Goup via the Azure CLI, run `az group create -n CityPower -l EastUS` within a terminal window. This command created a group named "CityPower" located in the East US [Azure Region](https://azure.microsoft.com/en-us/regions/). Feel free to adjust the name of the group or the region to be closer to your location.

Once the Resource Group is created, run `az vm create -g CityPower -n CityPowerVM --image UbuntuLTS` to provision a virtual machine named "CityPowerVM" within the CityPower resource group and using the image for Ubuntu. 

## Exercise 3 - Migrating application code

After the provisioning process completes, SSH into the virtual machine's Public IP address and verify that a connection can be established. By default, port 22 is enabled for this SSH operation while all other ports are closed by the [Network Security Group (NSG)](https://docs.microsoft.com/en-us/azure/virtual-network/virtual-networks-nsg).  To open a second port in the NSG, run `az vm open-port -g CityPower -n CityPowerVM --port 80`. This will allow us to access the virtual machine from the browser's default port of port 80.

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
