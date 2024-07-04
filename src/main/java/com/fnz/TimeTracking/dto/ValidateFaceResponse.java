package com.fnz.TimeTracking.dto;

public class ValidateFaceResponse
{
  private String email;

  public ValidateFaceResponse() {}

  public ValidateFaceResponse(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}