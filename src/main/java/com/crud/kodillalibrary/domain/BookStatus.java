package com.crud.kodillalibrary.domain;

public enum BookStatus {
    AVAILABLE("available"),
    INUSE("inuse"),
    DAMAGED("damaged"),
    LOST("lost");

    private final String status;

    BookStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
