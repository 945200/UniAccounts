package com.uniaccounts.prototype;

/**
 * Pattern PROTOTYPE - role "Prototype" (interface).
 * Declares the copy operation that every prototype must provide.
 */
public interface AccountPrototype<T> {
    T copy();
}
