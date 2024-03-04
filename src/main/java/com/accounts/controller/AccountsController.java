package com.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accounts.constants.AccountConstants;
import com.accounts.dto.CustomerDto;
import com.accounts.dto.ErrorResponseDto;
import com.accounts.dto.ResponseDto;
import com.accounts.service.IAccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
@Tag(
	name = "Accounts Controller Microservice - CRUD - EazyBank Application",
	description = "Accounts controller Microservice for Create, Fetch, Update and Delete Operations - EazyBank App"
		)
public class AccountsController {

	private IAccountService iaccountService;

	
	@Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer &  Account inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                              schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
	@PostMapping("/createAccount")
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
		iaccountService.createAccount(customerDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
	}

	@Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer &  Account details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
            		content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
	@GetMapping("/fetch")
	public ResponseEntity<CustomerDto> fetchAccount(
			@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number should be exactly 10 digits") @RequestParam String mobileNumber) {
		CustomerDto customerDto = iaccountService.fetchAccount(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}

	
	@Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer &  Account details based on a account number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
            		content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
		Boolean updateAccount = iaccountService.updateAccount(customerDto);
		if (updateAccount) {
			return new ResponseEntity<ResponseDto>(
					new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200), HttpStatus.OK);
		} else {
			return new ResponseEntity<ResponseDto>(
					new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE),
					HttpStatus.EXPECTATION_FAILED);
		}
	}

	
	@Operation(
            summary = "Delete Account & Customer Details REST API",
            description = "REST API to delete Customer &  Account details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
            		content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteCustomerAndAccount(
			@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number should be exactly 10 digits") @RequestParam String mobileNumber) {
		Boolean deleteAccount = iaccountService.deleteAccount(mobileNumber);
		if (deleteAccount) {
			return new ResponseEntity<ResponseDto>(
					new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200), HttpStatus.OK);
		} else {
			return new ResponseEntity<ResponseDto>(
					new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE),
					HttpStatus.EXPECTATION_FAILED);
		}
	}
}
