package me.kshulzh.farm

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["me.kshulzh.farm"])
@EnableAutoConfiguration(exclude = [LiquibaseAutoConfiguration::class])
class Configuration