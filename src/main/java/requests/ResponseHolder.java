package requests;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

/**
 * A utility class that holds Rest Assured Response and ValidatableResponse objects
 * using ThreadLocal to support thread-safe access in parallel test executions.
 */
public class ResponseHolder {
	//Thread-safe storage for the raw API response.
    private static final ThreadLocal<Response> responseThreadLocal = new ThreadLocal<>();
    //Thread-safe storage for the validatable API response.
    private static final ThreadLocal<ValidatableResponse> validatableResponseThreadLocal = new ThreadLocal<>();

   
	public static void setResponse(Response response) {
        responseThreadLocal.set(response);
    }

    public static ValidatableResponse getValidateableResponse() {
        return validatableResponseThreadLocal.get();
    }
    
    public static void setValidateableResponse(ValidatableResponse vresponse) {
    	validatableResponseThreadLocal.set(vresponse);
    }

    public static Response getResponse() {
        return responseThreadLocal.get();
    }

    public static void clear() {
        responseThreadLocal.remove();
        validatableResponseThreadLocal.remove();
		
	}
}
