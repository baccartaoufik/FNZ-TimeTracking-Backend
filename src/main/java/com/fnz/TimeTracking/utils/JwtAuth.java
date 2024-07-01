package com.fnz.TimeTracking.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( ElementType.METHOD)  // Can be used on methods
@Retention( RetentionPolicy.RUNTIME)  // Available at runtime
public @interface JwtAuth
{
}
