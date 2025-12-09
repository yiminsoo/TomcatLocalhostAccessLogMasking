package customvalve;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import javax.servlet.ServletException; 

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MaskingAttributeValve extends ValveBase {


    private static final Pattern ID_PATTERN = Pattern.compile("id:(\\d+)");
    
    private static final String MASKED_STRING = "id:******"; 
    
    private static final String MASKED_URI_ATTRIBUTE = "maskedUriForLog";

   
    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
       
        String originalURI = request.getRequestURI();
        
        String maskedURI = maskIdInString(originalURI);
        
        request.setAttribute(MASKED_URI_ATTRIBUTE, maskedURI);
)
        getNext().invoke(request, response);
    }
    

    private String maskIdInString(String input) {
        if (input == null) {
            return null;
        }
 
        Matcher matcher = ID_PATTERN.matcher(input);
 
        return matcher.replaceAll(MASKED_STRING);
    }
}