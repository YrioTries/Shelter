package ru.shalter.exeption;

import lombok.Getter;

@Getter
public class ParameterNotValidException extends IllegalArgumentException {
  private final String parameter;
  private final String reason;

  public ParameterNotValidException(String parameter, String reason) {
    super();
    this.parameter = parameter;
    this.reason = reason;
  }
}
