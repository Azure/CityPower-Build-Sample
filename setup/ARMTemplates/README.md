# Azure OpenDev ARM Templates
ARM templates for single-region and multi-region application deployments.

## Single-Region Multi-Instance Deployment Instructions
### PowerShell
1. Open **.\deploymentParameters\virtualNetwork.Parameters.json**
2. Replace the IP address in `mgmtSubnetNsgSourceAddressPrefix` with your IP address.
  The ARM template will whitelist this IP address in the *mgmt* subnet to allow SSH connections to the jumpbox (bastion host).
3. Open a PowerShell console.
```PowerShell
# Authenticate to Azure Subscription
Login-AzureRmAccount

# Deploy single-region multi-instance ARM templates for Java version of app
.\Single-Region-ARM-Templates\Deploy-AzureResourceGroup.ps1 -ResourceGroupLocation westus -AppType Java

# Deploy single-region multi-instance ARM templates for Node.js version of app
.\Single-Region-ARM-Templates\Deploy-AzureResourceGroup.ps1 -ResourceGroupLocation westus -AppType Node

```

## Multi-Region Multi-Instance Deployment Instructions
### PowerShell
1. Open **.\deploymentParameters\virtualNetwork.Parameters.json**
2. Replace the IP address in `mgmtSubnetNsgSourceAddressPrefix` with your IP address.
  The ARM template will whitelist this IP address in the *mgmt* subnet to allow SSH connections to the jumpbox (bastion host).
3. Open a PowerShell console.
```PowerShell
# Authenticate to Azure Subscription
Login-AzureRmAccount

# Deploy Multi-Region ARM template to n regions (ie: WestUS and EastUS) for Java version of app.  Also deploys an HA resource group that contains the traffic manager profile, traffic manager endpoint configurations, document DB, blob storage, and CDN.
.\Multi-Region-ARM-Templates\Deploy-AzureResourceGroup.ps1 -Locations ("westus", "eastus") -AppType Java

# Deploy Multi-Region ARM template to n regions (ie: WestUS and EastUS) for Node.js version of app.  Also deploys an HA resource group that contains the traffic manager profile, traffic manager endpoint configurations, document DB, blob storage, and CDN.
.\Multi-Region-ARM-Templates\Deploy-AzureResourceGroup.ps1 -Locations ("westus", "eastus") -AppType Node

```
