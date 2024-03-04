package com.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDto {

	@NotEmpty(message = "Name should not be null or empty")
	@Size(min = 5, max = 30, message = "Name should be atleast 5 characters and not exceeding 30 characters")
	@Schema(
            description = "Name of the customer", example = "Eazy Bytes"
    )
	private String name;

	@NotEmpty
	@Email
	@Schema(
            description = "Email address of the customer", example = "tutor@eazybytes.com"
    )
	private String email;

	@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number should be exactly 10 digits")
	 @Schema(
	            description = "Mobile Number of the customer", example = "9345432123"
	    )
	private String mobileNumber;

	 @Schema(
	            description = "Account details of the Customer"
	    )
	private AccountDto accountDto;
}
