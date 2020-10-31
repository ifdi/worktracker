package com.worktracker.integration.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class DatabaseContainerTest extends PostgreSQLContainer<DatabaseContainerTest> {

    private static final String IMAGE_VERSION = "postgres:10.9";
    private static DatabaseContainerTest container;

    private DatabaseContainerTest() {
        super(IMAGE_VERSION);
    }

    public static DatabaseContainerTest getInstance() {
        if (container == null) {
            container = new DatabaseContainerTest();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
    }
}
