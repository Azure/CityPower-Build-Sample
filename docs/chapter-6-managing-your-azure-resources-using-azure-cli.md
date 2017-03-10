# Azure OpenDev Chapter 6: Managing your Azure Resources Using Azure Command Line Interface (CLI)

## Session Overview

The Azure *Command Line Interface (CLI)* is Microsoft's cross-platform tool for managing your Azure resources from a command-line. By using this tool, users on Linux, MacOS, and Windows can send individual or create scripts which utilize Azure Resource Manager to administer their Azure resources.

In the video for this session, Brian Benz and Aaron Roney discuss using the Azure CLI to manage virtual machines, blobs, and resource templates.

<!-- BUGBUG: Need final URL here!!! -->

In order to view this video, [click here](http://aka.ms/azureopendev).

<!-- BUGBUG: Need final URL here!!! -->

All of the code from the demos is available at http://aka.ms/azureopendev.

*Time to view the video for this session: 21:06*

## Session Objectives

In this session you will learn:

* What is the Azure CLI?

* How can you use the Azure CLI to manage your Azure resouces?

### Prerequisites

In order to use the Azure Command Line Interface, you will first need to create an Azure account. For more information on creating an Azure account for free, see the notes in the [Azure OpenDev Overview][Overview] article.

To install the Azure CLI, follow the instructions in the [Install Azure CLI 2.0][Azure CLI Install] article.

## Exercise 1 - Configuring the Azure CLI

Once the CLI is installed, you can configure the various settings by using the following command:

```CMD
az configure
```

## Exercise 2 - Managing Virtual Machines

In the first of their demos, Brian and Aaron demonstrate several commands to manage virtual machines. For example:

1. The following command will provide you with the syntax for creating a virtual machine:

   ```CMD
   az vm create --help
   ```
1. The following command will show the syntax for listing your virtual machines:

   ```CMD
   az vm list --help
   ```
1. The following command will descrive the syntax for stopping a virtual machine:

   ```CMD
   az vm stop --help
   ```

## Exercise 3 - Managing Blobs

In the second part of their demos, Brian and Aaron demonstrate managing blobs with the Azure CLI. For example:

1. The following command will list your Azure storage accounts:

   ```CMD
   az storage account list
   ```
1. The following command will display the syntax for the various options for managing storage containers:

   ```CMD
   az storage container --help
   ```
1. The following command will show the various options for working with blobs:

   ```CMD
   az storage blob --help
   ```

## Exercise 4 - Using Azure Resource Manager Templates

In the third part of their demos, Brian and Aaron demonstrate using the Azure CLI to create templates to use with the Azure Resource Manager (ARM). For example:

1. The following command will provide assistance with exporting a resource group's configuration as a template:

   ```CMD
   az group export --help
   ```
1. The following command will show you the available options for creating resources from a template:

   ```CMD
   az group deployment create --help
   ```

## Session Summary

In this session you learned:

* The Azure Command Line Interface (CLI) is Microsoft's command-line application for managing your Azure resources

* The Azure CLI is a cross-platform tool which works on Linux, MacOS, and Windows 

* How to use the Azure CLI to manage virtual machines, blobs, and resource templates.

## See Also

For more information about using Java with Microsoft Azure, see [Overview of the Azure CLI 2.0][[Azure CLI Overview]].

For detailed information about using the Azure CLI to manage resources, see [Deploy resources with Resource Manager templates and Azure CLI](https://docs.microsoft.com/en-us/azure/azure-resource-manager/resource-group-template-deploy-cli).

For information about using JMESPATH to create queries to parse the JSON which is returned by the Azure CLI, see the [JMESPATH] website.

<!-- URL List -->

[Azure Java Developer Center]: https://azure.microsoft.com/develop/java/
[Java Tools for Visual Studio Team Services]: https://java.visualstudio.com/
[Azure Node.js Developer Center]: https://azure.microsoft.com/develop/nodejs/

[Azure CLI Overview]: https://docs.microsoft.com/cli/azure/overview
[Azure CLI Install]: https://docs.microsoft.com/cli/azure/install-azure-cli
[Azure CLI Get Started]: https://docs.microsoft.com/cli/azure/get-started-with-az-cli2
[JMESPATH]: http://jmespath.org/

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
