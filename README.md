# FNZ-TimeTracking-Backend
# Add this line in application.properties:
# flask.api.endpoint=${FLASK_API_ENDPOINT}

#public class YourComponent {
#
#    @Value("${flask.api.endpoint}")
#    private String flaskApiEndpoint;
#
#    public void yourMethod() {
#        // Use the flaskApiEndpoint variable
#        System.out.println("Flask API Endpoint: " + flaskApiEndpoint);
#    }
#}

#For Windows:
#Open the Start Search, type in "env", and select "Edit the system environment variables".
#Click the "Environment Variables" button.
#Under "System variables", click "New" and add:
#Variable name: FLASK_API_ENDPOINT
#Variable value: http://your-flask-api-endpoint


#For Linux/macOS:
#Open a terminal.
#Add the environment variable to your shell configuration file
#export FLASK_API_ENDPOINT=http://your-flask-api-endpoint
