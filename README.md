# FlowRET


To run navigate to src/test/java/com/americanexpress/unify/flowret/sample and run PharmacyWorkflowRunner.java


General notes on Flowret readme:


java classes that implement InvokableRoute and InvokableStep
jar file must be included to access methods
json to define workflow (steps) for pharmacy application 
	- Customer name
	- Agreement expiry date
	- Warning threshold
	- Check agreement
	- Display message to user

When Flowret reaches a unit in the json file, it calls the component factory to obtain an instance of the corresponding java class. 

The instance that is returned will create an object and implement the InvokableStep or InvokableRoute

The component factory is using switch-case statements to check the string in the component field of the unit in the json file, and creating objects based off of that string. If a case fails it checks another case, until there are no other checks and it outputs an error.

Once the object is created and implements the Invokable, it calls the appropriate methods for steps or for routes.

These methods are custom for what you want that class to accomplish. They contain the business logic.

The route needs to read the return value of the previous route to determine which branch to take

Each subsequent class needs logic for checking the return value of the previous class stored in the process context, before it serves its purpose and updates the value of any process variables in the context.

In the JSON file you have create a process_variables array that includes all of the process variables.

Ex: 

"process_variables": [
  {
    "name": "agreementExpiryDate",
    "type": "string",
    "value": "2025-04-01",
    "comment": "The expiry date of the agreement"
  },
  {
    "name": "warningThresholdDays",
    "type": "integer",
    "value": "30",
    "comment": "Days before expiry to trigger a warning"
  }
]

When the process starts, flowret loads these variables into the process context.

In the JSON file you define whether the process type for each unit. 



Implementation thought process:



- Context: In pharmacy, certain states require pharmacies to notify a customer when their LSA is about to expire for a certain perscription. We need to use this libary to complete the following worflow:

- If a perscription is being filled complete the following: 

- Check the database for the persons name and retrieve their list of perscriptions. If the perscription has the status of requiring an LSA, continue using those perscriptions in the rest of the workflow.

- The user should receive a message regarding the status of each of their perscriptions that require an LSA, along with each individual LSA.

- If the user doesn't interact with the message after a certain amount of days, we need to send another message. This can be quantified by whether they responds to the message.

- There should be an exception thrown if a certain number of days have passed with no response from the customer. 

- I want to adapt the flowret library to accomplish these tasks, defining necessary contexts and context variables, as well as business logic, while being seperate from the expected UI components. Instead I want messages to be printed to the console. 

- This will likely consist of a single json file and a couple of java classes for each branch in the workflow. 




Notes for implementation: 


Process starts and intitial audit logs were written

CheckPrescriptionsStep found perscriptions ("Rx101", "Rx205")

The PrescriptionsDecisionRoute determined that prescriptions requiring LSA exist, so it chose the "hasLSA" branch.

The SendNotificationStep executed 3 times, incrementing the count each time.

Each notification is followed by the WaitResponseStep and then a branch decision in ResponseDecisionRoute, where it found that the customer has not responded.

HandleNoResponseRoute was invoked twice:
- The first time, with notification count less than 3, it chose to resend the notification.
- After the third notification, it chose the "timeout" branch.

The ThrowExceptionStep executed, printing that it is throwing an exception due to no response.

The error message "Could not resume case. No process definition found. Case id -> pharmacy_case_1" is expected because Flowret has finalized (or pended) the case, so attempting to resume it further leads to that exception.

Catch block in the main runner logs this error and the process ends with exit code 0

To simulate different scenarios you can:
- Change the boolean value of responseRecieved in the json file
- Modify the notification count or the logic in HandleNoResponseRoute to test if the process correctly chooses to resend notifications or time out
- Update the JSON and/or add logging in the step/route classes to simulate other decision paths.


Flowchart:



flowchart TD
    A[Start] --> B[Check Prescriptions]
    B --> C[Prescriptions Decision]
    C -- "No LSA Required" --> X[End]
    C -- "LSA Required" --> D[Send Notification]
    D --> E[Wait for Response]
    E --> F[Response Decision]
    F -- "Customer Responded" --> X[End]
    F -- "No Response" --> G[Handle No Response]
    G -- "Resend Notification" --> D
    G -- "Timeout/Max Notifications Reached" --> H[Throw Exception]
    H --> X[End]



Process variables: 

- customerName

- LSAPrescriptions: Initially empty; later set to a comma-seperated list of prescriptions that require LSA

- responseReceived: A boolean flag to indicate if the customer has responded

- notificationCount: Keeps track of how many notifications have been sent



Individual files:



StartStep.java: Prints a message to indicate the process has started.

CheckPrescriptionsStep.java: Simulates checking a database for prescriptions that require LSA, using a hard coded string. It sets the LSAPrescriptions variable to a sample value.

SendNotificationStep.java: Reads the prescriptions and the current notification count, increments the count, updates the variable, and prints the notification message.

Simulates waiting for a customer rseponse by printing a message.

Simulates a timeout scenario by printing an error message and resturning an error response.

Marks the end of the process by printing a message.

PrescriptionsDecisionRoute.java: Reads the LSAPrescritions variable and chooses a branch:
- If empty, it returns "noLSA".
- If not, it returns "hasLSA".

Currently "hasLSA" is returned for any existing prescription

ResponseDecisionRoute.java returns "responded" or "notResponded" depending on the assigned value for responseReceived in the json

HandleNoResponseRoute.java: Reads the notification count variable in the json and checks it to determine whether to resend the notification or timeout (count >= 3).

PharmacyComponentFactory.java: Maps the component names and unit types from the json to the java classes.

PharmacyWorkflowRunner.java: Initializes Flowret, clears the working directory, loads the JSON process definition, wires up the runtime service with the custom comps (DAO, Component Factory, Event Handler), starts the case, and resumes it until completion.


The route checks the LSAPrescriptions variable to see if its empty. If isn't empty, it adds "hasLSA" to a list. That is used to create a RouteResponse which tells Flowret which branch to follow next: 

"branches": [
    { "name": "noLSA", "next": "end" },
    { "name": "hasLSA", "next": "send_notification" }
]
