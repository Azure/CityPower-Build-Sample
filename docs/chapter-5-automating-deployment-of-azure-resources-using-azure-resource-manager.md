# Azure OpenDev Chapter 5: Automating Deployment of Azure Resources Using Azure Resource Manager (ARM)

## Session Overview

The Azure Resource Manager (ARM) provides administrators with a powerful means to manage all of their Azure resources. By using ARM, you can create or deploy new resources to Azure, update existing resources, or delete resources which are no longer necessary. In addition, ARM provides a template-based infrastructure whereby administrators can use JSON files to define the resources and any dependencies when deploying to Azure.

In the video for this session, Rick Rainey and Ryan Jones discuss using Azure Resource Manager templates to define the multi-region, multi-instance architecture which was created in earlier sessions.

![Azure OpenDev Multi-Region Multi-Instance Architecture][MRMI]

<!-- BUGBUG: Need final URL here!!! -->

All of the templates from the demos are available at https://github.com/Azure/OpenDev/tree/master/setup/ARMTemplates.

*Time to view the video for this session: 20:58*

## Session Objectives

In this session you will learn:

* What are ARM templates?

* How can you use ARM templates to automate your deployments to Azure?

### Prerequisites

In order to use the Azure Resource Manager (ARM), you will first need to create an Azure account. For more information on creating an Azure account for free, see the notes in the [Azure OpenDev Overview][Overview] article.

## Exercise 1 - Discussing ARM template syntax

In the first half of the video, Rick and Ryan demonstrate the following:

* The JSON syntax which is used for ARM templates.

* Defining the resources and dependencies within an ARM template.

* Referencing child ARM templates from within a parent template.

## Exercise 2 - Deploying using ARM templates

In the second half of the video, Rick and Ryan demonstrate the following:

* Where to find the ARM templates which they have written for this video.

* How to use Powershell with ARM templates to deploy the multi-region, multi-instance architecture from earlier sessions to Azure.

* How to customize the templates to change the environment for your topology.

## Session Summary

In this session you learned:

* Azure Resource Manager (ARM) templates make it easy to define the necessary resources and dependencies for deploying applications to Azure.

* How to use JSON to define the list of resources and dependencies within an ARM template.

* How to reference templates within other templates.

* How to deploy a multi-region, multi-instance architecture to Azure by using ARM templates.

## See Also

For more information about using Azure Resource Manager, see the [Azure Resource Manager Overview][ARM Overview].

<!-- URL List -->

[Azure Java Developer Center]: https://azure.microsoft.com/develop/java/
[Java Tools for Visual Studio Team Services]: https://java.visualstudio.com/
[Azure Node.js Developer Center]: https://azure.microsoft.com/develop/nodejs/
[ARM Overview]: https://docs.microsoft.com/en-us/azure/Azure-Resource-Manager/resource-group-overview

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

[MRMI]: ./media/Azure-OpenDev-Multi-Region-Multi-Instance-Architecture.png
