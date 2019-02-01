package com.hristiyantodorov.weatherapp.dagger;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Custom scope.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
@interface ActivityScoped {
}