package io.swagger.api;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-11-13T01:11:41.400Z")

public class ApiException extends Exception{
    private int code;
    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
    
    
}
